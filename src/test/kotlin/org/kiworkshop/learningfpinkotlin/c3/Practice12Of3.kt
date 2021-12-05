package org.kiworkshop.learningfpinkotlin.c3

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe

class Practice12Of3 : ShouldSpec({
    context("음이 아닌 정수 n이 주어졌을 때") {
        should("n!을 구한다") {
            listOf(
                TailrecFactorialTestData(0, 1),
                TailrecFactorialTestData(1, 1),
                TailrecFactorialTestData(2, 2),
                TailrecFactorialTestData(3, 6),
                TailrecFactorialTestData(4, 24),
                TailrecFactorialTestData(5, 120),
                TailrecFactorialTestData(6, 720),
                TailrecFactorialTestData(7, 5040),
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

private tailrec fun factorial(n: Int, beforeEvaluated: Int): Int {
    if (n == 0) {
        return beforeEvaluated
    }
    return factorial(n - 1, beforeEvaluated * n)
}

private data class TailrecFactorialTestData(
    val n: Int,
    val expected: Int
)
