package com.scda.data

import groovyx.net.http.HTTPBuilder
import groovyx.net.http.RESTClient
import static groovyx.net.http.ContentType.*

import groovy.json.*

class PropertyPrices extends BaseDataProcessor {

    def country = 'US'
    def priceItems = []

    PropertyPrices() {
        outputPath = 'output/price_items.json'
        apiPath = '/api/price_items'
    }

    void process() {
        def client = new RESTClient(apiHost)
        def resp = client.get(path:apiPath, query:[api_key:apiKey, country:country])
        def json = resp.data

        log '-- categories --'
        def categories = []
        json.items.each { categories << it.category }
        log categories.unique(false)

        log 'search for items impacting rent'
        def rentItems = json.items.findAll { it.category == 'Rent Per Month' }
        log rentItems

        log 'search for items impacting property purchase'
        def propItems = json.items.findAll { it.category == 'Buy Apartment Price' }
        log propItems

        priceItems = rentItems + propItems
        log 'impacted item ids'
        priceItems.each { log it.item_id }
    }

    void output() {
        def jsonOut = JsonOutput.toJson(['items':priceItems])
        def output = new File(outputPath)
        output.write(JsonOutput.prettyPrint(jsonOut))
    }
}


