package org.kiworkshop.learningfpinkotlin.chapter10

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Practice6 : FreeSpec() {
    init {
        "리스트 모나드가 결합 법칙을 만족하는지 확인해 보자." {
            val f = { a: Int -> Just(a * 2) }
            val g = { a: Int -> Just(a + 1) }
            val m = Cons(10, Nil)

            (m flatMap f) flatMap g shouldBe (m flatMap { x: Int -> f(x) flatMap g })
        }
    }
}
