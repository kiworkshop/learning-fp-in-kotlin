package org.kiworkshop.learningfpinkotlin

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldHaveMinLength
import java.math.BigDecimal

class Chapter3 : StringSpec({
    "Exercise 3-1" {
        """
            - 명제: fibonacci(n)은 음이 아닌 정수 n에 대해서 n번째 피보나치 수를 올바르게 계산한다
            - n = 0인 경우, 0을 반환하므로 참이다.
            - n = 1인 경우, 1을 반환하므로 참이다.
            - 임의의 양의 정수 k에 대해서 n < k인 경우, k번째 피보나치 수를 올바르게 계산하여 반환한다고 가정한다.
            - n = k 인 경우 fibonacci(k - 1)과 fibonacci(k - 2)를 호출할 때 
              3번 가정에 의해서 k-1, k-2 번째 피보나치 수를 올바르게 계산한다. 
              이렇게 나온 두 값을 더하여 반환하므로, fibonacci 함수는 n번째 피보나치 수를 올바르게 계산한다.
        """ shouldHaveMinLength 0
    }

    "Exercise 3-2" {
        fun power(x: Double, n: Int): Double = when (n) {
            0 -> 1.0
            else -> x * power(x, n - 1)
        }

        power(2.0, 0) shouldBe 1.0
        power(2.0, 2) shouldBe 4.0
        power(3.0, 3) shouldBe 27.0
    }

    "Exercise 3-3" {
        fun factorial(n: Int): Long = when {
            n == 0 -> 1
            else -> n * factorial(n - 1)
        }

        factorial(0) shouldBe 1
        factorial(3) shouldBe 6
        factorial(4) shouldBe 24
    }

    "Exercise 3-4" {
        fun toBinary(n: Int): String = when (n) {
            0 -> "0"
            1 -> "1"
            else -> toBinary(n / 2) + (n % 2)
        }

        toBinary(0) shouldBe "0"
        toBinary(1) shouldBe "1"
        toBinary(8) shouldBe "1000"
        toBinary(25) shouldBe "11001"
    }

    "Exercise 3-5" {
        fun replicate(n: Int, element: Int): List<Int> = when (n) {
            0 -> emptyList()
            else -> listOf(element).plus(replicate(n - 1, element))
        }

        replicate(0, 2) shouldBe emptyList()
        replicate(3, 5) shouldBe listOf(5, 5, 5)
        replicate(6, 9) shouldBe listOf(9, 9, 9, 9, 9, 9)
    }

    "Exercise 3-6" {
        fun elem(num: Int, list: List<Int>): Boolean = when {
            list.isEmpty() -> false
            list.first() == num -> true
            else -> elem(num, list.drop(1))
        }

        elem(1, emptyList()) shouldBe false
        elem(1, listOf(2, 3)) shouldBe false
        elem(1, listOf(1, 2, 3)) shouldBe true
        elem(3, listOf(1, 2, 3)) shouldBe true
    }

    "Exercise 3-7" {
        fun repeat(n: Int): Sequence<Int> = sequenceOf(n) + { repeat(n) }
        fun takeSequence(n: Int, sequence: Sequence<Int>): List<Int> = when {
            n <= 0 -> emptyList()
            sequence.none() -> emptyList()
            else -> listOf(sequence.first()).plus(takeSequence(n - 1, sequence))
        }

        takeSequence(5, repeat(3)) shouldBe listOf(3, 3, 3, 3, 3)
        takeSequence(4, repeat(2)) shouldBe listOf(2, 2, 2, 2)
    }

    "Exercise 3-8" {
        fun quicksort(list: List<Int>): List<Int> = when {
            list.size <= 1 -> list
            else -> {
                val pivot = list.first()
                quicksort(list.filter { it < pivot })
                    .plus(list.filter { it == pivot })
                    .plus(quicksort(list.filter { it > pivot }))
            }
        }

        quicksort(listOf(3, 2, 1)) shouldBe listOf(1, 2, 3)
        quicksort(listOf(5, 3, 1, 2, 4)) shouldBe listOf(1, 2, 3, 4, 5)
        quicksort(listOf(5, 3, 1, 2, 2, 4)) shouldBe listOf(1, 2, 2, 3, 4, 5)
    }

    "Exercise 3-9" {
        fun gcd(m: Int, n: Int): Int = when {
            n == 0 -> m
            else -> gcd(n, m % n)
        }

        gcd(12, 3) shouldBe 3
        gcd(18, 16) shouldBe 2
        gcd(3, 2) shouldBe 1
    }

    "Exercise 3-10" {
        val memo = Array(100) { -1L }
        fun factorial(n: Int): Long = when {
            n <= 0 -> 1
            memo[n] != -1L -> memo[n]
            else -> {
                memo[n] = n * factorial(n - 1)
                memo[n]
            }
        }

        factorial(0) shouldBe 1
        factorial(3) shouldBe 6
        factorial(4) shouldBe 24
        factorial(20) shouldBe 2432902008176640000
    }

    "Exercise 3-11, Exercise 3-12" {
        tailrec fun factorial(n: Int, first: Long, second: Long): Long = when (n) {
            0 -> second
            else -> factorial(n - 1, first + 1, first * second)
        }

        fun factorial(n: Int): Long = factorial(n, 1L, 1L)

        factorial(0) shouldBe 1
        factorial(3) shouldBe 6
        factorial(4) shouldBe 24
        factorial(20) shouldBe 2432902008176640000
    }

    "Exercise 3-13" {
        tailrec fun power(x: Double, n: Int, first: Int = 1, second: Double = 1.0): Double = when (n) {
            0 -> second
            else -> power(x, n - 1, first + 1, second * x)
        }

        power(2.0, 0) shouldBe 1.0
        power(2.0, 2) shouldBe 4.0
        power(3.0, 3) shouldBe 27.0
    }

    "Exercise 3-14" {
        tailrec fun toBinary(n: Int, acc: String = "1"): String = when {
            n == 0 -> "0"
            n == 1 -> acc
            else -> toBinary(n / 2, acc + (n % 2))
        }

        toBinary(0) shouldBe "0"
        toBinary(1) shouldBe "1"
        toBinary(8) shouldBe "1000"
        toBinary(25) shouldBe "11001"
    }

    "Exercise 3-15" {
        tailrec fun replicate(n: Int, element: Int, acc: List<Int> = emptyList()): List<Int> = when (n) {
            0 -> acc
            else -> replicate(n - 1, element, acc.plus(element))
        }

        replicate(0, 2) shouldBe emptyList()
        replicate(3, 5) shouldBe listOf(5, 5, 5)
        replicate(6, 9) shouldBe listOf(9, 9, 9, 9, 9, 9)
    }

    "Exercise 3-16" {
        // elm fun is tail recursive 
        tailrec fun elem(num: Int, list: List<Int>): Boolean = when {
            list.isEmpty() -> false
            list.first() == num -> true
            else -> elem(num, list.drop(1))
        }

        elem(1, emptyList()) shouldBe false
        elem(1, listOf(2, 3)) shouldBe false
        elem(1, listOf(1, 2, 3)) shouldBe true
        elem(3, listOf(1, 2, 3)) shouldBe true
    }

    "Exercise 3-17" {
        sqrt(4.0) shouldBe 0.5
        divide(8.0) shouldBe 0.5
    }

    "Exercise 3-18" {
        trampoline(sqrtTram(4.0)) shouldBe 0.5
        trampoline(divideTram(8.0)) shouldBe 0.5
    }

    "Exercise 3-19" {
        println(trampoline(factorialTram(100000)))
    }
})

fun sqrt(n: Double): Double = when {
    n < 1.0 -> n
    else -> divide(kotlin.math.sqrt(n))
}

fun divide(n: Double): Double = when {
    n < 1.0 -> n
    else -> sqrt(n / 2)
}

fun sqrtTram(n: Double): Bounce<Double> = when {
    n < 1.0 -> Done(n)
    else -> More { divideTram(kotlin.math.sqrt(n)) }
}

fun divideTram(n: Double): Bounce<Double> = when {
    n < 1.0 -> Done(n)
    else -> More { sqrtTram(n / 2) }
}

fun factorialTram(n: Int, first: BigDecimal = BigDecimal(1), second: BigDecimal = BigDecimal(1)): Bounce<BigDecimal> =
    when (n) {
        0 -> Done(second)
        else -> More { factorialTram(n - 1, first + BigDecimal(1), first * second) }
    }

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

sealed class Bounce<A>
data class Done<A>(val result: A) : Bounce<A>()
data class More<A>(val thunk: () -> Bounce<A>) : Bounce<A>()

tailrec fun <A> trampoline(bounce: Bounce<A>): A = when (bounce) {
    is Done -> bounce.result
    is More -> trampoline(bounce.thunk())
}
