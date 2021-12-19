package org.kiworkshop.learningfpinkotlin.seonghunlee

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe

class Chapter4 : StringSpec({

    "ch4-1" {
        val condition: (Int) -> Boolean = { it.rem(2) == 0 }
        val body: (Int) -> String = { "$it is even" }
        val isEven = body.toPartialFunction(condition)

        isEven.isDefinedAt(100).shouldBeTrue()
        isEven(100) shouldBe "100 is even"

        val oddNumber = 99
        isEven.invokeOrElse(oddNumber, "$oddNumber is odd number") shouldBe "$oddNumber is odd number"

        val isEvenAndDivideBy3 = isEven.orElse(PartialFunction({ it.rem(3) == 0 }, { "$it is divide by 3" }))
        isEvenAndDivideBy3(9) shouldBe "9 is divide by 3"
    }

    "ch4-2" {
        val func = { a: String, b: String, c: String -> a + b + c }
        val partiallyAppliedFunc1 = func.partial1("Hello")
        val result1 = partiallyAppliedFunc1("World", "Harris")
        result1 shouldBe "HelloWorldHarris"

        val partiallyAppliedFunc2 = func.partial2("World")
        val result2 = partiallyAppliedFunc2("Hello", "Harris")
        result2 shouldBe "HelloWorldHarris"

        val partiallyAppliedFunc3 = func.partial3("Harris")
        val result3 = partiallyAppliedFunc3("Hello", "World")
        result3 shouldBe "HelloWorldHarris"
    }
    fun normalMax(x: Int, y: Int): Int =
        if (x > y) x else y

    fun curryMax(x: Int): (Int) -> Int = { y: Int ->
        if (x > y) x else y
    }
    "ch4-3" {
        val partialMax = curryMax(10)
        partialMax(20) shouldBe 20
        partialMax(9) shouldBe 10
    }
    fun <P1, P2, R> ((P1, P2) -> R).curried(): (P1) -> (P2) -> R =
        { p1: P1 -> { p2: P2 -> this(p1, p2) } }
    "ch4-4" {
        val minTwo = { a: Int, b: Int -> if (a > b) b else a }
        val curried = minTwo.curried()
        val partialMin = curried(10)
        partialMin(20) shouldBe 10
        partialMin(4) shouldBe 4
    }
})
