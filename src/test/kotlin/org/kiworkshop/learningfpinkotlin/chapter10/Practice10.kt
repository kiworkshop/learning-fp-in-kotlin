package org.kiworkshop.learningfpinkotlin.chapter10

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.chapter10.list.monad.emptyFunStream
import org.kiworkshop.learningfpinkotlin.chapter10.list.monad.funStreamOf
import org.kiworkshop.learningfpinkotlin.chapter10.list.monad.printFunStream

class Practice10 : FreeSpec() {
    init {
        "FunStream에 출력 함수 printFunStream()를 작성해 보자." {
            val stream = funStreamOf(1, 2, 3)
            stream.printFunStream() shouldBe "[1, 2, 3]"
            emptyFunStream<Int>().printFunStream() shouldBe "[]"
        }
    }
}
