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

//    "연습문제 4-6" {
//        infix fun <F, G, R> ((F) -> R).compose(g: (G) -> F): (G) -> R {
//            return { gInput: G -> this(g(gInput)) }
//        }
//
//        val composed = maximum compose power
//    }
})
