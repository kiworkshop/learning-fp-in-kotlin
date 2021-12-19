package org.kiworkshop.learningfpinkotlin

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Chapter4 : StringSpec({
    "연습문제 4-1" {
        val isEven = PartialFunction<Int, String>({ it % 2 == 0 }, { "$it is even" })

        val odd = 55
        isEven.invokeOrElse(odd, "$odd isn't even")

        val isEvenOrOdd = isEven.orElse(PartialFunction({ it % 2 == 1 }, { "$it is odd" }))

        isEvenOrOdd(2) shouldBe "2 is even"
        isEvenOrOdd(3) shouldBe "3 is odd"
    }

    "연습문제 4-2" {
        val func = { a: String, b: String, c: String -> a + b + c }
        val f1 = func.partial1("Hello")
        f1("World", "!") shouldBe "HelloWorld!"

        val f2 = func.partial2("World")
        f2("Hello", "!") shouldBe "HelloWorld!"

        val f3 = func.partial3("!")
        f3("Hello", "World") shouldBe "HelloWorld!"
    }

    "연습문제 4-3" {
        fun max(a: Int) = { b: Int -> if (a > b) a else b }

        max(2)(3) shouldBe 3
        max(5)(3) shouldBe 5
    }

    "연습문제 4-4" {
        val min = { a: Int, b: Int -> if (a < b) a else b }
        min(1, 2) shouldBe 1

        val minCurried = min.curried()
        minCurried(1)(2) shouldBe 1
    }

    val maximum = { a: List<Int> -> a.maxOrNull() }
    val power = { a: Int? -> if (a == null) 0 else a * a }

    "연습문제 4-5" {
        power(maximum(listOf(1, 2, 3))) shouldBe 9
    }

    "연습문제 4-6" {
        // LIST<INT> INT?  
        infix fun <F, G, R> ((F) -> R).compose(g: (G) -> F): (G) -> R {
            return { gInput: G -> this(g(gInput)) }
        }

        val composed = power compose maximum

        composed(listOf(1, 2, 3)) shouldBe 9
        composed(listOf(1)) shouldBe 1
        composed(listOf(5, 6, 4)) shouldBe 36
    }

    "연습문제 4-7" {
        tailrec fun <P1> takeWhile(func: (P1) -> Boolean, list: List<P1>, acc: List<P1> = listOf()): List<P1> = when {
            list.isEmpty() -> acc
            else -> {
                val head = list.head()
                val takeList = if (func(head)) acc + head else acc
                takeWhile(func, list.tail(), takeList)
            }
        }

        val condition = { x: Int -> x < 3 }
        takeWhile(condition, listOf(1, 2, 3)) shouldBe listOf(1, 2)
        takeWhile(condition, listOf(4, 5, 6)) shouldBe listOf()
        takeWhile(condition, listOf(2, 6)) shouldBe listOf(2)
    }

    "연습문제 4-8" {
        tailrec fun <P> takewhile(
            condition: (P) -> Boolean,
            iter: Iterator<P>,
            acc: List<P> = listOf()
        ): List<P> = when {
            !iter.hasNext() -> acc
            else -> {
                val head = iter.next()
                when {
                    condition(head) -> takewhile(condition, iter, acc + head)
                    else -> takewhile(condition, iter, acc)
                }
            }
        }
    }
})
