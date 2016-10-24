package com.scda.data

import groovy.json.*

class NumbeoMerge extends BaseDataProcessor {

    def mergeData = []

    def cityPricesFilePath = 'output/city_prices.json'
    def indicesFilePath = 'output/indices.json'

    NumbeoMerge() {
        outputPath = 'output/comparison.json'
    }

    void process() {
        def cityPricesFile  = new File(cityPricesFilePath)
        def cityPrices = new JsonSlurper().parseText(cityPricesFile.text)
        assert cityPrices.cities

        def cityIndicesFile = new File(indicesFilePath)
        def cityIndices = new JsonSlurper().parseText(cityIndicesFile.text)
        assert cityIndices.cities

        cityIndices.cities.each { city ->
            log "\n** Add data for $city.city_id **"
            def cityData = [:]

            log '\n-- Add Price Data --'
            cityData << cityPrices.cities.find { it.city_id == city.city_id } 
            log cityData

            log '\n-- Add Indices Data --'
            cityData << city
            log cityData

            mergeData << cityData
        }
    }

    void output() {
        def json = JsonOutput.toJson(['cities':mergeData])
        File output = new File(outputPath)
        output.write(JsonOutput.prettyPrint(json))
    }

}
