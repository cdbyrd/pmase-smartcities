package com.scda.qol

abstract class BaseIndex implements IndexCalculator {

    protected static final def YES = 1
    protected static final def NO = -1

    def enableLogging 
    def appConfig
    def appPropFilename = 'app.properties'
    //def loadAsResource = System.properties['qol.properties.file'] ? false : true
    //def propFilename = System.properties['qol.properties.file'] ?: 'qol.properties'
    def loadAsResource = false
    def propFilename = System.properties['qol.properties.file'] ?: 'src/dist/config/qol.properties'
    def config
    def indexType
    def indices = [:]

    BaseIndex(IndexType type) {
        indexType = type
        config = getIndexConfig(indexType)
        appConfig = getAppConfig()

        enableLogging = appConfig?.logging == true ?: false
        log("Initialize ${indexType} Index")

        // force execution of the interface methods
        setup()
        calculateIndex()
    }

    def getIndex(def key='main') { indices[key] }

    def getIndexConfig(def indexType) {
        def props
        if (loadAsResource) {
            def propURL = getClass().getResource("/${propFilename}")
            log("Loading propertes as resource from ${propURL.toString()}")
            props = propURL.text
        } else {
            def propFile = new File(propFilename)
            log("Loading properties as file from ${propFile.absolutePath}")
            props = propFile.text
        }
       
       assert props
       assert indexType

       new ConfigSlurper(indexType.propPrefix).parse(props)
    }

    def getAppConfig() {
        def propURL = getClass().getResource("/${appPropFilename}")
        new ConfigSlurper().parse(propURL.text)
    }

    void log(def message) {
        if (enableLogging) {
            println message
        }
    }

    double getIndexPartPreCalc(double internalValue) {
        return (internalValue + 2) * 25
    }

    double getIndexPartPreCalcExpScaleStandard(double internalValue) {
        return getIndexPartPreCalcExpScale(internalValue, Math.E)
    }

    double getIndexPartPreCalcExpScale(double internalValue, double exp) {
        return Math.pow((internalValue + 2) * 25, exp)
    }

    double calcScaleStandardIndexFromSum(double scaleSum, int elems) {
        return Math.pow(scaleSum / elems, 1 / (Math.E * 8.8 / 10))
    }

}
