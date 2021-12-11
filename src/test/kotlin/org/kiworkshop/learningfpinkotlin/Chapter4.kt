package org.kiworkshop.learningfpinkotlin

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Chapter4 : StringSpec({
    "Exercise 4-1" {
        class PartialFunction<P, R>(
            private val condition: (P) -> Boolean,
            private val f: (P) -> R
        ) : (P) -> R {
            override fun invoke(p: P): R = when {
                condition(p) -> f(p)
                else -> throw IllegalArgumentException("$p isn't supported.")
            }

            fun isDefinedAt(p: P): Boolean = condition(p)
            fun invokeOrElse(p: P, default: R): R = when {
                condition(p) -> f(p)
                else -> default
            }

            fun orElse(that: PartialFunction<P, R>, p: P): PartialFunction<P, R> = when {
                condition(p) -> this
                else -> that
            }
        }

        fun <P, R> ((P) -> R).toPartialFunction(condition: (P) -> Boolean): PartialFunction<P, R> =
            PartialFunction(condition, this)

//        val isEven = PartialFunction<Int, String>({it % 2 == 0}, {"$it is even"})
        val body: (Int) -> String = { "$it is even" }
        val isEven = body.toPartialFunction { it % 2 == 0 }

        val otherBody: (Int) -> String = { "$it is odd" }
        val isOdd = otherBody.toPartialFunction { it % 2 == 1 }

//        isEven(100) shouldBe "100 is even"
//        shouldThrow<IllegalArgumentException> {
//            isEven(3)
//        }

        isEven.invokeOrElse(3, "isn't even") shouldBe "isn't even"

        isEven.orElse(isOdd, 100) shouldBe isEven
        isEven.orElse(isOdd, 3) shouldBe isOdd
    }

    "Exercise 4-2" {
        fun <P1, P2, P3, R> ((P1, P2, P3) -> R).partial1(p1: P1): (P2, P3) -> R {
            return { p2, p3 -> this(p1, p2, p3) }
        }

        fun <P1, P2, P3, R> ((P1, P2, P3) -> R).partial2(p2: P2): (P1, P3) -> R {
            return { p1, p3 -> this(p1, p2, p3) }
        }

        fun <P1, P2, P3, R> ((P1, P2, P3) -> R).partial3(p3: P3): (P1, P2) -> R {
            return { p1, p2 -> this(p1, p2, p3) }
        }

        val func = { a: String, b: String, c: String -> "$a $b $c" }

        val paf1 = func.partial1("I")
        paf1("am", "Ironman") shouldBe "I am Ironman"

        val paf2 = func.partial2("am")
        paf2("I", "Ironman") shouldBe "I am Ironman"

        val paf3 = func.partial3("Ironman")
        paf3("I", "am") shouldBe "I am Ironman"
    }

    "Exercise 4-3" {

    }

    "Exercise 4-4" {

    }

    "Exercise 4-5" {

    }

    "Exercise 4-6" {

    }

    "Exercise 4-7" {

    }

    "Exercise 4-8" {

    }
})