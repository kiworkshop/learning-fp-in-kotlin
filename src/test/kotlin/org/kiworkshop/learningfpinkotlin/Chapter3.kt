package org.kiworkshop.learningfpinkotlin

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Chapter3 : StringSpec({
    "연습문제 3-2" {
        fun power(x: Double, n: Int): Double = when (n) {
            0 -> 1.0
            else -> x * power(x, n - 1)
        }

        fun assert(power: (Double, Int) -> Double) {
            power(2.0, 1) shouldBe 2.0
            power(10.0, 2) shouldBe 100.0
            power(2.0, 10) shouldBe 1024.0
            power(3.0, 5) shouldBe 243.0
        }

        assert(::power)
    }

    "연습문제 3-3" {
        fun factorial(n: Int): Int = when (n) {
            0 -> 1
            else -> n * factorial(n - 1)
        }

        factorial(5) shouldBe 120
        factorial(1) shouldBe 1
        factorial(0) shouldBe 1
        factorial(3) shouldBe 6
    }

    fun String.head() = first()
    fun String.tail() = drop(1)

    "Code 3-7" {
        fun reverse(s: String): String = when {
            s.isEmpty() -> ""
            else -> reverse(s.tail()) + s.head()
        }

        reverse("abc") shouldBe "cba"
        reverse("hello") shouldBe "olleh"
    }

    "연습문제 3-4" {
        fun toBinary(n: Int): String = when {
            n <= 1 -> "1"
            else -> "${toBinary(n / 2)}${n % 2}"
        }
        toBinary(10) shouldBe "1010"
        toBinary(11) shouldBe "1011"
        toBinary(15) shouldBe "1111"
    }

    "연습문제 3-5" {
        // replicate(3, 5) [5, 5, 5]
        fun replicate(n: Int, element: Int): List<Int> = when {
            n == 1 -> listOf(element)
            else -> listOf(element) + replicate(n - 1, element)
        }

        replicate(3, 5) shouldBe listOf(5, 5, 5)
        replicate(4, 5) shouldBe listOf(5, 5, 5, 5)
        replicate(1, 6) shouldBe listOf(6)
    }

    "연습문제 3-6" {
        // 입력값 n 이 리스트에 존재하는 지 확인하는 함수
        fun elem(num: Int, list: List<Int>): Boolean = when {
            list.isEmpty() -> false
            num == list.head() -> true
            else -> elem(num, list.tail())
        }

        elem(5, listOf(1, 2, 3)) shouldBe false
        elem(5, listOf(1, 2, 3, 5, 6)) shouldBe true
        elem(6, listOf(6)) shouldBe true
        elem(7, listOf(6)) shouldBe false
    }

    "연습문제 3-7" {
        // 풀지 못함.. overflow..
        operator fun <T> Sequence<T>.plus(other: () -> Sequence<T>) = object : Sequence<T> {
            private val thisIterator: Iterator<T> by lazy { this@plus.iterator() }
            private val otherIterator: Iterator<T> by lazy { other().iterator() }

            override fun iterator() = object : Iterator<T> {
                override fun next(): T =
                    if (thisIterator.hasNext())
                        thisIterator.next()
                    else
                        otherIterator.next()

                override fun hasNext(): Boolean = thisIterator.hasNext() || otherIterator.hasNext()
            }
        }

        fun repeat(n: Int): Sequence<Int> = sequenceOf(n) + repeat(n)
        fun take(n: Int, list: List<Int>): List<Int> = when {
            n <= 0 -> listOf()
            list.isEmpty() -> listOf()
            else -> listOf(list.head()) + take(n - 1, list.tail())
        }

        fun takeSequence(n: Int, sequence: Sequence<Int>): List<Int> = when {
            sequence.none() -> listOf()
            n <= 0 -> listOf()
            else -> take(1, sequence.toList()) + takeSequence(n - 1, sequence)
        }

        // Stackoverflow
//        takeSequence(1, repeat(3)) shouldBe listOf(3, 3, 3, 3, 3)
    }

    "연습문제 3-8" {
    }

    "연습문제 3-9" {
        fun gcd(m: Int, n: Int): Int = when (n) {
            0 -> m
            else -> gcd(n, m % n)
        }

        gcd(4, 5) shouldBe 1
        gcd(16, 20) shouldBe 4
    }

    "연습문제 3-10" {
        var memo = Array(100, { -1 })
        fun factorial(n: Int): Int = when {
            n == 0 -> 1
            memo[n] != -1 -> memo[n]
            else -> {
                println("factorial($n)")
                memo[n] = factorial(n - 1) * n
                memo[n]
            }
        }
        factorial(5) shouldBe 120
        factorial(1) shouldBe 1
        factorial(0) shouldBe 1
        factorial(3) shouldBe 6
    }

    "연습문제 3-11 / 연습문제 3-12" {
        tailrec fun factorialFP(n: Int, first: Int, second: Int): Int = when (n) {
            0 -> first
            1 -> second
            else -> factorialFP(n - 1, second, n * second)
        }

        fun factorialFP(n: Int) = factorialFP(n, 1, 1)

        factorialFP(5) shouldBe 120
        factorialFP(1) shouldBe 1
        factorialFP(0) shouldBe 1
        factorialFP(3) shouldBe 6
    }

    "연습문제 3-13" {
        tailrec fun powerFP(x: Double, n: Int, first: Double): Double = when (n) {
            0 -> first
            else -> powerFP(x, n - 1, first * x)
        }

        fun powerFP(x: Double, n: Int) = powerFP(x, n, 1.0)

        powerFP(2.0, 4) shouldBe 16.0
        powerFP(10.0, 2) shouldBe 100.0
        powerFP(2.0, 10) shouldBe 1024.0
        powerFP(3.0, 5) shouldBe 243.0
    }

    "연습문제 3-14" {
        tailrec fun toBinaryFP(n: Int, acc: String = ""): String = when (n) {
            0 -> acc
            else -> {
                println("toBinaryFP($n / 2, $acc + ${n % 2})")
                toBinaryFP(n / 2, "${n % 2}" + acc)
            }
        }
        toBinaryFP(10) shouldBe "1010"
        toBinaryFP(11) shouldBe "1011"
        toBinaryFP(15) shouldBe "1111"
    }

    "연습문제 3-15" {
        // replicate(3, 5) [5, 5, 5]
        fun replicate(n: Int, element: Int, acc: List<Int> = listOf()): List<Int> = when {
            n == 0 -> acc
            else -> {
                println("replicate($n-1, $element, $acc + $element)")
                replicate(n - 1, element, acc + element)
            }
        }

        replicate(3, 5) shouldBe listOf(5, 5, 5)
        replicate(4, 5) shouldBe listOf(5, 5, 5, 5)
        replicate(1, 6) shouldBe listOf(6)
    }

    "연습문제 3-16" {
        tailrec fun elem(num: Int, list: List<Int>, acc: Boolean = false): Boolean = when {
            acc -> acc
            list.isEmpty() -> false
            else -> {
                println("elem($num, ${list.tail()}, $num == ${list.head()})")
                elem(num, list.tail(), num == list.head())
            }
        }

        elem(5, listOf(1, 2, 3)) shouldBe false
        elem(5, listOf(1, 2, 3, 5, 6)) shouldBe true
        elem(6, listOf(6)) shouldBe true
        elem(7, listOf(6)) shouldBe false
    }

    "연습문제 3-17" {
//        tailrec fun sqrtRecursion(n: Int, acc: Int = 0) = when {
//            n < 1 -> acc
//            else -> sqrtRecursion(n / 2, )
//        }
//
//        sqrtRecursion(2, 0)
    }

    "연습문제 3-18" {
    }

    "연습문제 3-19" {
    }
})
