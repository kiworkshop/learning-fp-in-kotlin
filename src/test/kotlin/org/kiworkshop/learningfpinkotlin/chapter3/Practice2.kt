package org.kiworkshop.learningfpinkotlin.chapter3

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Practice2 : FreeSpec({
    fun power(x: Double, n: Int): Double = if (n <= 0) 1.0 else x * power(x, n - 1)

    "X의 n 승을 구하는 함수를 재귀로 구현해 보자" {
        power(5.0, 0) shouldBe 1.0
        power(123123.0, 0) shouldBe 1.0
        power(2.0, 2) shouldBe 4.0
        power(3.0, 3) shouldBe 27.0
        power(12.0, 5) shouldBe 248832.0
    }
})
