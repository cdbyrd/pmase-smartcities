package com.scda.qol

enum IndexType {
    CostOfLiving('costofliving'), 
    PropertyPrices('propertyprices'), 
    Crime('crime'), 
    Health('health'), 
    Pollution('pollution'), 
    Traffic('traffic'),
    Climate('climate') 

    IndexType(def prefix) { propPrefix = prefix }

    private final def propPrefix
    def propPrefix() { propPrefix }
}
