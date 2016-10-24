package com.scda.data

import groovyx.net.http.HTTPBuilder
import groovyx.net.http.RESTClient
import groovy.json.*

class Indices extends BaseDataProcessor {

    def goodCounter = 0
    def badCounter = 0
    def indices = []

    def citiesFilePath = 'output/cities.json'

    Indices() {
        apiPath = '/api/indices'
        outputPath = 'output/indices.json'
    }
    
    void process() {
        def citiesFile = new File(citiesFilePath)
        def citiesJson = new JsonSlurper().parseText(citiesFile.text)

        println "Make call to ${apiHost}${apiPath}"
        def client = new RESTClient(apiHost)
        citiesJson.cities.each { city ->
            def resp = client.get(path:apiPath, query:[api_key:apiKey, city_id:city.city_id])
            def cityData = ['city_id':city.city_id, indices:resp.data]
            
            if (cityData.indices.quality_of_life_index) {
                log "-- $city.city_name --"
                log cityData
                goodCounter += 1
                indices << cityData
            } else {
                badCounter += 1
            }
        }
    }

    void output() {
        def json = JsonOutput.toJson(['cities':indices])
        def output = new File(outputPath)

        output.write(JsonOutput.prettyPrint(json))
        log 'Final Results'
        log "Good Counter = $goodCounter"
        log "Bad Counter = $badCounter"
    }
}
