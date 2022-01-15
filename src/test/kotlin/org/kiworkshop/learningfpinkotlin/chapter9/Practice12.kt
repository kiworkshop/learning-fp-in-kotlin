package org.kiworkshop.learningfpinkotlin.chapter9

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Practice12 : FreeSpec() {
    init {
        "foldMap 함수를 사용하여 폴더블 리스트에 contains 함수를 구현해 보자." {
            val list = Cons(1, Cons(2, Cons(3, Cons(4, Nil))))

            list.contains(1) shouldBe true
            list.contains(4) shouldBe true
            list.contains(0) shouldBe false
            list.contains(5) shouldBe false
        }
    }
}
