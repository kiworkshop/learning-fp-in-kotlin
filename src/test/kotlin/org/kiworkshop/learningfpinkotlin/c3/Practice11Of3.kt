package org.kiworkshop.learningfpinkotlin.c3

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe

class Practice11Of3 : ShouldSpec({
    context("음이 아닌 정수 n이 주어졌을 때") {
        should("n!을 구한다") {
            listOf(
                FunctionalFactorialTestData(0, 1),
                FunctionalFactorialTestData(1, 1),
                FunctionalFactorialTestData(2, 2),
                FunctionalFactorialTestData(3, 6),
                FunctionalFactorialTestData(4, 24),
                FunctionalFactorialTestData(5, 120),
                FunctionalFactorialTestData(6, 720),
                FunctionalFactorialTestData(7, 5040),
            ).forEach { (n, expected) ->
                val result = factorial(n)

                result shouldBe expected
            }
        }
    }
})

private fun factorial(n: Int): Int {
    return factorial(n, 1)
}

private fun factorial(n: Int, beforeEvaluated: Int): Int {
    if (n == 0) {
        return beforeEvaluated
    }
    return factorial(n - 1, beforeEvaluated * n)
}

private data class FunctionalFactorialTestData(
    val n: Int,
    val expected: Int
)
