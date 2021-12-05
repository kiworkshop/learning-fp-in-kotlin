package org.kiworkshop.learningfpinkotlin.c3

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.be
import io.kotest.matchers.shouldBe
import java.math.BigDecimal

class Practice19Of3 : ShouldSpec({
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
                val result = factorial(BigDecimal(n))

                result shouldBe BigDecimal(expected)
            }
            println(factorial(BigDecimal(100_000)))
        }
    }
})

private fun factorial(n: BigDecimal): BigDecimal {
    return trampoline(factorial(n, BigDecimal.ONE))
}

private fun factorial(n: BigDecimal, beforeEvaluated: BigDecimal): Bounce<BigDecimal> {
    if (n == BigDecimal.ZERO) {
        return Done(beforeEvaluated)
    }
    return More { factorial(n - BigDecimal.ONE, beforeEvaluated * n) }
}
