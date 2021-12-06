package org.kiworkshop.learningfpinkotlin.chapter3

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Practice13 : FreeSpec({
    // fun power(x: Double, n: Int): Double = if (n <= 0) 1.0 else x * power(x, n - 1)

    tailrec fun power(x: Double, n: Int, acc: Double): Double = if (n <= 0) acc else power(x, n - 1, acc * x)
    fun power(x: Double, n: Int): Double = power(x, n, 1.0)

    "연습문제 3-2에서 작성한 power 함수가 꼬리 재귀인지 확인해 보자. 만약 꼬리 재귀가 아니라면 개선해 보자." {
        power(5.0, 0) shouldBe 1.0
        power(123123.0, 0) shouldBe 1.0
        power(2.0, 2) shouldBe 4.0
        power(3.0, 3) shouldBe 27.0
        power(12.0, 5) shouldBe 248832.0
    }
})
