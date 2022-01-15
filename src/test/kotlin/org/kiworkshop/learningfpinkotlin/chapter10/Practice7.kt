package org.kiworkshop.learningfpinkotlin.chapter10

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Practice7 : FreeSpec() {
    init {
        "함수 합성 관점에서 리스트 모나드가 모나드 법칙 세 가지를 만족하는지 확인하라." {
            val f = { a: Int -> Cons(a * 2, Nil) }
            val g = { a: Int -> Cons(a + 1, Nil) }
            val h = { a: Int -> Cons(a * 10, Nil) }
            val pure = { a: Int -> Cons(a, Nil) }

            (pure compose f)(10) shouldBe f(10)
            (f compose pure)(10) shouldBe f(10)
            ((f compose g) compose h)(10) shouldBe (f compose (g compose h))(10)
        }
    }
}
