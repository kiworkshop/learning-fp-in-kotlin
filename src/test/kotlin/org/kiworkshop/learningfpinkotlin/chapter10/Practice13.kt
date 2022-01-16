package org.kiworkshop.learningfpinkotlin.chapter10

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.chapter10.list.monad.apply
import org.kiworkshop.learningfpinkotlin.chapter10.list.monad.funStreamOf
import org.kiworkshop.learningfpinkotlin.chapter10.list.monad.pure

class Practice13 : FreeSpec() {
    init {
        "FunStream에 pure, apply 함수를 추가해 보자." {
            val stream1 = funStreamOf(1, 2, 3)
            val stream2 = funStreamOf(4, 5, 6)
            val stream3 = funStreamOf<(Int) -> Int>({ it * 2 }, { it + 1 })

            stream1.pure(5) shouldBe funStreamOf(5)

            stream3 apply stream1 shouldBe funStreamOf(2, 4, 6, 2, 3, 4)
            stream3 apply stream2 shouldBe funStreamOf(8, 10, 12, 5, 6, 7)

//            stream1 _apply stream3 shouldBe funStreamOf(2, 4, 6, 2, 3, 4)
//            stream2 _apply stream3 shouldBe funStreamOf(8, 10, 12, 5, 6, 7)
        }
    }
}
