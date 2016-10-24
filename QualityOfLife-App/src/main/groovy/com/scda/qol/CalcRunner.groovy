package com.scda.qol

import groovy.json.*

def outputFile = new File('output/qol.output')

if (args) {
    println("Attempting to initialize with properties from ${args[0]}")
    System.setProperty('qol.properties.file',args[0])
    
    if (args.size() > 1) {
        outputFile = new File(args[1])
    }
    println("Output will be written to ${outputFile.absolutePath}")
}

println('')
println('----- POLLUTION INDEX -------')
def pollution = new PollutionIndex()

println('')
println('----- CRIME INDEX -------')
def crime = new CrimeIndex()

println('')
println('----- TRAFFIC INDEX -------')
def traffic = new TrafficIndex()

println('')
println('----- COST OF LIVING INDEX -------')
def costOfLiving = new CostOfLivingIndex()

println('')
println('----- PROPERTY PRICES INDEX -------')
def propertyPrices = new PropertyPricesIndex()

println('')
println('----- CLIMATE INDEX -------')
def climate = new ClimateIndex()

println('')
println('----- HEALTH INDEX -------')
def health = new HealthIndex()

println('')
println('*~*~*~ QUALITY OF LIFE INDEX ~*~*~*')
double purchasePowerInclRentIndex = costOfLiving.getIndex('cost_of_living_incl_rent')
double housePriceToIncomeRatio = propertyPrices.getIndex('price_to_income_ratio')
double costOfLivingIndex = costOfLiving.getIndex('cost_of_living')
double safetyIndex = crime.getIndex('safety')
double healthIndex = health.getIndex('health')
double trafficTimeIndex = traffic.getIndex('commute')
double pollutionIndex = pollution.getIndex('pollution')
double climateIndex = climate.getIndex('climate')

def qolParams = ['purchasePowerInclRentIndex':purchasePowerInclRentIndex, 'housePriceToIncomeRatio':housePriceToIncomeRatio,
                 'costOfLivingIndex':costOfLivingIndex, 'safetyIndex':safetyIndex, 'healthIndex':healthIndex,
                 'trafficTimeIndex':trafficTimeIndex, 'pollutionIndex':pollutionIndex, 'climateIndex':climateIndex]

double qol = Math.max(0, 100 + purchasePowerInclRentIndex / 2.5 - (housePriceToIncomeRatio * 1.0) - costOfLivingIndex / 5 + safetyIndex / 2.0 + healthIndex / 2.5 - trafficTimeIndex / 2.0 - pollutionIndex * 2.0 / 3.0 + climateIndex / 2.0);
println('-------------------------------------------')
println("    QoL Index = ${qol} ")
println('-------------------------------------------')

// Write final output to a file for external integrations
JsonBuilder builder = new JsonBuilder()
builder.Results {
    Pollution {
        config pollution.config
        indices pollution.indices
    }
    Crime {
        config crime.config
        indices crime.indices
    }
    Traffic {
        config traffic.config
        indices traffic.indices
    }
    CostOfLiving {
        config costOfLiving.config
        indices costOfLiving.indices
    }
    PropertyPrices {
        config propertyPrices.config
        indices costOfLiving.indices
    }
    Climate {
        config climate.config
        indices climate.indices
    }
    Health {
        config health.config
        indices health.indices
    }
    QualityOfLife {
        config qolParams
        index qol
    }
}

String output = JsonOutput.prettyPrint(builder.toString())
outputFile.write(output)

