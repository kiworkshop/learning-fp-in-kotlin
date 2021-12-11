package org.kiworkshop.learningfpinkotlin.chapter4

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Practice5 : FreeSpec() {
    private fun maxInList(numbers: List<Int>) = numbers.maxOrNull()!!
    private fun square(number: Int) = number * number

    init {
        "숫자(Int)의 리스트를 받아서 최댓값의 제곱을 구하는 함수를 작성해 보자. 이떄 반드시 max 함수와 power 함수를 만들어 합성해야 한다."{
            val composedFn = { numbers: List<Int> -> square(maxInList(numbers)) }
            composedFn(listOf(1, 2, 3)) shouldBe 9
            composedFn(listOf(2, 4, 3, 2)) shouldBe 16
            composedFn(listOf(1, 1, 1, 1)) shouldBe 1
            composedFn(listOf(5, 3, 12, 9)) shouldBe 144
        }
    }
}
