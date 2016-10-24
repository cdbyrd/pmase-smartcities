package com.scda.data

import groovy.json.*

class ZillowMerge extends BaseDataProcessor {

    def mergeData = []
    def cityPricesPath = 'output/zillow_city_prices.json'
    def cityIndicesPath = 'output/indices.json'

    ZillowMerge() {
        outputPath = 'output/zillow_comparison.json'
    }

    void process() {
        def cityPricesFile  = new File(cityPricesPath)
        def cityIndicesFile = new File(cityIndicesPath)

        def slurper = new JsonSlurper()
        def cityPrices = slurper.parseText(cityPricesFile.text)
        def cityIndices = slurper.parseText(cityIndicesFile.text)

        cityIndices.cities.each { city ->
            def regionIds = city.indices.name?.split(/,\s*/) //"name, state, country"
            def name = regionIds[0]
            def state = regionIds[1]
            def country = regionIds[2]
            def cityData = ['city_name':name, 'state':state, 'country':country]

            // add prices
            def prices = cityPrices.find { 
                it.RegionName == name && it.State == state
            } 

            if (prices) {
                // add prices
                cityData['prices'] = ['size_rank':prices.SizeRank, 
                                      'avg_prop_price_sqft':prices.Average]

                // add indices
                cityData << city

                log "Matched price and index for ${name}, ${state}"
                mergeData << cityData
            }
        }
    }

    void output() {
        def json = JsonOutput.toJson(['cities':mergeData])
        File output = new File(outputPath)
        output.write(JsonOutput.prettyPrint(json))
    }
}
