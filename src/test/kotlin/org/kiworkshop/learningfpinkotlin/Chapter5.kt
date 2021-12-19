package org.kiworkshop.learningfpinkotlin

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Chapter5 : StringSpec({
    "연습문제 5-1" {
        val intList: FunnyList<Int> = getIntList()
    }

    "연습문제 5-2" {
        val doubleList: FunnyList<Double> =
            FunnyList.Cons(
                1.0,
                FunnyList.Cons(
                    2.0,
                    FunnyList.Cons(3.0, FunnyList.Cons(4.0, FunnyList.Cons(5.0, FunnyList.Nil)))
                )
            )
    }

    "연습문제 5-3" {
        getIntList().getHead() shouldBe 1
    }

    "연습문제 5-4" {
        getIntList().drop(2).getHead() shouldBe 3
    }

    "연습문제 5-5" {
        getIntList().dropWhile { it > 2 }.getHead() shouldBe 3
    }

    "연습문제 5-6" {
        val results = getIntList().take(2)
        results.getHead() shouldBe 1
        results.getTail().getHead() shouldBe 2
    }

    "연습문제 5-7" {
        (getIntList().takeWhile { it > 2 }).getHead() shouldBe 3
        (getIntList().takeWhile { it > 5 }) shouldBe FunnyList.Nil
    }
})

fun getIntList(): FunnyList<Int> =
    FunnyList.Cons(
        1,
        FunnyList.Cons(
            2,
            FunnyList.Cons(3, FunnyList.Cons(4, FunnyList.Cons(5, FunnyList.Nil)))
        )
    )
