package com.scda.data

import groovy.json.*

final def NUMBEO = 'Numbeo'
final def ZILLOW = 'Zillow'

def validTypes = [NUMBEO, ZILLOW]
def exportType = NUMBEO
if (args) { 
    exportType = args[0] 
} else if (System.properties['data.export.type']) {
    exportType = System.properties['data.export.type']
}
assert exportType in validTypes

def exists(def path) {
    new File(path)?.exists()
}

println("Attempting to export ${exportType} data")

println "\n-- Gather Cities Data -- "
def cities = new Cities()
if (! exists(cities.outputPath)) {
    println "**(UPDATE DATA)**"
    cities.process()
    cities.output()
}

println "\n-- Gather Indices Data -- "
def indices = new Indices()
if (! exists(indices.outputPath)) {
    println "**(UPDATE DATA)**"
    indices.process()
    indices.output()
}

def propPrices
def cityPrices
def merger
def exporter

if (exportType == NUMBEO) {
    println "\n-- Gather Property Prices Data -- "
    propPrices = new PropertyPrices()
    if (! exists(propPrices.outputPath)) {
        println "**(UPDATE DATA)**"
        propPrices.process()
        propPrices.output()
    }

    cityPrices = new NumbeoCityPrices()
    merger = new NumbeoMerge()
    exporter = new NumbeoExport()
} else if (exportType == ZILLOW) {
    JsonConverter.fromCsvToJson('input/City_MedianValuePerSqft_AllHomes.csv',
                                'output/City_MedianValuePerSqft_AllHomes.json')
    cityPrices = new ZillowCityPrices()
    merger = new ZillowMerge()
    exporter = new ZillowExport()
}

println "\n-- Gather City Prices Data --"
if (! exists(cityPrices.outputPath)) {
    println "**(UPDATE DATA)**"
    cityPrices.process()
    cityPrices.output()
}

println "\n-- Merge City Prices and Indices --"
merger.process()
merger.output()

println "\n-- Export Comparison To CSV --"
exporter.process()
exporter.output()
