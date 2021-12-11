package org.kiworkshop.learningfpinkotlin.chapter4

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Practice6 : FreeSpec() {
    infix fun <F, G, R> ((F) -> R).compose(g: (G) -> F) = { p: G -> this(g(p)) }

    private fun maxInList(numbers: List<Int>) = numbers.maxOrNull()!!
    private fun square(number: Int) = number * number

    init {
        "연습문제 4-5에서 작성한 함수를 compose 함수를 사용해서 다시 작성해 보자."{
            val composedFn = ::square compose ::maxInList
            composedFn(listOf(1, 2, 3)) shouldBe 9
            composedFn(listOf(2, 4, 3, 2)) shouldBe 16
            composedFn(listOf(1, 1, 1, 1)) shouldBe 1
            composedFn(listOf(5, 3, 12, 9)) shouldBe 144
        }
    }
}
