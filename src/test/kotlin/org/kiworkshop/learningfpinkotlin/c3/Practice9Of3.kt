package org.kiworkshop.learningfpinkotlin.c3

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe

class Practice9Of3 : ShouldSpec({
    context("두 자연수가 주어지면") {
        should("최대 공약수를 구한다") {
            listOf(
                GcdTestData(1, 1, 1),
                GcdTestData(2, 4, 2),
                GcdTestData(21, 14, 7),
                GcdTestData(9, 12, 3),
                GcdTestData(7, 9, 1),
                GcdTestData(4, 4, 4),
            ).forEach { (first, second, expected) ->
                val result = gcd(first, second)

                result shouldBe expected
            }
        }
    }
})

private fun gcd(first: Int, second: Int): Int {
    val mod = first % second
    if (mod == 0) {
        return second
    }
    return gcd(second, mod)
}

private data class GcdTestData(
    val first: Int,
    val second: Int,
    val expected: Int,
)
