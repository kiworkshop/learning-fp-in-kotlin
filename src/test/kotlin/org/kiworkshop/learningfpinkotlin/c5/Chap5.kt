package org.kiworkshop.learningfpinkotlin.c5

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.c5.FunList.Cons
import org.kiworkshop.learningfpinkotlin.c5.FunList.Nil
import kotlin.math.sqrt
import kotlin.system.measureTimeMillis

class Chap5 : StringSpec({
    "Example 5-1" {
        val intList = Cons(1, Cons(2, Cons(3, Cons(4, Cons(5, Nil)))))
    }

    "Example 5-2" {
        val doubleList = Cons(1.0, Cons(2.0, Cons(3.0, Cons(4.0, Cons(5.0, Nil)))))
    }

    "Example 5-3" {
        val intList = Nil
            .addHead(3)
            .addHead(2)
            .addHead(1)
        intList.getTail().getHead() shouldBe 2

        val four = FunList.Nil
            .appendTail(4)
        four.getHead() shouldBe 4
    }

    "Example 5-4" {
        val intList = Nil
            .addHead(3)
            .addHead(2)
            .addHead(1)
        intList.drop(2).getHead() shouldBe 3
        intList.drop(3) shouldBe Nil
        intList.drop(4) shouldBe Nil
    }

    "Example 5-5" {
        val intList = Nil
            .addHead(3)
            .addHead(2)
            .addHead(1)
        intList.dropWhile { it <= 2 }.getHead() shouldBe 3
    }

    "Example 5-6" {
        val intList = Nil
            .addHead(3)
            .addHead(2)
            .addHead(1)
        intList.take(3).getHead() shouldBe 1
        intList.take(4).getHead() shouldBe 1
        intList.take(1).getTail() shouldBe Nil
    }

    "Example 5-7" {
        val intList = Nil
            .addHead(3)
            .addHead(2)
            .addHead(1)
        val under3 = intList.takeWhile { it <= 2 }
        under3.getHead() shouldBe 1
        under3.getTail().getHead() shouldBe 2
        under3.getTail().getTail() shouldBe Nil
    }

    "Example 5-8" {
        val list = funListOf(1, 2, 3)
        val result = list.indexedMap { index, element ->
            index * element
        }
        result.getHead() shouldBe 0
        result.getTail().getHead() shouldBe 2
        result.getTail().getTail().getHead() shouldBe 6
    }

    "Example 5-9" {
        fun FunList<Int>.maximumByFoldLeft(): Int = foldLeft(0) { acc, element ->
            when {
                acc < element -> element
                else -> acc
            }
        }

        val list = funListOf(1, 3, 4, 2)
        list.maximumByFoldLeft() shouldBe 4
    }

    "Example 5-10" {
        fun <T> FunList<T>.filterByFoldLeft(predicate: (T) -> Boolean): FunList<T> =
            foldLeft(Nil) { acc: FunList<T>, element: T ->
                when {
                    predicate(element) -> acc.appendTail(element)
                    else -> acc
                }
            }

        val list = funListOf(1, 4, 3, 2)
        list.filterByFoldLeft { it >= 3 }.getHead() shouldBe 4
    }

    "Example 5-11" {
        fun <T> FunList<T>.reverseByFoldRight(): FunList<T> = foldRight(Nil) { element: T, acc: FunList<T> ->
            acc.appendTail(element)
        }

        val list = funListOf(1, 4, 3, 2)
        list.reverseByFoldRight().getHead() shouldBe 2
    }

    "Example 5-12" {
        fun <T> FunList<T>.filterByFoldRight(predicate: (T) -> Boolean): FunList<T> =
            foldRight(Nil) { element: T, acc: FunList<T> ->
                when {
                    predicate(element) -> acc.addHead(element)
                    else -> acc
                }
            }

        val list = funListOf(1, 4, 3, 2)
        list.filterByFoldRight { it >= 3 }.getHead() shouldBe 4
    }

    "Example 5-13" {
        val list1 = funListOf(1, 2, 3)
        val list2 = funListOf("a", "b", "c")

        list1.zip(list2).getHead() shouldBe (1 to "a")
    }

    "Example 5-14" {
        val list = funListOf(1, 2, 3)
        val result = list.associate { it to it + 1 }

        result[1] shouldBe 2
        result[2] shouldBe 3
        result[3] shouldBe 4
    }

    "Example 5-15" {
        val list = funListOf(4, 2, 3, 1)
        val result = list.groupBy { it % 3 }

        result[1] shouldBe funListOf(4, 1)
        result[2] shouldBe funListOf(2)
        result[0] shouldBe funListOf(3)
    }

    "Example 5-16" {
        fun imperativeWay(list: List<Int>): Int {
            for (value in list) {
                val square = value * value
                if (square < 10) {
                    return square
                }
            }

            throw NoSuchElementException()
        }

        fun functionalWay(list: List<Int>): Int = list
            .map { it * it }
            .filter { it < 10 }
            .first()

        fun realFunctionalWay(list: List<Int>): Int = list.asSequence()
            .map { it * it }
            .filter { it < 10 }
            .first()

        val list = (10000000 downTo 1).toList()

        val imperativeTime = measureTimeMillis { imperativeWay(list) }
        val functionalTime = measureTimeMillis { functionalWay(list) }
        val realFunctionalTime = measureTimeMillis { realFunctionalWay(list) }

        println("imperative: $imperativeTime, functional: $functionalTime, realFunctional: $realFunctionalTime")
    }

    "Example 5-17" {
        funStreamOf(3, 1, 2, 4).sum() shouldBe 10
    }

    "Example 5-18" {
        funStreamOf(3, 1, 2, 4).product() shouldBe 24
    }

    "Example 5-19" {
        val stream = funStreamOf(1).appendTail(4)
        stream.getTail().getHead() shouldBe 4
    }

    "Example 5-20" {
        val stream = funStreamOf(1, 3, 2, 4)

        stream.filter { it >= 3 } shouldBe funStreamOf(3, 4)
    }

    // 메서드 단독 실행해야함
    "Example 5-21" {
        val stream = funStreamOf(1, 3, 2, 4)

        stream.map { it * it } shouldBe funStreamOf(1, 9, 4, 16)

        fun funListWay(intList: FunList<Int>): Int = intList
            .map { it * it }
            .filter { it < 1000000 }
            .map { it - 2 }
            .filter { it < 1000 }
            .map { it * 10 }
            .getHead()

        fun funStreamWay(intStream: FunStream<Int>): Int = intStream
            .map { it * it }
            .filter { it < 1000000 }
            .map { it - 2 }
            .filter { it < 1000 }
            .map { it * 10 }
            .getHead()

        val list = (1..10000000).toList()
        val intList = list.toFunList()
        val intStream = list.toFunStream()

        val listTime = measureTimeMillis { funListWay(intList) }
        val streamTime = measureTimeMillis { funStreamWay(intStream) }

        println("list: $listTime, stream: $streamTime")
    }

    "Example 5-22" {
        FunStream.Nil.take(1) shouldBe FunStream.Nil
        funStreamOf(1, 2, 3).take(2) shouldBe funStreamOf(1, 2)
        funStreamOf(1, 2, 3).take(0) shouldBe FunStream.Nil
        funStreamOf(1, 2, 3).take(4) shouldBe funStreamOf(1, 2, 3)
    }

    "Example 5-23" {
        funListOf(1, 2, 3).toString("") shouldBe "[1, 2, 3]"
    }

    "Example 5-24" {
        tailrec fun getCount(stream: FunStream<Int>, count: Int = 0, sum: Double = 0.0): Int {
            if (sum > 1000.0) {
                return count
            }
            val newSum = sum + sqrt(stream.getHead().toDouble())
            return getCount(stream.getTail(), count + 1, newSum)
        }

        val naturalStream = generateFunStream(1) { it + 1 }
        println(getCount(naturalStream))
    }
})
