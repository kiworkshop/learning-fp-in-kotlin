package org.kiworkshop.learningfpinkotlin.seonghunlee

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.seonghunlee.FunList.Cons
import org.kiworkshop.learningfpinkotlin.seonghunlee.FunList.Nil

class Chapter5 : StringSpec({
    "ch5-1" {
        val intList: FunList<Int> = Cons(1, Cons(2, Cons(3, Cons(4, Cons(5, Nil)))))
    }
    "ch5-2" {
        val doubleList: FunList<Double> = Cons(1.0, Cons(2.0, Cons(3.0, Cons(4.0, Cons(5.0, Nil)))))
    }
    "ch5-3" {
        Cons(11, Cons(5, Nil)).getHead() shouldBe 11
    }
    "ch5-4" {
        Cons(1, Cons(2, Cons(3, Cons(4, Cons(5, Nil))))).drop(2) shouldBe Cons(3, Cons(4, Cons(5, Nil)))
    }
    "ch5-5" {
        Cons(1, Cons(2, Cons(3, Cons(4, Cons(5, Nil))))).dropWhile { x -> x == 3 } shouldBe Cons(4, Cons(5, Nil))
    }
    "ch5-6" {
        Cons(1, Cons(2, Cons(3, Cons(4, Cons(5, Nil))))).take(3) shouldBe Cons(1, Cons(2, Cons(3, Nil)))
    }
    "ch5-7" {
        Cons(1, Cons(2, Cons(3, Cons(4, Cons(0, Nil))))).takeWhile { x -> x <= 3 } shouldBe Cons(
            1,
            Cons(2, Cons(3, Nil))
        )
    }
    "ch5-8" {
        Cons(1, Cons(2, Cons(3, Cons(4, Cons(5, Nil))))).indexedMap { i, it -> it + i } shouldBe
            Cons(1, Cons(3, Cons(5, Cons(7, Cons(9, Nil)))))
    }
    "ch5-9" {
        funListOf(1, 2, 3).maximumByFoldLeft() shouldBe 3
    }
    "ch5-10" {
        funListOf(1, 3, 5, 2, 9).filterByFoldLeft { it > 4 } shouldBe funListOf(5, 9)
    }
    "ch5-11" {
        funListOf(1, 2, 3).reverseByFoldRight() shouldBe funListOf(3, 2, 1)
    }
    "ch5-12" {
        funListOf(1, 2, 3).filterByFoldRight { x -> x >= 2 } shouldBe funListOf(2, 3)
    }
    "ch5-13" {
        funListOf(1, 2, 3, 4).zip(funListOf(5, 6, 7)) shouldBe funListOf(Pair(1, 5), Pair(2, 6), Pair(3, 7))
    }
    "ch5-14" {
        val intList = funListOf(1, 2, 3)

        intList.associate { x -> Pair(x, x * 2) } shouldBe mapOf(Pair(1, 2), Pair(2, 4), Pair(3, 6))
    }
    "ch5-15" {
        funListOf(2, 4, 6, 7).groupBy { x -> x % 2 } shouldBe mapOf(0 to funListOf(2, 4, 6), 1 to funListOf(7))
    }
    "ch5-16" {
        val bigIntList = (10000000 downTo 1).toList()
//        val bigIntList = (1..10000000).toList()

        var start = System.currentTimeMillis()
        imperativeWay(bigIntList)
        println("${System.currentTimeMillis() - start} ms")

        start = System.currentTimeMillis()
        functionalWay(bigIntList)
        println("${System.currentTimeMillis() - start} ms")

        start = System.currentTimeMillis()
        realFunctionalWay(bigIntList)
        println("${System.currentTimeMillis() - start} ms")
    }
    "ch5-17" {
        funStreamOf(1, 2, 3).sum() shouldBe 6
    }
    "ch5-18" {
        funStreamOf(1, 2, 3).product() shouldBe 6
    }
    "ch5-19" {
        funStreamOf(1, 2, 3).appendTail(4) shouldBe funStreamOf(1, 2, 3, 4)
        funStreamOf(1, 2, 3).appendTail(4).getTail().getTail().getTail().getHead() shouldBe
            funStreamOf(1, 2, 3, 4).getTail().getTail().getTail().getHead()

        funStreamOf(1, 2, 3).appendTail(4).getTail().getTail().getTail().getTail() shouldBe
            funStreamOf(1, 2, 3, 4).getTail().getTail().getTail().getTail()
    }
    "ch5-20" {
        funStreamOf(1, 2, 3, 4).filter { x -> x % 2 == 0 } shouldBe funStreamOf(2, 4)
    }
    "ch5-21" {
        funStreamOf(1, 2, 3, 4).map { x -> x * 2 } shouldBe funStreamOf(2, 4, 6, 8)
    }
    "ch5-22" {
        (1..500).toFunStream().take(5) shouldBe funStreamOf(1, 2, 3, 4, 5)
    }
    "ch5-23" {
        funListOf(1, 2, 3).toString1() shouldBe "[1, 2, 3]"
    }
    "ch5-24" {
        (1..100000).sumSqrtBiggerThanOneThousand() shouldBe 131
    }
})
