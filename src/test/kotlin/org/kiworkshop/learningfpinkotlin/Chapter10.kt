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
})