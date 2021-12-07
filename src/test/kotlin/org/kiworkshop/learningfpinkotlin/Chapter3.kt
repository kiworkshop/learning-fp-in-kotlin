package org.kiworkshop.learningfpinkotlin

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import java.math.BigDecimal

class Chapter3 : StringSpec({
    "Exercise 1" {
        """
            명제: fiboRecursion(n) 함수는 n일 때 n 번째의 피보나치 수를 반환한다.
            n = 0인 경우, fiboRecursion은 0을 반환하여 참이다.
            n = 1 인 경우 1을 반환하여 참이다.
            임의의 양의 정수 k에 대해 fiboRecursion이 k부터 0까지의 변수에 대해 그 값을 올바른 피보나치 수를 반환한다고 가정하면,
            n = k 일때 fiboRecursion(n) = fiboRecursion(n-1) + fiboRecursion(n-2) 이고, 뒤 두 항이 항상 올바른 피보나치 수를 반환하므로 참이다.
        """.trimIndent()
    }

    "Exercise 2" {
        fun power(x: Double, n: Int): Double = when (n) {
            // 0일때는 어덯게 하는지? 방법이 없나
            1 -> x
            else -> x * power(x, n - 1)
        }

        // power(0.0, 2) shouldBe 1
        power(1.0, 5) shouldBe 1.0
        power(2.0, 2) shouldBe 4.0
        power(5.0, 3) shouldBe 125.0
        power(1.5, 2) shouldBe 2.25
    }

    "Exercise 3" {
        fun factorialRecur(n: Int): Int = when (n) {
            0 -> 1
            else -> n * factorialRecur(n - 1)
        }
        factorialRecur(0) shouldBe 1
        factorialRecur(1) shouldBe 1
        factorialRecur(2) shouldBe 2
        factorialRecur(3) shouldBe 6
        factorialRecur(7) shouldBe 5040
    }

    "Exercise 4" {
        fun toBinary(n: Int): String = when {
            n == 0 -> "0"
            n == 1 -> "1"
            n % 2 == 1 -> toBinary(n / 2) + "1"
            n % 2 == 0 -> toBinary(n / 2) + "0"
            else -> throw Error("not defined")
        }

        toBinary(1) shouldBe "1"
        toBinary(0) shouldBe "0"
        toBinary(2) shouldBe "10"
        toBinary(5) shouldBe "101"
    }

    "Exercise 5" {
        fun replicate(n: Int, element: Int): List<Int> = when (n) {
            0 -> throw Error("not defined")
            1 -> listOf(element)
            else -> listOf(element).plus(replicate(n - 1, element))
        }

        replicate(3, 5) shouldBe listOf(5, 5, 5)
        replicate(2, 3) shouldBe listOf(3, 3)
        replicate(1, 3) shouldBe listOf(3)
    }

    "Exercise 6" {
        fun elem(num: Int, list: List<Int>): Boolean = when {
            list.isEmpty() -> false
            list.head() == num -> true
            list.head() != num -> elem(num, list.tail())
            else -> throw Error("not defined")
        }

        elem(3, listOf(3)) shouldBe true
        elem(1, listOf(1, 1)) shouldBe true
        elem(4, listOf(1, 2, 3)) shouldBe false
        elem(0, listOf()) shouldBe false
    }

//    "Exercise 7" {
//        fun takeSequence(n: Int, sequence: Sequence<Int>): List<Int> = when {
//        }
//    }
    // sequence 개념을 잘 모르겠다. 

    "Exercise 8" {
        fun quickSort(list: List<Int>): List<Int> = when {
            // 너무 간단하다...이럴수가
            list.isEmpty() -> listOf()
            list.size == 1 -> list
            else -> {
                val pivot = list.head()
                val (lesser, greater) = list.tail().partition { it <= pivot }
                quickSort(lesser) + pivot + quickSort(greater)
            }
        }

        quickSort((listOf())) shouldBe listOf()
        quickSort(listOf(5, 4, 3, 2, 1)) shouldBe listOf(1, 2, 3, 4, 5)
        quickSort(listOf(1, 1, 1, 1, 1)) shouldBe listOf(1, 1, 1, 1, 1)
        quickSort(listOf(4, 1, 3, 2, 5)) shouldBe listOf(1, 2, 3, 4, 5)
    }

    "Exercise 9" {
        fun gcd(m: Int, n: Int): Int = when {
            // 호제법
            m < 0 || n < 0 -> throw Error("not defined for negative integers")
            n == 0 -> m
            else -> gcd(n, m % n)
        }

        gcd(6, 3) shouldBe 3
        gcd(15, 7) shouldBe 1
        gcd(18, 4) shouldBe 2
    }

    "Exercise 10" {
        val memo = Array(100) { -1 }
        fun factorialRecurWithMemo(n: Int): Int = when {
            n == 0 -> 1
            memo[n] != -1 -> memo[n]
            else -> {
                val result = n * factorialRecurWithMemo(n - 1)
                memo[n] = result
                result
            }
        }

        factorialRecurWithMemo(0) shouldBe 1
        factorialRecurWithMemo(1) shouldBe 1
        factorialRecurWithMemo(2) shouldBe 2
        factorialRecurWithMemo(3) shouldBe 6
        factorialRecurWithMemo(7) shouldBe 5040
        factorialRecurWithMemo(12) shouldBe 479001600
    }

    "Exercise 11" {
        tailrec fun factorialFP(n: Int, memo: Int = 1): Int = when (n) {
            0 -> memo
            else -> factorialFP(n - 1, memo * n)
        }

        factorialFP(0) shouldBe 1
        factorialFP(1) shouldBe 1
        factorialFP(2) shouldBe 2
        factorialFP(3) shouldBe 6
        factorialFP(7) shouldBe 5040
        factorialFP(12) shouldBe 479001600
    }

    "Exercise 12" {
        """
            tailrec annotation을 붙여 꼬리재귀 형태로 변경함
            f(3) => f(2,3) => f(1,6)
        """.trimIndent()
    }

    "Exercise 13" {
        tailrec fun power(x: Double, n: Int, memo: Double = x): Double = when (n) {
            1 -> memo
            else -> power(x, n - 1, x * memo)
        }

        power(1.0, 5) shouldBe 1.0
        power(2.0, 2) shouldBe 4.0
        power(5.0, 3) shouldBe 125.0
        power(1.5, 2) shouldBe 2.25
    }

    "Exercise 14" {
        tailrec fun toBinary(n: Int, acc: String = ""): String = when {
            n == 0 -> "0$acc"
            n == 1 -> "1$acc"
            n % 2 == 0 -> toBinary(n / 2, "0$acc")
            n % 2 == 1 -> toBinary(n / 2, "1$acc")
            else -> throw Error("not defined")
        }

        toBinary(1) shouldBe "1"
        toBinary(0) shouldBe "0"
        toBinary(2) shouldBe "10"
        toBinary(5) shouldBe "101"
    }

    "Exercise 15" {
        tailrec fun replicate(n: Int, element: Int, memo: List<Int> = listOf()): List<Int> = when (n) {
            0 -> throw Error("not defined")
            1 -> memo.plus(element)
            else -> replicate(n - 1, element, memo.plus(element))
        }

        replicate(3, 5) shouldBe listOf(5, 5, 5)
        replicate(2, 3) shouldBe listOf(3, 3)
        replicate(1, 3) shouldBe listOf(3)
    }

    "Exercise 16" {
        tailrec fun elem(num: Int, list: List<Int>): Boolean = when {
            list.isEmpty() -> false
            list.head() == num -> true
            list.head() != num -> elem(num, list.tail())
            // 결과값이 boolean이라 가능한지?
            else -> throw Error("not defined")
        }

        elem(3, listOf(3)) shouldBe true
        elem(1, listOf(1, 1)) shouldBe true
        elem(4, listOf(1, 2, 3)) shouldBe false
        elem(0, listOf()) shouldBe false
    }

    "Exercise 17" {
        kotlin.math.round(sqrt(16.0) * 10.0) / 10.0 shouldBe 0.7
        kotlin.math.round(sqrt(25.0) * 10.0) / 10.0 shouldBe 0.8
    }

    "Exercise 18" {
        kotlin.math.round(trampoline(sqrtTramp(16.0)) * 10.0) / 10.0 shouldBe 0.7
        kotlin.math.round(trampoline(sqrtTramp(25.0)) * 10.0) / 10.0 shouldBe 0.8
    }

    "Exercise 19" {
        fun factorialFP(n: Int, memo: BigDecimal = BigDecimal(1)): Bounce<BigDecimal> = when (n) {
            0 -> Done(memo)
            else -> More { factorialFP(n - 1, memo.multiply(BigDecimal(n))) }
        }

        trampoline(factorialFP(0)) shouldBe BigDecimal(1)
        trampoline(factorialFP(1)) shouldBe BigDecimal(1)
        trampoline(factorialFP(2)) shouldBe BigDecimal(2)
        trampoline(factorialFP(12)) shouldBe BigDecimal(479001600)
        trampoline(factorialFP(100000))
    }
})

fun sqrt(n: Double): Double = when {
    n < 1 -> n
    else -> divideByTwo(kotlin.math.sqrt(n))
}

fun divideByTwo(n: Double): Double = when {
    n < 1 -> n
    else -> sqrt(n / 2)
}

fun sqrtTramp(n: Double): Bounce<Double> = when {
    n < 1 -> Done<Double>(n)
    else -> More { divideByTwoTramp(kotlin.math.sqrt(n)) }
}

fun divideByTwoTramp(n: Double): Bounce<Double> = when {
    n < 1 -> Done<Double>(n)
    else -> More { sqrtTramp(n / 2) }
}
