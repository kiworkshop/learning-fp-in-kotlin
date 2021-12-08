package org.kiworkshop.learningfpinkotlin.chapter4

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Practice2 : FreeSpec() {

    private fun <P1, P2, P3, R> ((P1, P2, P3) -> R).partial1(p1: P1): (P2, P3) -> R = { p2, p3 -> this(p1, p2, p3) }
    private fun <P1, P2, P3, R> ((P1, P2, P3) -> R).partial2(p2: P2): (P1, P3) -> R = { p1, p3 -> this(p1, p2, p3) }
    private fun <P1, P2, P3, R> ((P1, P2, P3) -> R).partial3(p3: P3): (P1, P2) -> R = { p1, p2 -> this(p1, p2, p3) }

    init {
        "매개변수 3개를 받는 부분 적용 함수 3개를 직접 구현하라"{
            val helloFunc: (String, String, String) -> String = { a, b, c -> a + b + c }
            helloFunc.partial1("Hello ")("World", "!") shouldBe "Hello World!"
            helloFunc.partial2("World")("Hello ", "!") shouldBe "Hello World!"
            helloFunc.partial3("!")("Hello ", "World") shouldBe "Hello World!"
        }
    }
}
