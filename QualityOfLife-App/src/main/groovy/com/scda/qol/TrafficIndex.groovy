package com.scda.qol

class TrafficIndex extends BaseIndex implements IndexCalculator {

    TrafficIndex() {
        super(IndexType.Traffic)
    }

    void setup() {
        log("Loaded Configuration: ${config}")
    }

    void calculateIndex() {
        double avgTime = config.time_overall
        double tooMuchTime = 0.0;
        if (avgTime > 25.0) {
            tooMuchTime = avgTime - 25;
        }
        double timeExp = avgTime + Math.pow(tooMuchTime, Math.E);

        double co2 = 0.0;
        co2 += config.time_bus * 20.0; // bus produces 20g of CO2 per minute (for each passenger)
        co2 += config.time_driving * 133.0; // car produces 133g of CO2 per minute (assumes only driver)
        co2 += config.time_train * 10.0; // train produces 10g of CO2 per minute (for each passenger)
        co2 += config.time_tram * 15.0; // tram produces 15g of CO2 per minute (for each passenger)
        co2 += config.time_other * 10.0; // other produces 10g of CO2 per minute
        co2 += config.time_motorbike * 80.0; // motorbike produces 80g of CO2 per hour
        co2 = 2 * co2; 

        indices.commute = avgTime
        indices.commuteExp = timeExp
        indices.co2 = co2
        indices.inefficiency = config.index.inefficiency
        indices.traffic = avgTime + Math.sqrt(timeExp) + Math.sqrt(co2) + Math.sqrt(config.index.inefficiency);
        
        log("Calculated index values: ${indices}")
    }

}
