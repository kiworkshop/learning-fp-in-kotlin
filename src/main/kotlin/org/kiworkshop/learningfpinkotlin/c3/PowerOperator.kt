package org.kiworkshop.learningfpinkotlin.c3

class PowerOperator {
    fun power(base: Double, power: Int): Double {
        if (power == 0) {
            return 1.0
        }
        if (power < 0) {
            return power(1 / base, -power)
        }
        return base * power(base, power - 1)
    }
}

class ImprovedPowerOperator {
    fun power(base: Double, power: Int): Double {
        if (power < 0) {
            return power(1 / base, -power, 1.0)
        }
        return power(base, power, 1.0)
    }

    tailrec fun power(base:Double, power: Int, beforeEvaluated: Double): Double {
        if (power == 0) {
            return beforeEvaluated
        }
        return power(base, power - 1, base * beforeEvaluated)
    }
}
