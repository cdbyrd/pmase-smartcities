package com.scda.data

import groovyx.net.http.HTTPBuilder
import groovyx.net.http.RESTClient
import groovy.json.*

class NumbeoCityPrices extends BaseDataProcessor {

    def prices = []
    def citiesFilePath = 'output/cities.json'
    def pricesFilePath = 'output/price_items.json'
    
    NumbeoCityPrices() {
        apiPath = '/api/city_prices'
        outputPath = 'output/city_prices.json'
    }

    def validatePriceData(def priceData) {
        def valid = true
        if (priceData['rent_items']?.size < 4) { valid = false }
        if (priceData['property_items']?.size < 2) { valid = false }
        
        valid
    }

    void process() {
        def citiesFile = new File(citiesFilePath)
        def citiesJson = new JsonSlurper().parseText(citiesFile.text)
        assert citiesJson.cities

        def pricesFile = new File(pricesFilePath)
        def pricesJson = new JsonSlurper().parseText(pricesFile.text)
        assert pricesJson.items

        def rentItemIds = pricesJson.items.findAll { it.category =~ '^Rent' }.collect { it.item_id }
        def propItemIds = pricesJson.items.findAll { it.category =~ '^Buy' }.collect { it.item_id } 

        println "Make call to ${apiHost}${apiPath}"
        def client = new RESTClient(apiHost)
        citiesJson.cities.each { city ->
            def resp = client.get(path:apiPath, query:[api_key:apiKey, city_id:city.city_id])
            
            //def rentItems = resp.data.prices.findAll { it.item_id in [26,27,28,29] }
            //def propItems = resp.data.prices.findAll { it.item_id in [100,101] }
            def rentItems = resp.data.prices.findAll { it.item_id in rentItemIds }
            def propItems = resp.data.prices.findAll { it.item_id in propItemIds }

            def cityItems = [:]
            cityItems << ['city_id':city.city_id, 'rent_items':rentItems, 'property_items':propItems]

            if (validatePriceData(cityItems)) {
                log("-- $city.city_name --")
                log(cityItems)
                prices << cityItems
            }
        }
    }

    void output() {
        def json = JsonOutput.toJson(['cities':prices])
        def output = new File(outputPath)
        output.write(JsonOutput.prettyPrint(json))
    }
}
