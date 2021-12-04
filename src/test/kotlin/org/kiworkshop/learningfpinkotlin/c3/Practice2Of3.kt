package org.kiworkshop.learningfpinkotlin.c3

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.doubles.plusOrMinus
import io.kotest.matchers.shouldBe

class Practice2Of3 : ShouldSpec({
    context("실수 X와 거듭제곱 n이 주어졌을 때") {
        should("X의 n제곱을 구한다") {
            listOf(
                PowerTestData(2.5, 2, 6.25),
                PowerTestData(1.0, 100, 1.0),
                PowerTestData(-0.2, 2, 0.04),
                PowerTestData(-0.2, 3, -0.008),
                PowerTestData(100.0, -1, 0.01),
                PowerTestData(100.0, -2, 0.0001),
            ).forEach { (base, power, expected) ->
                val operator = PowerOperator()

                val result = operator.power(base, power)

                result shouldBe (expected plusOrMinus 0.0000000001)
            }
        }
    }
})

private data class PowerTestData(
    val base: Double,
    val power: Int,
    val expected: Double,
)
