package com.scda.qol

class CrimeIndex extends BaseIndex implements IndexCalculator {

    double level_of_crime
    double crime_increasing
    double safe_alone_daylight
    double safe_alone_night
    double worried_home_broken
    double worried_mugged_robbed
    double worried_car_stolen
    double worried_things_car_stolen
    double worried_attacked
    double worried_insulted
    double worried_skin_ethnic_religion
    double problem_drugs
    double problem_property_crimes
    double problem_violent_crimes
    double problem_corruption_bribery

    CrimeIndex() {
        super(IndexType.Crime)
    }

    void setup() {
        level_of_crime = config.level_of_crime
        crime_increasing = config.crime_increasing
        safe_alone_daylight = config.safe_alone_daylight
        safe_alone_night = config.safe_alone_night
        worried_home_broken = config.worried_home_broken
        worried_mugged_robbed = config.worried_mugged_robbed
        worried_car_stolen = config.worried_car_stolen
        worried_things_car_stolen = config.worried_things_car_stolen
        worried_attacked = config.worried_attacked
        worried_insulted = config.worried_insulted
        worried_skin_ethnic_religion = config.worried_skin_ethnic_religion
        problem_drugs = config.problem_drugs
        problem_property_crimes = config.problem_property_crimes
        problem_violent_crimes = config.problem_violent_crimes
        problem_corruption_bribery = problem_corruption_bribery

        log("Loaded Configuration: ${config}")
    }

    //assumes all input values are in the range [-2 , 2], where -2 means very low and 2 means very high
    void calculateIndex() {

        double overall = 0.0
        overall += 2 * getIndexPartPreCalc(level_of_crime)
        overall += getIndexPartPreCalc(crime_increasing)
        overall += getIndexPartPreCalc(-safe_alone_daylight)
        overall += getIndexPartPreCalc(-safe_alone_night)
        overall += getIndexPartPreCalc(worried_home_broken)
        overall += getIndexPartPreCalc(worried_mugged_robbed)
        overall += getIndexPartPreCalc(worried_car_stolen)
        overall += getIndexPartPreCalc(worried_things_car_stolen)
        overall += getIndexPartPreCalc(worried_attacked)
        overall += getIndexPartPreCalc(worried_insulted)
        overall += getIndexPartPreCalc(worried_skin_ethnic_religion)
        overall += getIndexPartPreCalc(problem_drugs)
        overall += getIndexPartPreCalc(problem_property_crimes)
        overall += getIndexPartPreCalc(problem_violent_crimes)
        overall += getIndexPartPreCalc(problem_corruption_bribery)

        double main = overall / 16
        double exp = main / 2 + ((main > 20) ? Math.pow(main - 20, 1.65) : 0.0)
        indices.crime = main
        indices.crimeExp = exp

        double safety = 0.0
        safety += 2 * getIndexPartPreCalc(-level_of_crime)
        safety += getIndexPartPreCalc(-crime_increasing)
        safety += getIndexPartPreCalc(safe_alone_daylight)
        safety += getIndexPartPreCalc(safe_alone_night)
        safety += getIndexPartPreCalc(-worried_home_broken)
        safety += getIndexPartPreCalc(-worried_mugged_robbed)
        safety += getIndexPartPreCalc(-worried_car_stolen)
        safety += getIndexPartPreCalc(-worried_things_car_stolen)
        safety += getIndexPartPreCalc(-worried_attacked)
        safety += getIndexPartPreCalc(-worried_insulted)
        safety += getIndexPartPreCalc(-worried_skin_ethnic_religion)
        safety += getIndexPartPreCalc(-problem_drugs)
        safety += getIndexPartPreCalc(-problem_property_crimes)
        safety += getIndexPartPreCalc(-problem_violent_crimes)
        safety += getIndexPartPreCalc(-problem_corruption_bribery)

        indices.safety = safety / 16

        log("Calculated index values: ${indices}")
    }

}
