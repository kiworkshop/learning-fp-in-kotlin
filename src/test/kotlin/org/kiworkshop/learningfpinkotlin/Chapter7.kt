package org.kiworkshop.learningfpinkotlin

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Chapter7 : StringSpec({
    "UsageOfMaybeFunctor" {
        println(FJust(10).fmap { it + 10 })
        println(FNothing.fmap { a: Int -> a + 10 })
    }

    "Maybe 펑터 법칙 검증" {
        val maybeInt: MaybeFunctor<Int> = FJust(7)
        val maybeNothing: MaybeFunctor<Int> = FNothing

        // 펑터 제1 법칙
        fun <T> identity(x: T): T = x

        maybeInt.fmap { identity(it) } shouldBe identity(FJust(7))
        maybeNothing.fmap { identity(it) } shouldBe identity(Nothing)

        // 펑터 제2 법칙
        fun g(x: Int) = x + 1
        fun f(x: Int) = x * x

        maybeInt.fmap { f(g(it)) } shouldBe maybeInt.fmap { g(it) }.fmap { f(it) }
        maybeNothing.fmap { f(g(it)) } shouldBe maybeNothing.fmap { g(it) }.fmap { f(it) }
    }

    "7-1" {
        funListFunctorOf(1, 2, 3, 4).fmap { it + 10 } shouldBe funListFunctorOf(11, 12, 13, 14)
        funListFunctorOf<Int>().fmap { it + 10 } shouldBe FunListFunctor.Nil

        funListFunctorOf(1, 2, 3).first() shouldBe 1
        shouldThrow<NoSuchElementException> { funListFunctorOf<Int>().first() }

        funListFunctorOf(1, 2, 3 ,4 ,5).size shouldBe 5
        funListFunctorOf<Int>().size shouldBe 0
    }

    "7-2" {
        // 제1 법칙 검증
        fun <T> identity(x: T): T = x

        funListFunctorOf(1, 2, 3).fmap { identity(it) } shouldBe
                identity(funListFunctorOf(1, 2, 3))
        funListFunctorOf<Int>().fmap { identity(it) } shouldBe
                identity(funListFunctorOf())

        // 제2 법칙 검증
        val g = { x: Int -> x + 1 }
        val f = { x: Int -> x * x }

        funListFunctorOf(1, 2, 3).fmap { f(g(it)) } shouldBe
                funListFunctorOf(1, 2, 3).fmap { g(it) }.fmap { f(it) }
        funListFunctorOf<Int>().fmap { f(g(it)) } shouldBe
                funListFunctorOf<Int>().fmap { g(it) }.fmap { f(it) }
    }
})