package com.scda.data

abstract class BaseDataProcessor implements DataProcessor {

    //def appPropFilename = 'src/main/resources/app.properties'
    def appPropFilename = 'app.properties'
    def appConfig
    def enableLogging

    def apiHost
    def apiPath
    def apiKey

    def outputPath

    BaseDataProcessor() {
        appConfig = getAppConfig()
        apiHost = appConfig.api_host
        apiKey = appConfig.api_key
        enableLogging = appConfig.logging == true ?: false
    }

    def getAppConfig() {
        def propURL = getClass().getResource("/${appPropFilename}")
        new ConfigSlurper().parse(propURL.text)
    }

    def log(def message) {
        if (enableLogging) { println message }
    }
}
