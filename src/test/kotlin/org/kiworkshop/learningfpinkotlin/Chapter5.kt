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

    "연습문제 5-8" {
        println(getIntList().indexedMap(2) { index, value -> index + value })
    }

    "연습문제 5-9" {
        getIntList().maximumByFoldLeft() shouldBe 5
        funnyListOf(10, 5).maximumByFoldLeft() shouldBe 10
    }

    "연습문제 5-10" {
        println(getIntList().filterByFoldLeft { it > 2 })
    }

    "연습문제 5-11" {
        funnyListOf(1, 3, 10).reverseByFoldRight().getHead() shouldBe 1
    }

    "연습문제 5-12" {
        (funnyListOf(1, 3, 10).filterByFoldRight { it > 3 }).getHead() shouldBe 10
    }

    "연습문제 5-13" {
        funnyListOf(1, 2, 3).zip(funnyListOf(4, 5, 6)).getHead().toString() shouldBe "(1, 4)"
    }

    // 5-14, 5-15 ㅠㅠ
    "연습문제 5-16" {
        val bigIntList = (10000000 downTo 1).toList()
        val start = System.currentTimeMillis()
        imperativeWay(bigIntList)
        println("Imperative Way ${System.currentTimeMillis() - start} ms")

        val fStart = System.currentTimeMillis()
        functionalWay(bigIntList)
        println("functionalWay ${System.currentTimeMillis() - fStart} ms")

        val rfStart = System.currentTimeMillis()
        realFunctionalWay(bigIntList)
        println("Real functionalWay ${System.currentTimeMillis() - rfStart} ms")
    }

    "연습문제 5-17" {
        funnyStreamOf(1, 2, 3).sum() shouldBe 6
        funnyStreamOf(1, 2, 3, 4, 5).sum() shouldBe 15
    }

    "연습문제 5-18" {
        funnyStreamOf(1, 2, 3).product() shouldBe 6
        funnyStreamOf(2, 3, 4).product() shouldBe 24
    }

    "연습문제 5-19" {
        FunnyStream.Nil.appendTail(1).getHead() shouldBe 1
        funnyStreamOf(1).appendTail(2).getTail().getHead() shouldBe 2
    }

    "연습문제 5-20" {
        val result = funnyStreamOf(1, 2, 3, 4).filter { it in 2..3 }
        result.getHead() shouldBe 2
        result.getTail().getHead() shouldBe 3
    }

    "연습문제 5-21" {
        val result = funnyStreamOf(1, 2, 3, 4, 5).map { it * 2 }
        result.getHead() shouldBe 2
        result.getTail().getHead() shouldBe 4
    }

    "연습문제 5-22" {
        funnyStreamOf(1, 2, 3).take(1).getHead() shouldBe 1
        funnyStreamOf(1, 2, 3).take(1).getTail() shouldBe FunnyStream.Nil
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

fun imperativeWay(intList: List<Int>): Int {
    for (value in intList) {
        val doubleValue = value * value
        if (doubleValue < 10) {
            return doubleValue
        }
    }
    throw NoSuchElementException("There is no value")
}

fun functionalWay(intList: List<Int>): Int =
    intList.map { n -> n * n }.first { n -> n < 10 }

fun realFunctionalWay(intList: List<Int>): Int = intList.asSequence().map { n -> n * n }.first { n -> n < 10 }
