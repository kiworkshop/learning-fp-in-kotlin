package org.kiworkshop.learningfpinkotlin.chapter10

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.chapter10.list.monad.FunStream.Cons
import org.kiworkshop.learningfpinkotlin.chapter10.list.monad.FunStream.Nil
import org.kiworkshop.learningfpinkotlin.chapter10.list.monad.emptyFunStream
import org.kiworkshop.learningfpinkotlin.chapter10.list.monad.funStreamOf
import kotlin.Nothing

class Practice9 : FreeSpec() {
    init {
        "FunStream에 리스트 생성 함수 funStreamOf를 작성해 보자." {
            funStreamOf(1, 2, 3) shouldBe Cons({ 1 }, { Cons({ 2 }, { Cons({ 3 }, { Nil }) }) })
            emptyFunStream<Nothing>() shouldBe Nil
        }
    }
}
