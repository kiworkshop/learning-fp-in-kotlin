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
