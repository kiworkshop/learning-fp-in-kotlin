package org.kiworkshop.learningfpinkotlin.chapter8

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.applicativeFunListOf

class Practice2 : FreeSpec() {
    init {
        "7장에서 만든 리스트 펑터를 Applicative의 인스턴스로 만들고 테스트해 보자."{
            val list = applicativeFunListOf(1, 2, 3, 4, 5)
            val ff = applicativeFunListOf({ a: Int -> a + 1 })

            list.size() shouldBe 5
            list.first() shouldBe 1
            list.fmap { it + 1 } shouldBe applicativeFunListOf(2, 3, 4, 5, 6)

            list.pure(5) shouldBe applicativeFunListOf(5)

            list.apply(ff) shouldBe applicativeFunListOf(2, 3, 4, 5, 6)
        }
    }
}
