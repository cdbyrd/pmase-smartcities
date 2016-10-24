package com.scda.data

import groovyx.net.http.HTTPBuilder
import groovyx.net.http.RESTClient
import groovy.json.*

class ZillowCityPrices extends BaseDataProcessor {
    
    def inputPath = 'input/City_MedianValuePerSqft_AllHomes.csv'

    ZillowCityPrices() {
        outputPath = 'output/zillow_city_prices.json' 
    }

    void process() { }

    void output() {
        JsonConverter.fromCsvToJson(inputPath, outputPath)
    }
}
