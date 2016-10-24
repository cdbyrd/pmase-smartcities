package com.scda.data

import groovyx.net.http.HTTPBuilder
import groovyx.net.http.RESTClient
import groovy.json.*

class Cities extends BaseDataProcessor {

    def outputCities = []
    def country='US'

    Cities() {
        apiPath  = '/api/cities'
        outputPath = 'output/cities.json'
    }

    void process() {
        println "Make call to ${apiHost}${apiPath}"
        def client = new RESTClient(apiHost)
        def resp = client.get(path:apiPath, query:[api_key:apiKey, country:country])

        // get all cities
        def cities = resp.data.cities.sort { it.city }

        // reformat output
        cities.each { 
            def cityData = [:]
            cityData['city_id'] = it.city_id
            cityData['city_name'] = it.city.getAt(0..it.city.indexOf(',')-1)
            cityData['city_state'] = it.city.getAt(it.city.indexOf(',')+1..-1).minus(' ')
            cityData['country'] = it.country
            cityData['latitude'] = it.latitude
            cityData['longitude'] = it.longitude

            outputCities << cityData
        }
    }

    def search(def cityName) {
        println "Search for ${cityName}"
        def record = outputCities.find { it.city_name =~ cityName }
        println record
    }

    void  output() {
        println 'Output all cities to file'
        def output = new File(outputPath)
        def json = JsonOutput.toJson(['cities':outputCities])
        output.write(JsonOutput.prettyPrint(json))
    }

}
