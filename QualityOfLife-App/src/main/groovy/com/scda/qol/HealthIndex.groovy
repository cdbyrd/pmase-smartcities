package com.scda.qol

class HealthIndex extends BaseIndex implements IndexCalculator {

    double skill_and_competency
    double speed
    double modern_equipment
    double accuracy_and_completeness
    double friendliness_and_courtesy
    double responsiveness_waitings
    double location
    double cost

    HealthIndex() {
        super(IndexType.Health)
    }

    void setup() {
        skill_and_competency = config.skill_and_competency
        speed = config.speed
        modern_equipment = config.modern_equipment
        accuracy_and_completeness = config.accuracy_and_completeness
        friendliness_and_courtesy = config.friendliness_and_courtesy
        responsiveness_waitings = config.responsiveness_waitings
        location = config.location
        cost = config.cost

        log("Loaded Configuration: ${config}")
    }

    void calculateIndex() {

        double overall = 0.0;
        overall += getIndexPartPreCalc(skill_and_competency);
        overall += getIndexPartPreCalc(speed);
        overall += getIndexPartPreCalc(modern_equipment);
        overall += getIndexPartPreCalc(accuracy_and_completeness);
        overall += getIndexPartPreCalc(friendliness_and_courtesy);
        overall += getIndexPartPreCalc(responsiveness_waitings);
        overall += getIndexPartPreCalc(location);
        overall += 2 * getIndexPartPreCalc(cost);
        indices.health = overall / 9;

        double expScale = 0.0;
        expScale += getIndexPartPreCalcExpScaleStandard(skill_and_competency);
        expScale += getIndexPartPreCalcExpScaleStandard(speed);
        expScale += getIndexPartPreCalcExpScaleStandard(modern_equipment);
        expScale += getIndexPartPreCalcExpScaleStandard(accuracy_and_completeness);
        expScale += getIndexPartPreCalcExpScaleStandard(friendliness_and_courtesy);
        expScale += getIndexPartPreCalcExpScaleStandard(responsiveness_waitings);
        expScale += getIndexPartPreCalcExpScaleStandard(location);
        expScale += 2 * getIndexPartPreCalcExpScaleStandard(cost);
        indices.healthExp = calcScaleStandardIndexFromSum(expScale, 9);

        log("Calculated index values: ${indices}")
    }

}
