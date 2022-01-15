package org.kiworkshop.learningfpinkotlin.chapter10

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Practice5 : FreeSpec() {
    init {
        "리스트 모나드가 오른쪽 법칙을 만족하는지 확인해 보자." {
            val pure = { x: Int -> Cons(x, Nil) }
            val m = Cons(5, Nil)

            m flatMap pure shouldBe m
        }
    }
}
