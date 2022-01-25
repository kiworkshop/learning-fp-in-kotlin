package org.kiworkshop.learningfpinkotlin

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Chapter10 : StringSpec({
    "10-1" {
        // FunListMonad in Monad.kt
    }
    "10-2" {
        // fmap
        Cons(10, Nil).fmap { it + 10 } shouldBe Cons(20, Nil)
        Cons(10, Cons(11, Nil)).fmap { it + 10 } shouldBe Cons(20, Cons(21, Nil))
        Nil.fmap { x: Int -> x + 10 } shouldBe Nil

        // pure
        FunListMonad.pure(10) shouldBe Cons(10, Nil)
        Nil.pure(10) shouldBe Nil

        // apply
        Cons({ x: Int -> x }, Cons({ x: Int -> x + 10 }, Nil)) apply Cons(10, Cons(11, Nil)) shouldBe Cons(10, Cons(11, Cons(20, Cons(21, Nil))))
        Nil as FunListMonad<(Int) -> Int> apply Cons(10, Cons(20, Nil)) shouldBe Nil

        // leadTo
        Cons(10, Nil).leadTo(Nil) shouldBe Nil
        Cons(10, Nil).leadTo(Cons(20, Nil)) shouldBe Cons(20, Nil)
        Cons(10, Cons(11, Nil)).leadTo(Cons(20, Cons(21, Nil))) shouldBe Cons(20, Cons(21, Cons(20, Cons(21, Nil))))
        Nil.leadTo(Cons(10, Cons(11, Nil))) shouldBe Nil

        // flatMap
        Cons(10, Cons(11, Nil)).flatMap { Cons(it, Cons(it + 10, Nil)) } shouldBe Cons(10, Cons(20, Cons(11, Cons(21, Nil))))
        Nil.flatMap { x: Int -> Cons(x, Cons(x + 10, Nil)) } shouldBe Nil
    }

    "10-3" {
        class D4(val value: MaybeMonad<String>)
        class C4(val d: D4)
        class B4(val c: MaybeMonad<C4>)
        class A4(val b: MaybeMonad<B4>)

        fun getValueOfD4(a: A4): MaybeMonad<String> = a.b.flatMap { it.c }.flatMap { it.d.value }
        fun getValueOfD4_2(a: A4): MaybeMonad<String> = a.b.flatMap { it.c }.fmap { it.d }.flatMap { it.value }

        val a = A4(Just(B4(Just(C4(D4(Just("someValue")))))))
        getValueOfD4(a) shouldBe Just("someValue")
        getValueOfD4_2(a) shouldBe Just("someValue")
    }

    "10-4" {
        val x = 10
        val f = { x: Int -> Cons(x * 2, Nil) }
        val pure = { a: Int -> FunListMonad.pure(a) }

        pure(x) flatMap f shouldBe f(x)
    }

    "10-5" {
        val pure = {a: Int -> FunListMonad.pure(a) }
        val m = Cons(10, Nil)

        m flatMap pure shouldBe m
    }

    "10-6" {
        val f = { a: Int -> Cons(a * 2, Nil) }
        val g = { a: Int -> Cons(a + 1, Nil) }
        val m = Cons(10, Nil)

        (m flatMap f) flatMap g shouldBe (m flatMap { a -> f(a) flatMap g })
    }
})