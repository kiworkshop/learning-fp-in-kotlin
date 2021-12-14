package org.kiworkshop.learningfpinkotlin

import io.kotest.core.spec.style.StringSpec

class Chap4 : StringSpec({
    // List<T>.max() 대신 List<T>.maxOrNull()
    // List<T>.min() 대신 List<T>.minOrNull()

//    "Example 4-1" {
//        // test code 4-16
//        val condition: (Int) -> Boolean = { it.rem(2) == 0 }
//        val body: (Int) -> String = { "$it is even" }
//
//        val isEven = body.toPartialFunction(condition)
//
//        isEven.isDefinedAt(100).shouldBeTrue()
//        isEven(100) shouldBe "100 is even"
//
//        // test invokeOrElse
//        val oddNumber = 99
//        isEven.invokeOrElse(oddNumber, "$oddNumber is odd") shouldBe "99 is odd"
//
//        // test orElse
//        val isEvenOrOdd = isEven.orElse(PartialFunction({ it.rem(2) == 1 }, { "$it is odd" }))
//        isEvenOrOdd(100) shouldBe "100 is even"
//        isEvenOrOdd(99) shouldBe "99 is odd"
//    }
//
//    "Example 4-2" {
//        fun <P1, P2, P3, R> ((P1, P2, P3) -> R).partial1(p1: P1): (P2, P3) -> R =
//            { p2, p3 -> this(p1, p2, p3) }
//
//        fun <P1, P2, P3, R> ((P1, P2, P3) -> R).partial2(p2: P2): (P1, P3) -> R =
//            { p1, p3 -> this(p1, p2, p3) }
//
//        fun <P1, P2, P3, R> ((P1, P2, P3) -> R).partial3(p3: P3): (P1, P2) -> R =
//            { p1, p2 -> this(p1, p2, p3) }
//
//        val f: (String, String, String) -> String = { p1, p2, p3 -> "$p1, $p2, $p3" }
//
//        f.partial1("alpha")("beta", "gamma") shouldBe "alpha, beta, gamma"
//        f.partial2("alpha")("beta", "gamma") shouldBe "beta, alpha, gamma"
//        f.partial3("alpha")("beta", "gamma") shouldBe "beta, gamma, alpha"
//    }
//
//    "Example 4-3" {
//        fun max(p1: Int): (Int) -> Int = { p2 ->
//            kotlin.math.max(p1, p2)
//        }
//
//        max(1)(2) shouldBe 2
//    }
//
//    "Example 4-4" {
//        fun min(p1: Int, p2: Int) = kotlin.math.min(p1, p2)
//
//        val curriedMin: (Int) -> (Int) -> Int = ::min.curried()
//
//        curriedMin(1)(2) shouldBe 1
//    }
//
//    fun max(ints: List<Int>): Int? {
//        tailrec fun max(ints: List<Int>, acc: Int): Int =
//            if (ints.isEmpty())
//                acc
//            else
//                max(ints.tail(), kotlin.math.max(ints.head(), acc))
//
//        return if (ints.isEmpty()) null else max(ints.tail(), ints.head())
//    }
//
//    fun power(x: Int?, n: Int): Int? {
//        if (x == null) return null
//
//        tailrec fun power(n: Int, acc: Int): Int = when (n) {
//            0 -> 1
//            1 -> acc
//            else -> power(n - 1, acc * x)
//        }
//
//        return power(n, x)
//    }
//
//    "Example 4-5" {
//        max(listOf()) shouldBe null
//        max(listOf(1, 2, 3, 4, 5)) shouldBe 5
//        power(5, 3) shouldBe 5 * 5 * 5
//        power(5, 0) shouldBe 1
//        power(null, 50).shouldBeNull()
//
//        fun composed(ints: List<Int>): Int? = power(max(ints), 2)
//
//        composed(listOf(1, 2, 3, 4, 5)) shouldBe 25
//        composed(listOf()) shouldBe null
//    }
//
//    "Example 4-6" {
//        // solution 1
//        infix fun <F, G, H, R> ((F, G) -> R).compose(h: (H) -> F): (H, G) -> R =
//            { hInput: H, gInput: G -> this(h(hInput), gInput) }
//
//        val composed = ::power compose ::max
//
//        composed(listOf(1, 2, 3, 4, 5), 2) shouldBe 25
//        composed(listOf(), 2) shouldBe null
//
//        // solution 2
//        val square: (Int?) -> Int? = { power(it, 2) }
//
//        val composed2 = square compose ::max
//        composed2(listOf(1, 2, 3, 4, 5)) shouldBe 25
//        composed2(listOf()) shouldBe null
//
//        // solution 3
//        val getMaxAndPower = ::power.curried() compose ::max
//        getMaxAndPower(listOf(1, 2, 3, 4, 5))(2) shouldBe 25
//        getMaxAndPower(listOf())(2) shouldBe null
//    }
//
//    "Example 4-7" {
//        fun <T> takeWhile(list: List<T>, condition: (T) -> Boolean): List<T> {
//            tailrec fun takeWhile(rest: List<T>, acc: List<T>): List<T> = when {
//                rest.isEmpty() -> acc
//                else -> takeWhile(
//                    rest.tail(),
//                    acc + rest.head().let { if (condition(it)) listOf(it) else listOf() }
//                )
//            }
//
//            return takeWhile(list, listOf())
//        }
//
//        takeWhile(listOf(1, 2, 3, 4, 5)) { it < 3 }.shouldContainExactly(1, 2)
//    }
//
//    "Example 4-8" {
//        fun <T> takeWhile(list: Sequence<T>, condition: (T) -> Boolean): List<T> {
//            val iterator = list.iterator()
//            tailrec fun takeWhile(acc: List<T>): List<T> {
//                if (!iterator.hasNext()) {
//                    return acc
//                }
//
//                val next = iterator.next()
//                if (!condition(next)) {
//                    return acc
//                }
//
//                return takeWhile(acc + next)
//            }
//
//            return takeWhile(listOf())
//        }
//
//        takeWhile(generateSequence(1) { it + 1 }) { it < 3 }.shouldContainExactly(1, 2)
//    }
})
