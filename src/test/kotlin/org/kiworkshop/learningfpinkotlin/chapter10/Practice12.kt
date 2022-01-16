package org.kiworkshop.learningfpinkotlin.chapter10

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.chapter10.list.monad.emptyFunStream
import org.kiworkshop.learningfpinkotlin.chapter10.list.monad.fmap
import org.kiworkshop.learningfpinkotlin.chapter10.list.monad.funStreamOf

class Practice12 : FreeSpec() {
    init {
        "FunStream에 fmap 함수를 구현해 보자." {
            val stream = funStreamOf(1, 2, 3)

            stream fmap { it + 1 } shouldBe funStreamOf(2, 3, 4)
            stream fmap { it * 2 } shouldBe funStreamOf(2, 4, 6)

            emptyFunStream<Int>() fmap { x: Int -> x + 10 } shouldBe emptyFunStream<Int>()
        }
    }
}
