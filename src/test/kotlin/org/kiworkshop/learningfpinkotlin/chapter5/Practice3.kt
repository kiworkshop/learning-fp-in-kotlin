package org.kiworkshop.learningfpinkotlin.chapter5

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.EmptyMyFunList
import org.kiworkshop.learningfpinkotlin.MyFunListOf
import org.kiworkshop.learningfpinkotlin.getHead
import org.kiworkshop.learningfpinkotlin.getTail

class Practice3 : FreeSpec() {
    init {
        "리스트의 첫 번째 값을 가져오는 getHead 함수를 작성해 보자."{
            val simpleList = MyFunListOf(1)
            val intList = MyFunListOf(1, 2, 3)
            val doubleList = MyFunListOf(1.0, 2.0, 3.0)
            val emptyList = EmptyMyFunList<Int>()

            intList.getHead() shouldBe 1
            doubleList.getHead() shouldBe 1.0

            shouldThrow<NoSuchElementException>(simpleList.getTail()::getHead)
            @Suppress("IMPLICIT_NOTHING_TYPE_ARGUMENT_IN_RETURN_POSITION")
            shouldThrow<NoSuchElementException>(emptyList::getHead)
        }
    }
}
