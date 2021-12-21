package org.kiworkshop.learningfpinkotlin.kimjuyeop

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Chapter03 : StringSpec({

    // TODO 3-1
    "Example 3-1" {}

    fun assertThatParameterIsPowerFunction(power: (Double, Int) -> Double) {
        power(3.0, 3) shouldBe 27.0
        power(2.0, 0) shouldBe 1.0
        power(5.0, 12) shouldBe 244140625.0
    }

    "Example 3-2" {
        fun power(x: Double, n: Int): Double = when (n) {
            0 -> 1.0
            else -> x * power(x, n - 1)
        }
        assertThatParameterIsPowerFunction(::power)
    }

    fun assertThatParameterIsFactorialFuction(factorial: (Int) -> Int) {
        factorial(5) shouldBe (5 * 4 * 3 * 2 * 1)
        factorial(1) shouldBe 1
        factorial(12) shouldBe (12 * 11 * 10 * 9 * 8 * 7 * 6 * 5 * 4 * 3 * 2 * 1)
    }

    "Example 3-3" {
        fun factorial(n: Int): Int = when (n) {
            0 -> 1
            else -> n * factorial(n - 1)
        }

        assertThatParameterIsFactorialFuction(::factorial)
    }

    fun assertThatParameterIsToBinaryFunction(toBinary: (Int) -> String) {
        toBinary(2) shouldBe "10"
        toBinary(4) shouldBe "100"
        toBinary(13) shouldBe "1101"
    }

    "Example 3-4" {
        fun toBinary(n: Int): String = when (n) {
            0 -> "0"
            1 -> "1"
            else -> toBinary(n / 2) + n % 2
        }

        assertThatParameterIsToBinaryFunction(::toBinary)
    }

    fun assertThatParameterIsReplicateFunction(replicate: (Int, Int) -> List<Int>) {
        replicate(3, 5) shouldBe listOf(5, 5, 5)
        replicate(6, 7) shouldBe listOf(7, 7, 7, 7, 7, 7)
    }

    "Example 3-5" {
        fun replicate(n: Int, element: Int): List<Int> = when (n) {
            0 -> emptyList()
            else -> {
                val list = arrayListOf(element)
                list.addAll(replicate(n - 1, element))
                list
            }
        }

        assertThatParameterIsReplicateFunction(::replicate)
    }

    fun assertThatParameterIsElemFunction(elem: (Int, List<Int>) -> Boolean) {
        elem(4, listOf(1, 2, 3, 4, 5)) shouldBe true
        elem(10, listOf(20, 30, 40, 50)) shouldBe false
    }

    "Example 3-6" {
        fun elem(num: Int, list: List<Int>): Boolean = when {
            list.isEmpty() -> false
            num == list.first() -> true
            else -> elem(num, list.drop(1))
        }

        assertThatParameterIsElemFunction(::elem)
    }

    "Example 3-7" {
        operator fun <T> Sequence<T>.plus(other: () -> Sequence<T>) = object : Sequence<T> {
            private val thisIterator: Iterator<T> by lazy { this@plus.iterator() }
            private val otherIterator: Iterator<T> by lazy { other().iterator() }

            override fun iterator() = object : Iterator<T> {
                override fun next(): T = if (thisIterator.hasNext()) thisIterator.next() else otherIterator.next()
                override fun hasNext(): Boolean = thisIterator.hasNext() || otherIterator.hasNext()
            }
        }

        fun repeat(n: Int): Sequence<Int> = sequenceOf(n) + { repeat(n) }

        fun takeSequence(n: Int, sequence: Sequence<Int>): List<Int> = when {
            n <= 0 -> listOf()
            sequence.none() -> listOf()
            else -> listOf(sequence.first()) + takeSequence(n - 1, sequence.drop(1))
        }

        println(takeSequence(5, repeat(3)))
    }

    // TODO 3-8
    "Example 3-8" {}

    fun assertThatParameterIsGcdFunction(gcd: (Int, Int) -> Int) {
        gcd(12, 24) shouldBe 12
        gcd(120, 832) shouldBe 8
    }

    "Example 3-9" {
        fun gcd(m: Int, n: Int): Int = when (n) {
            0 -> m
            else -> gcd(n, m % n)
        }

        assertThatParameterIsGcdFunction(::gcd)
    }

    "Example 3-10" {
        val memo = Array(100) { -1 }

        fun factorial(n: Int): Int = when {
            n == 0 -> 1
            memo[n] != -1 -> memo[n]
            else -> {
                println("factorial($n)")
                memo[n] = n * factorial(n - 1)
                memo[n]
            }
        }

        assertThatParameterIsFactorialFuction(::factorial)
    }

    // TODO 3-11
    "Example 3-11" {}

    // TODO 3-12
    "Example 3-12" {}

    "Example 3-13" {
        tailrec fun power(x: Double, n: Int, acc: Double = 1.0): Double = when (n) {
            0 -> acc
            else -> power(x, n - 1, acc * x)
        }

        assertThatParameterIsPowerFunction(::power)
    }

    "Example 3-14" {
        tailrec fun toBinary(n: Int, acc: String = ""): String = when (n) {
            0 -> "0$acc"
            1 -> "1$acc"
            else -> toBinary(n / 2, "${n % 2}$acc")
        }

        assertThatParameterIsToBinaryFunction(::toBinary)
    }

    "Example 3-15" {
        tailrec fun replicate(n: Int, element: Int, acc: List<Int> = listOf()): List<Int> = when (n) {
            0 -> acc
            else -> replicate(n - 1, element, acc.plus(listOf(element)))
        }

        assertThatParameterIsReplicateFunction(::replicate)
    }

    "Example 3-16" {
        tailrec fun elem(num: Int, list: List<Int>): Boolean = when {
            list.isEmpty() -> false
            num == list.first() -> true
            else -> elem(num, list.drop(1))
        }

        assertThatParameterIsElemFunction(::elem)
    }

    "Example 3-17" {
        fun sqrt(n: Double, divide: Double = 1.0): Double {
            fun div(n: Double): Double = sqrt(n, n / 2)
            return when {
                divide < 1.0 -> n
                else -> div(kotlin.math.sqrt(n))
            }
        }

        println(sqrt(49.0))
    }

    // TODO 3-18
    "Example 3-18" {}

    // TODO 3-19
    "Example 3-19" {}
})