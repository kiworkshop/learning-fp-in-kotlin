package org.kiworkshop.learningfpinkotlin.c3

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe

class Practice14Of3 : ShouldSpec({
    context("음이 아닌 정수 n이 주어졌을 때") {
        should("n의 이진수를 구한다") {
            listOf(
                BinaryTestData(0, "0"),
                BinaryTestData(1, "1"),
                BinaryTestData(2, "10"),
                BinaryTestData(3, "11"),
                BinaryTestData(4, "100"),
                BinaryTestData(5, "101"),
                BinaryTestData(6, "110"),
                BinaryTestData(7, "111"),
            ).forEach { (n, expected) ->
                val result = BinaryUtils.toBinaryImproved(n)

                result shouldBe expected
            }
        }
    }
})
