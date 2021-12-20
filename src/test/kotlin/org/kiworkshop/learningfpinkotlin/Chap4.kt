package org.kiworkshop.learningfpinkotlin

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe

class Chap4 : StringSpec({
    // List<T>.max() 대신 List<T>.maxOrNull()
    // List<T>.min() 대신 List<T>.minOrNull()

    "Example 4-1" {
        // test code 4-16
        val condition: (Int) -> Boolean = { it.rem(2) == 0 }
        val body: (Int) -> String = { "$it is even" }

        val isEven = body.toPartialFunction(condition)

        isEven.isDefinedAt(100).shouldBeTrue()
        isEven(100) shouldBe "100 is even"

        // test invokeOrElse
        val oddNumber = 99
        isEven.invokeOrElse(oddNumber, "$oddNumber is odd") shouldBe "99 is odd"

        // test orElse
        val isEvenOrOdd = isEven.orElse(PartialFunction({ it.rem(2) == 1 }, { "$it is odd" }))
        isEvenOrOdd(100) shouldBe "100 is even"
        isEvenOrOdd(99) shouldBe "99 is odd"
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

        val f = { p1: String, p2: String, p3: String -> listOf(p1, p2, p3).joinToString(", ") }

        f.partial1("alpha")("beta", "gamma") shouldBe "alpha, beta, gamma"
        f.partial2("alpha")("beta", "gamma") shouldBe "beta, alpha, gamma"
        f.partial3("alpha")("beta", "gamma") shouldBe "beta, gamma, alpha"
    }

    "Example 4-3" {
        fun max(n1: Int) = { n2: Int -> if (n1 > n2) n1 else n2 }

        max(1)(2) shouldBe 2
    }

    "Example 4-4" {
        fun <P1, P2, R> ((P1, P2) -> R).curried(): (P1) -> (P2) -> R = { p1: P1 -> { p2: P2 -> this(p1, p2) } }
        val curriedMin = { n1: Int, n2: Int -> if (n1 > n2) n2 else n1 }.curried()

        curriedMin(1)(2) shouldBe 1
    }

    fun max(ints: List<Int>): Int? {
        tailrec fun max(ints: List<Int>, acc: Int): Int =
            if (ints.isEmpty())
                acc
            else
                max(ints.tail(), kotlin.math.max(ints.head(), acc))

        return if (ints.isEmpty()) null else max(ints.tail(), ints.head())
    }

    fun power(x: Int?, n: Int): Int? {
        if (x == null) return null

        tailrec fun power(n: Int, acc: Int): Int = when (n) {
            0 -> 1
            1 -> acc
            else -> power(n - 1, acc * x)
        }

        return power(n, x)
    }

    "Example 4-5" {
        max(listOf()) shouldBe null
        max(listOf(1, 2, 3, 4, 5)) shouldBe 5
        power(5, 3) shouldBe 5 * 5 * 5
        power(5, 0) shouldBe 1
        power(null, 50).shouldBeNull()

        fun composed(ints: List<Int>): Int? = power(max(ints), 2)

        composed(listOf(1, 2, 3, 4, 5)) shouldBe 25
        composed(listOf()) shouldBe null
    }

    "Example 4-6" {
        infix fun <F, G, H, R> ((F, G) -> R).compose(h: (H) -> F): (H, G) -> R =
            { hInput: H, gInput: G -> this(h(hInput), gInput) }

        val composed = ::power compose ::max

        composed(listOf(1, 2, 3, 4, 5), 2) shouldBe 25
        composed(listOf(), 2) shouldBe null
    }

    "Example 4-7" {
        fun <P> takeWhile(list: List<P>, func: (P) -> Boolean): List<P> {
            tailrec fun <P> takeWhileTail(list: List<P>, func: (P) -> Boolean, acc: List<P>): List<P> = when {
                list.isEmpty() -> acc
                else -> {
                    if (func(list.head()))
                        takeWhileTail(list.tail(), func, acc + list.head())
                    else
                        takeWhileTail(list.tail(), func, acc + emptyList())
                }
            }
            return takeWhileTail(list, func, listOf())
        }

        takeWhile(listOf(1, 2, 3, 4, 5)) { it < 3 }.shouldContainExactly(1, 2)
    }

    "Example 4-8" {
        fun <T> takeWhile(list: Sequence<T>, condition: (T) -> Boolean): List<T> {
            val iterator = list.iterator()
            tailrec fun takeWhile(acc: List<T>): List<T> {
                if (!iterator.hasNext()) {
                    return acc
                }

                val next = iterator.next()
                if (!condition(next)) {
                    return acc
                }

                return takeWhile(acc + next)
            }

            return takeWhile(listOf())
        }

        takeWhile(generateSequence(1) { it + 1 }) { it < 3 }.shouldContainExactly(1, 2)
    }
})
