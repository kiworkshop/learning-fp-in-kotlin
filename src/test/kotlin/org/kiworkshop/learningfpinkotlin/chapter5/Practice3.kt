package org.kiworkshop.learningfpinkotlin.chapter5

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.MyFunList.Cons
import org.kiworkshop.learningfpinkotlin.MyFunList.Nil
import org.kiworkshop.learningfpinkotlin.getHead
import org.kiworkshop.learningfpinkotlin.getTail

class Practice3 : FreeSpec() {
    init {
        "리스트의 첫 번째 값을 가져오는 getHead 함수를 작성해 보자."{
            val simpleList = Cons(1, Nil)
            val intList = Cons(1, Cons(2, Cons(3, Cons(4, Cons(5, Nil)))))
            val doubleList = Cons(1.0, Cons(2.0, Cons(3.0, Cons(4.0, Cons(5.0, Nil)))))
            val emptyList = Nil

            intList.getHead() shouldBe 1
            doubleList.getHead() shouldBe 1.0

            shouldThrow<NoSuchElementException>(simpleList.getTail()::getHead)
            @Suppress("IMPLICIT_NOTHING_TYPE_ARGUMENT_IN_RETURN_POSITION")
            shouldThrow<NoSuchElementException>(emptyList::getHead)
        }
    }
}
