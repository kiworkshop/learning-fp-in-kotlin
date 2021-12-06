package org.kiworkshop.learningfpinkotlin.chapter3

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import kotlin.math.sqrt

class Practice17 : FreeSpec() {
    private fun sqrtAndDivide(n: Double): Double = divide(sqrt(n))
    private fun divide(n: Double): Double = when {
        (n / 2) < 1 -> n / 2
        else -> sqrtAndDivide(n / 2)
    }

    init {
        """
       입력값 n의 제곱근을 2로 나눈 값이 1보다 작을 때까지 반복하고,
       최초의 1보다 작은 값을 반환하는 함수를 상호 재귀를 사용하여 구현하라.
       이때 입력값 n은 2보다 크다.
    """{
            sqrtAndDivide(4.0) shouldBe 0.5
            sqrtAndDivide(2.0) shouldBe 0.7071067811865476
            sqrtAndDivide(16.0) shouldBe 0.7071067811865476
            sqrtAndDivide(64.0) shouldBe 0.5
        }
    }
}
