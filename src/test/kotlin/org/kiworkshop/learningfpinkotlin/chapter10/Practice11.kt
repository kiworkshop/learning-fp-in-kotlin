package org.kiworkshop.learningfpinkotlin.chapter10

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.chapter10.list.monad.funStreamOf
import org.kiworkshop.learningfpinkotlin.chapter10.list.monad.mappend
import org.kiworkshop.learningfpinkotlin.chapter10.list.monad.mempty

class Practice11 : FreeSpec() {
    init {
        "FunStream을 모노이드로 작성해 보자." {
            val stream = funStreamOf(1, 2, 3)
            val otherStream = funStreamOf(4, 5)

            stream mappend stream.mempty() shouldBe stream
            stream.mempty() mappend stream shouldBe stream

            stream mappend otherStream shouldBe funStreamOf(1, 2, 3, 4, 5)
        }
    }
}
