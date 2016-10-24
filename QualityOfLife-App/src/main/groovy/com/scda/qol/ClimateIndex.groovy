package com.scda.qol

class ClimateIndex extends BaseIndex {

    ClimateIndex() {
        super(IndexType.Climate)
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
