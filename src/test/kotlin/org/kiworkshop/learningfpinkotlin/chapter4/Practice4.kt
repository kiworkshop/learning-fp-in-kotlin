package org.kiworkshop.learningfpinkotlin.chapter4

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import kotlin.math.min

class Practice4 : FreeSpec() {

    private fun <P1, P2, R> ((P1, P2) -> R).curried() = { a: P1 -> { b: P2 -> this(a, b) } }

    private fun simpleMin(a: Int, b: Int) = min(a, b)

    init {
        "두 개의 매개변수를 받아서 작은 값을 반환하는 min 함수를 curried 함수를 사용해서 작성하라"{
            val curriedMin = ::simpleMin.curried()
            curriedMin(1)(2) shouldBe 1
            curriedMin(3)(2) shouldBe 2
            curriedMin(4)(4) shouldBe 4
            curriedMin(1)(123) shouldBe 1
            curriedMin(12542)(123) shouldBe 123
        }
    }
}
