package org.kiworkshop.learningfpinkotlin

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.FunList.*

class Chapter5 : StringSpec({
    "5-1" {
        val list: FunList<Int> = Cons(1, Cons(2, Cons(3, Cons(4, Cons(5, Nil)))))
    }

    "5-2" {
        val list: FunList<Double> = Cons(1.0, Cons(2.0, Cons(3.0, Cons(4.0, Cons(5.0, Nil)))))
    }

    "5-3" {
        val list = Cons(1, Cons(2, Cons(3, Nil)))

        list.getHead() shouldBe 1
        list.getTail().getHead() shouldBe 2
    }

    "5-4" {
        Nil.appendTail(1).appendTail(2).appendTail(3)
            .drop(1) shouldBe Cons(2, Cons(3, Nil))
        Nil.appendTail(1).appendTail(2).appendTail(3)
            .drop(2) shouldBe Cons(3, Nil)
        Nil.appendTail(1).appendTail(2).appendTail(3)
            .drop(3) shouldBe Nil
        Nil.appendTail(1).appendTail(2).appendTail(3)
            .drop(4) shouldBe Nil
    }

    "5-5" {
        Nil.appendTail(1).appendTail(2).appendTail(3)
            .dropWhile { it < 2 } shouldBe Cons(2, Cons(3, Nil))
        Nil.appendTail(1).appendTail(2).appendTail(3)
            .dropWhile { it < 100 } shouldBe Nil
        funListOf<Int>().dropWhile { it < 5 } shouldBe Nil
    }

    "5-6" {
        Nil.appendTail(1).appendTail(2).appendTail(3)
            .take(0) shouldBe Nil
        Nil.appendTail(1).appendTail(2).appendTail(3)
            .take(2) shouldBe Cons(1, Cons(2, Nil))
        Nil.appendTail(1).appendTail(2).appendTail(3)
            .take(4) shouldBe Cons(1, Cons(2, Cons(3, Nil)))
    }

    "5-7" {
        Nil.appendTail(1).appendTail(2).appendTail(3)
            .takeWhile { it < 1 } shouldBe Nil
        Nil.appendTail(1).appendTail(2).appendTail(3)
            .takeWhile { it < 3 } shouldBe Cons(1, Cons(2, Nil))
        Nil.appendTail(1).appendTail(2).appendTail(3)
            .takeWhile { it < 100 } shouldBe Cons(1, Cons(2, Cons(3, Nil)))
    }

    "5-8" {
        funListOf<Int>().indexedMap { i, e -> i * e } shouldBe Nil
        funListOf(1, 2, 3, 4, 5).indexedMap { i, e -> i * e } shouldBe funListOf(0, 2, 6, 12, 20)
    }

    "5-9" {
        shouldThrow<NoSuchElementException> { funListOf<Int>().maximumByFoldLeft() }
        funListOf(3, -6, 5, 2, 4).maximumByFoldLeft() shouldBe 5
    }

    "5-10" {
        funListOf<Int>().filterByFoldLeft { it % 2 == 0 } shouldBe Nil
        funListOf(1, 2, 3, 4, 5).filterByFoldLeft { it % 2 == 0 } shouldBe funListOf(2, 4)
        funListOf(1, 2, 3, 4, 5).filterByFoldLeft { it > 5 } shouldBe Nil
    }

    "5-11" {
        funListOf<Int>().reverseByFoldRight() shouldBe Nil
        funListOf(1, 2, 3, 4, 5).reverseByFoldRight() shouldBe funListOf(5, 4, 3, 2, 1)
    }

    "5-12" {
        funListOf<Int>().filterByFoldRight { it % 2 == 0 } shouldBe Nil
        funListOf(1, 2, 3, 4, 5).filterByFoldRight { it % 2 == 0 } shouldBe funListOf(2, 4)
        funListOf(1, 2, 3, 4, 5).filterByFoldRight { it > 5 } shouldBe Nil
    }

    "5-13" {
        funListOf<Char>().zip(funListOf(1, 2)) shouldBe Nil
        funListOf('a', 'b', 'c', 'd', 'e').zip(funListOf(1, 2, 3)) shouldBe funListOf(
            Pair('a', 1),
            Pair('b', 2),
            Pair('c', 3)
        )
    }

    "5-14" {
        funListOf(1, 2, 3).associate { Pair(it, "v$it") } shouldBe mapOf(
            1 to "v1",
            2 to "v2",
            3 to "v3"
        )
    }

    "5-15" {
        funListOf(1, 2, 3).groupBy { "k$it" } shouldBe mapOf(
            "k1" to funListOf(1),
            "k2" to funListOf(2),
            "k3" to funListOf(3)
        )
    }

    "5-16" {
        fun imperativeWay(intList: List<Int>): Int {
            for (value in intList) {
                val doubleValue = value * value
                if (doubleValue < 10) {
                    return doubleValue
                }
            }
            throw NoSuchElementException("There is no value")
        }

        fun functionalWay(intList: List<Int>): Int = intList.map { it * it }.filter { it < 10 }.first()
        fun functionalWay2(intList: List<Int>): Int = intList.map { it * it }.first { it < 10 }

        fun realFunctionalWay(intList: List<Int>): Int = intList.asSequence().map { it * it }.filter { it < 10 }.first()

//        val bigIntList = (1..10_000_000).toList()
        val bigIntList = (10_000_000 downTo 1).toList()

        var start = System.currentTimeMillis()
        imperativeWay(bigIntList)
        println("${System.currentTimeMillis() - start} ms")

        start = System.currentTimeMillis()
        functionalWay(bigIntList)
        println("${System.currentTimeMillis() - start} ms")

        start = System.currentTimeMillis()
        functionalWay2(bigIntList)
        println("${System.currentTimeMillis() - start} ms")

        start = System.currentTimeMillis()
        realFunctionalWay(bigIntList)
        println("${System.currentTimeMillis() - start} ms")
    }
})