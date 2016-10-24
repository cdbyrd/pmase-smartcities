package com.scda.data

import org.apache.commons.csv.CSVPrinter
import org.apache.commons.csv.CSVFormat

import java.nio.file.Paths
import groovy.json.*

class NumbeoExport extends BaseDataProcessor {

    def records = []
    def header = []
    def inputPath = 'output/comparison.json'

    def avg(def listValues) {
        listValues?.sum() / listValues?.size()
    }

    NumbeoExport() {
        outputPath = 'output/export.csv'
    }

    void process() {
        def rowCount = 1
        def jsonFile = new File(inputPath)
        def jsonData = new JsonSlurper().parseText(jsonFile.text)
        assert jsonData.cities

        jsonData.cities.each { city ->

            def propPrices = city.property_items.collect { it.average_price }
            def avgPrice = avg(propPrices)

            def rentPrices = city.rent_items.collect { it.average_price }
            def avgRent = avg(rentPrices)

            def cityData = [ 'index':rowCount, 
                             'city_id':city.city_id, 
                             'prop_avg_price':avgPrice, 
                             'rent_avg_price':avgRent ]

            cityData << city.indices

            if (! header) {
                header = cityData.keySet()
            }

            records << cityData.values()
            rowCount ++
        }
    }

    void output() {
        Paths.get(outputPath).withWriter { csvWriter ->
            CSVFormat format = CSVFormat.DEFAULT.withHeader(header as String[])
            CSVPrinter printer = new CSVPrinter(csvWriter, format)
            records.each {
                printer.printRecord(it)
            }
        }
    }
}

