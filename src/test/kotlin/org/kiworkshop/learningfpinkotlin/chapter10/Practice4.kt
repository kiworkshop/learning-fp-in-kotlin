package org.kiworkshop.learningfpinkotlin.chapter10

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Practice4 : FreeSpec() {
    init {
        "리스트 모나드가 왼쪽 항등 법칙을 만족하는지 확인해 보자." {
            val x = 2
            val f = { x: Int -> Cons(x * 2, Nil) }
            val pure = { x: Int -> Cons(x, Nil) }
            pure(x) flatMap f shouldBe f(x)
        }
    }
}
