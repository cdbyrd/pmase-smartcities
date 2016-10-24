package com.scda.qol

class PropertyPricesIndex extends BaseIndex implements IndexCalculator {

    PropertyPricesIndex() {
        super(IndexType.PropertyPrices)
    }

    void setup() {
        log("Loaded Configuration: ${config}")
    }

    void calculateIndex() {
        // This is just a straight pass through of indices
        indices << config.index
        log("Calculated index values: ${indices}")
    }

}
