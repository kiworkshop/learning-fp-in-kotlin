package org.kiworkshop.learningfpinkotlin.c4

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlin.math.max
import kotlin.math.min

class Chap4 : StringSpec({

    "Example 4-1" {
        // even
        val evenCondition: (Int) -> Boolean = { it % 2 == 0 }
        val evenBody: (Int) -> String = { "$it is even" }
        val even = evenBody.toPartialFunction(evenCondition)

        even(2) shouldBe "2 is even"
        shouldThrow<IllegalArgumentException> { even(1) }
        even.invokeOrElse(1, "error") shouldBe "error"

        // odd
        val oddCondition: (Int) -> Boolean = { it % 2 == 1 }
        val oddBody: (Int) -> String = { "$it is odd" }
        val odd = oddBody.toPartialFunction(oddCondition)

        odd(1) shouldBe "1 is odd"
        shouldThrow<IllegalArgumentException> { odd(2) }

        // all integer
        val integer = even.orElse(odd)
        integer(1) shouldBe "1 is odd"
        integer(2) shouldBe "2 is even"
    }

    "Example 4-2" {
        val func: (String, String, String) -> String = { a, b, c -> "${a}${b}${c}" }
        val partiallyAppliedFunc1 = func.toPartial1("Hello")
        val partiallyAppliedFunc2 = partiallyAppliedFunc1.toPartial1("World")
        val result1 = partiallyAppliedFunc2("世界")

        val partiallyAppliedFunc3 = func.toPartial3("世界")
        val partiallyAppliedFunc4 = partiallyAppliedFunc3.toPartial2("World")
        val result2 = partiallyAppliedFunc4("Hello")

        result1 shouldBe result2
        result1 shouldBe "HelloWorld世界"
    }

    "Example 4-3" {
        fun maxCurried(a: Int) = { b: Int -> max(a, b) }

        val maxWithOne = maxCurried(1)
        maxWithOne(2) shouldBe 2
        maxCurried(1)(2) shouldBe 2
    }

    "Example 4-4" {
        val min: (Int, Int) -> (Int) = { a, b -> min(a, b) }
        val minCurried = min.curried()

        minCurried(1)(2) shouldBe 1
    }

    "Example 4-5" {
        fun squareOfMax(l: List<Int>): Int? {
            if (l.isEmpty()) {
                return null
            }
            return power(getMax(l), 2)
        }

        val list = listOf(3, 4, 1, 2)
        squareOfMax(list) shouldBe 16
    }

    "Example 4-6-1" {
        fun squareOfMax(l: List<Int>): Int? {
            if (l.isEmpty()) {
                return null
            }
            val square: (Int) -> Int = { power(it, 2) }

            val squareOfMax = square compose ::getMax
            return squareOfMax(l)
        }

        val list = listOf(3, 4, 1, 2)
        squareOfMax(list) shouldBe 16
    }

    "Example 4-6-2" {
        fun squareOfMax(l: List<Int>): Int? {
            if (l.isEmpty()) {
                return null
            }

            val powerOfMax = (::power.curried() compose ::getMax)
            return powerOfMax(l)(2)
        }

        val list = listOf(3, 4, 1, 2)
        squareOfMax(list) shouldBe 16
    }

    "Example 4-7" {
        listOf(1, 2, 3, 0, 1).takeWhile { it < 3 } shouldBe listOf(1, 2)
    }

    "Example 4-8" {
        val infiniteSequence = generateSequence(1) {it + 1}
        infiniteSequence.takeWhile { it < 3 } shouldBe listOf(1, 2)
    }
})
