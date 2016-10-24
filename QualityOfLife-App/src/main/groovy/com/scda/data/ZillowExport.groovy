package com.scda.data

import org.apache.commons.csv.CSVPrinter
import org.apache.commons.csv.CSVFormat

import java.nio.file.Paths
import groovy.json.*

class ZillowExport extends BaseDataProcessor {

    def records = []
    def header = []
    def inputPath = 'output/zillow_comparison.json'

    ZillowExport() {
        outputPath = 'output/zillow_export.json'
    }

    void process() {
        def counter = 1
        def jsonFile = new File(inputPath)
        def jsonData = new JsonSlurper().parseText(jsonFile.text)

        jsonData.cities.each { city ->
            def cityData = ['index':counter, 
                            'city_name':city.city_name, 
                            'state':city.state, 
                            'country':city.country, 
                            'city_id':city.city_id]

            cityData << city.prices
            cityData << city.indices

            if (! header) { 
                header = cityData.keySet() 
            }
            records << cityData.values()       
            counter ++
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

