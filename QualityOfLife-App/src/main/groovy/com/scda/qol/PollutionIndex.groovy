package com.scda.qol

class PollutionIndex extends BaseIndex implements IndexCalculator {

    double air_quality
    double drinking_water_quality_accessibility
    double water_pollution
    double garbage_disposal_satisfaction
    double clean_and_tidy
    double noise_and_light_pollution
    double green_and_parks_quality
    double comfortable_to_spend_time

    private class PollutionDbEntry {
        static final def IS_POLLUTION_AIR_QUALITY=NO
        static final def IS_POLLUTION_DRINKING_WATER_QUALITY_ACCESSIBILITY=NO
        static final def IS_POLLUTION_WATER_POLLUTION=YES
        static final def IS_POLLUTION_GARBAGE_DISPOSAL_SATISFACTION=NO
        static final def IS_POLLUTION_CLEAN_AND_TIDY=NO
        static final def IS_POLLUTION_NOISE_AND_LIGHT_POLLUTION=YES
        static final def IS_POLLUTION_GREEN_AND_PARKS_QUALITY=NO
        static final def IS_POLLUTION_COMFORTABLE_TO_SPEND_TIME=NO
    }

    PollutionIndex() {
        super(IndexType.Pollution)
    }

    void setup() {
        air_quality = config.air_quality
        drinking_water_quality_accessibility = config.drinking_water_quality_accessibility
        water_pollution = config.water_pollution 
        garbage_disposal_satisfaction = config.garbage_disposal_satisfaction
        clean_and_tidy = config.clean_and_tidy
        noise_and_light_pollution = config.noise_and_light_pollution
        green_and_parks_quality = config.green_and_parks_quality
        comfortable_to_spend_time = config.comfortable_to_spend_time

        log("Loaded Configuration: ${config}")
    }

    void calculateIndex() {
        //assumes air_quality and other entries from user are in the range [-2, 2], where -2 means perceived as very low, and +2 means very high
        //PollutionDbEntry.IS_POLLUTION_AIR_QUALITY and similar are constant variables which are either -1 and 1 i.e. IS_POLLUTION_AIR_QUALITY = -1.0
        //These constant variables in PollutionDbEntry class are 1 for values which represent pollutions and -1 for values which represent opposite (purity, cleanliness)
        double overall = 0.0
        overall += 7 * getIndexPartPreCalc(PollutionDbEntry.IS_POLLUTION_AIR_QUALITY * air_quality)
        overall += 2 * getIndexPartPreCalc(PollutionDbEntry.IS_POLLUTION_DRINKING_WATER_QUALITY_ACCESSIBILITY * drinking_water_quality_accessibility)
        overall += 2 * getIndexPartPreCalc(PollutionDbEntry.IS_POLLUTION_WATER_POLLUTION * water_pollution)
        overall += getIndexPartPreCalc(PollutionDbEntry.IS_POLLUTION_GARBAGE_DISPOSAL_SATISFACTION * garbage_disposal_satisfaction)
        overall += getIndexPartPreCalc(PollutionDbEntry.IS_POLLUTION_CLEAN_AND_TIDY * clean_and_tidy)
        overall += getIndexPartPreCalc(PollutionDbEntry.IS_POLLUTION_NOISE_AND_LIGHT_POLLUTION * noise_and_light_pollution)
        overall += getIndexPartPreCalc(PollutionDbEntry.IS_POLLUTION_GREEN_AND_PARKS_QUALITY * green_and_parks_quality)
        overall += 2 * getIndexPartPreCalc(PollutionDbEntry.IS_POLLUTION_COMFORTABLE_TO_SPEND_TIME * comfortable_to_spend_time)

        double overallExpScale = 0.0
        overallExpScale += 7 * getIndexPartPreCalcExpScaleStandard(PollutionDbEntry.IS_POLLUTION_AIR_QUALITY * air_quality)
        overallExpScale += 2 * getIndexPartPreCalcExpScaleStandard(PollutionDbEntry.IS_POLLUTION_DRINKING_WATER_QUALITY_ACCESSIBILITY * drinking_water_quality_accessibility)
        overallExpScale += 2 * getIndexPartPreCalcExpScaleStandard(PollutionDbEntry.IS_POLLUTION_WATER_POLLUTION * water_pollution)
        overallExpScale += getIndexPartPreCalcExpScaleStandard(PollutionDbEntry.IS_POLLUTION_GARBAGE_DISPOSAL_SATISFACTION * garbage_disposal_satisfaction)
        overallExpScale += getIndexPartPreCalcExpScaleStandard(PollutionDbEntry.IS_POLLUTION_CLEAN_AND_TIDY * clean_and_tidy)
        overallExpScale += getIndexPartPreCalcExpScaleStandard(PollutionDbEntry.IS_POLLUTION_NOISE_AND_LIGHT_POLLUTION * noise_and_light_pollution)
        overallExpScale += getIndexPartPreCalcExpScaleStandard(PollutionDbEntry.IS_POLLUTION_GREEN_AND_PARKS_QUALITY * green_and_parks_quality)
        overallExpScale += 2 * getIndexPartPreCalcExpScaleStandard(PollutionDbEntry.IS_POLLUTION_COMFORTABLE_TO_SPEND_TIME * comfortable_to_spend_time)

        indices.pollution = overall / 14.5 //max 17
        indices.pollutionExp = calcScaleStandardIndexFromSum(overallExpScale, 12)
        
        log("Calculated index values: ${indices}")
    }

}
