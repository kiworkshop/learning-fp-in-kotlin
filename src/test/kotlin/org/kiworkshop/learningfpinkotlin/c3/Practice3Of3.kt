package org.kiworkshop.learningfpinkotlin.c3

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe

class Practice3Of3 : ShouldSpec({
    context("음이 아닌 정수 n이 주어졌을 때") {
        should("n!을 구한다") {
            listOf(
                FactorialTestData(0, 1),
                FactorialTestData(1, 1),
                FactorialTestData(2, 2),
                FactorialTestData(3, 6),
                FactorialTestData(4, 24),
                FactorialTestData(5, 120),
                FactorialTestData(6, 720),
                FactorialTestData(7, 5040),
            ).forEach { (n, expected) ->
                val factorial = Factorial(n)

                val result = factorial.get()

                result shouldBe expected
            }
        }
    }
})

private data class FactorialTestData(
    val n: Int,
    val expected: Int
)
