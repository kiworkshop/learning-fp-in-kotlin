package org.kiworkshop.learningfpinkotlin.c3

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe

class Practice10Of3 : ShouldSpec({
    context("음이 아닌 정수 n이 주어졌을 때") {
        should("n!을 구한다") {
            listOf(
                ImprovedFactorialTestData(0, 1),
                ImprovedFactorialTestData(1, 1),
                ImprovedFactorialTestData(2, 2),
                ImprovedFactorialTestData(3, 6),
                ImprovedFactorialTestData(4, 24),
                ImprovedFactorialTestData(5, 120),
                ImprovedFactorialTestData(6, 720),
                ImprovedFactorialTestData(7, 5040),
            ).forEach { (n, expected) ->
                val factorial = ImprovedFactorial(n)

                val result = factorial.get()

                result shouldBe expected
            }
        }
    }
})

private data class ImprovedFactorialTestData(
    val n: Int,
    val expected: Int
)
