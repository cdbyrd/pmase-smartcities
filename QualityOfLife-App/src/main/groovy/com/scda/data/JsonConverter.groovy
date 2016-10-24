package com.scda.data

import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVPrinter
import org.apache.commons.csv.CSVFormat
import static org.apache.commons.csv.CSVFormat.*

import java.nio.file.Paths
import groovy.json.*

class JsonConverter {

    static fromCsvToJson(def input, def output) {
        def listing = []

        Paths.get(input).withReader { reader ->
            CSVParser csv = new CSVParser(reader, DEFAULT.withHeader())

            for (record in csv.iterator()) {
                listing << record.toMap()
            }
        }

        Paths.get(output).withWriter { jsonWriter ->
            jsonWriter.write JsonOutput.prettyPrint(JsonOutput.toJson(listing))
        }
    }

    static fromJsonToCsv(def input, def output) {
        def jsonFile = new File(output)
        def jsonData = new JsonSlurper().parseText(jsonFile.text)

        def records = []
        def header = []
        def counter = 1

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

        Paths.get(output).withWriter { csvWriter ->
            CSVFormat format = CSVFormat.DEFAULT.withHeader(header as String[])
            CSVPrinter printer = new CSVPrinter(csvWriter, format)
            records.each {
                printer.printRecord(it)
            }
        }
    }
}
