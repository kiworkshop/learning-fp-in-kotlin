package org.kiworkshop.learningfpinkotlin.kimjuyeop

import io.kotest.core.spec.style.StringSpec
import org.kiworkshop.learningfpinkotlin.head
import org.kiworkshop.learningfpinkotlin.tail

class Chapter04 : StringSpec({

    "Example 4-1" {
        class PartialFunction<in P, out R>(
            private val condition: (P) -> Boolean,
            private val f: (P) -> R
        ) : (P) -> R {

            override fun invoke(p: P): R = when {
                condition(p) -> f(p)
                else -> throw IllegalArgumentException("$p isn't supported.")
            }

            fun invokeOrElse(p: P, default: @UnsafeVariance R): R = when {
                condition(p) -> f(p)
                else -> default
            }

            // TODO orElse
            fun orElse(that: PartialFunction<@UnsafeVariance P, @UnsafeVariance R>): PartialFunction<P, R>? {
                return null
            }
        }
    }

    "Example 4-2" {
        fun <P1, P2, P3, R> ((P1, P2, P3) -> R).partial1(p1: P1): (P2, P3) -> R {
            return { p2, p3 -> this(p1, p2, p3) }
        }

        fun <P1, P2, P3, R> ((P1, P2, P3) -> R).partial2(p2: P2): (P1, P3) -> R {
            return { p1, p3 -> this(p1, p2, p3) }
        }

        fun <P1, P2, P3, R> ((P1, P2, P3) -> R).partial3(p3: P3): (P1, P2) -> R {
            return { p1, p2 -> this(p1, p2, p3) }
        }
    }

    "Example 4-3" {
        fun max(a: Int) = { b: Int -> if (a > b) a else b }
    }

    "Example 4-4" {
        fun <P1, P2, R> ((P1, P2) -> R).curried(): (P1) -> (P2) -> R =
            { p1: P1 -> { p2: P2 -> this(p1, p2) } }

        fun <P1, P2, R> ((P1) -> (P2) -> R).unCurried(): (P1, P2) -> R =
            { p1: P1, p2: P2 -> this(p1)(p2) }

        fun min(a: Int, b: Int) = { x: Int, y: Int -> if (x < y) x else y }.curried()(a)(b)
    }

    "Example 4-5" {
        fun max(list: List<Int>): Int? = list.maxOrNull()

        fun power(a: Int?): Int? = a?.let { it * it }

        power(max(listOf(1, 2, 3)))
    }

    "Example 4-6" {
        infix fun <F, G, R> ((F) -> R).compose(g: (G) -> F): (G) -> R {
            return { gInput: G -> this(g(gInput)) }
        }

        val max = { list: List<Int> -> list.maxOrNull() }
        val power = { a: Int? -> a?.let { it * it } }

        power compose max
    }

    "Example 4-7" {
        tailrec fun <P1> takeWhile(func: (P1) -> Boolean, list: List<P1>, acc: List<P1> = listOf()): List<P1> = when {
            list.isEmpty() -> acc
            else -> {
                var takeList = acc
                if (func(list.head())) takeList = takeList + listOf(list.head())
                takeWhile(func, list.tail(), takeList)
            }
        }

        val list = listOf(1, 2, 3, 4, 5)
        val takeFunc = { a: Int -> a < 3 }

        println(takeWhile(takeFunc, list))
    }

    // TODO Example 4-8
    "Example 4-8" {}
})