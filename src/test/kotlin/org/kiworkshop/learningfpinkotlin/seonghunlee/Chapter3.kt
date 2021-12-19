package org.kiworkshop.learningfpinkotlin.seonghunlee

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldHaveMinLength
import java.lang.Math.sqrt
import java.math.BigDecimal

class Chapter3 : StringSpec({
    "ch3-1" {
        """
            피보나치의 수학적 귀납법 증명
            명제 : 피보나치 수열 fiboRecursion(n)은 음이 아닌 정수 n에 대해서 f(n-1) + f(n-2) 의 합을 올바르게 계산한다.
            n=0인 경우 0을 반환한다.
            n=1인 경우 1을 반환한다.
            임의의 양의정수 k에 대해서 n<k인 경우, f(n) 까지의 합을 올바르게 계산하여 반환한다고 가정한다.
            n=k인 경우, f(k -1)을 호출할때 이전의 가정에 의해 f(k-2), f(k-3)을 올바르게 계산한다. 이렇게 나온 값을 합하여 반환하므로 fiboRecursion(k)은 올바르게 계산한다.
        """ shouldHaveMinLength 0
    }

    fun assertThatParameterIsPowerFunction(power: (Double, Int) -> Double) {
        power(2.0, 0) shouldBe 1.0
        power(2.0, 3) shouldBe 8.0
        power(2.0, 10) shouldBe 1024.0
        power(3.0, 3) shouldBe 27.0
    }

    "ch3-2" {
        fun power(x: Double, n: Int): Double = when (n) {
            0 -> 1.0
            else -> x * power(x, n - 1)
        }

        assertThatParameterIsPowerFunction(::power)
    }

    fun assertThatParameterIsFactorial(factorial: (Int) -> Int) {
        factorial(1) shouldBe 1
        factorial(2) shouldBe 2
        factorial(3) shouldBe 6
        factorial(6) shouldBe 720
    }

    "ch3-3" {
        fun factorial(i: Int): Int = when (i) {
            0 -> 1
            1 -> 1
            else -> i * factorial(i - 1)
        }

        assertThatParameterIsFactorial(::factorial)
    }

    fun assertThatParameterIsDecimalToBinary(decimalToBinary: (Int) -> String) {
        decimalToBinary(10) shouldBe "1010"
    }
    "ch3-4" {
        fun decimalToBinary(n: Int): String = when {
            n == 0 -> "0"
            n == 1 -> "1"
            n % 2 == 0 -> decimalToBinary(n / 2) + "0"
            else -> decimalToBinary(n / 2) + "1"
        }
        assertThatParameterIsDecimalToBinary(::decimalToBinary)
    }

    fun assertThatParameterIsReplicate(replicate: (Int, Int) -> List<Int>) {
        replicate(3, 5) shouldBe listOf(5, 5, 5)
    }
    "ch3-5" {
        fun replicate(num1: Int, num2: Int): List<Int> = when (num1) {
            0 -> listOf()
            else -> replicate(num1 - 1, num2) + listOf(num2)
        }
        assertThatParameterIsReplicate(::replicate)
    }

    fun assertThatParameterIsElem(elem: (Int, List<Int>) -> Boolean) {
        elem(3, listOf(2, 3, 4)) shouldBe true
        elem(5, listOf(1, 2, 3)) shouldBe false
    }
    "ch3-6" {
        fun List<Int>.head() = first()
        fun List<Int>.tail() = drop(1)
        fun elem(num: Int, list: List<Int>): Boolean = when {
            list.isEmpty() -> false
            num == list.head() -> true
            else -> elem(num, list.tail())
        }

        assertThatParameterIsElem(::elem)
    }

    fun assertThatParameterIsTakeSequenceAndRepeat(
        takeSequence: (Int, Sequence<Int>) -> List<Int>,
        repeat: (Int) -> Sequence<Int>
    ) {
        takeSequence(5, repeat(3)) shouldBe listOf(3, 3, 3, 3, 3)
    }
    "ch3-7" {

        operator fun <T> Sequence<T>.plus(other: () -> Sequence<T>) = object : Sequence<T> {
            private val thisIterator: Iterator<T> by lazy { this@plus.iterator() }
            private val otherIterator: Iterator<T> by lazy { other().iterator() }
            override fun iterator() = object : Iterator<T> {
                override fun hasNext(): Boolean = thisIterator.hasNext() || otherIterator.hasNext()

                override fun next(): T = if (thisIterator.hasNext()) {
                    thisIterator.next()
                } else {
                    otherIterator.next()
                }
            }
        }

        fun takeSequence(n: Int, sequence: Sequence<Int>): List<Int> = when {
            n <= 0 -> listOf()
            !sequence.iterator().hasNext() -> listOf()
            else -> listOf(sequence.iterator().next()) + takeSequence(n - 1, sequence)
        }

        fun repeat(n: Int): Sequence<Int> = sequenceOf(n) + { repeat(n) }

        assertThatParameterIsTakeSequenceAndRepeat(::takeSequence, ::repeat)
    }

    fun partition(arr: IntArray, lo: Int, hi: Int): Int {
        val pivot = arr[hi]
        var i = lo - 1
        for (j: Int in lo..hi) {
            if (arr[j] <= pivot) {
                i += 1
                arr[i] = arr[j].also { arr[j] = arr[i] }
            }
        }
        return i
    }

    fun assertThatParameterIsQuicksort(quicksort: (IntArray, Int, Int) -> Any) {
        val intArr = intArrayOf(5, 3, 2, 1)
        quicksort(intArr, 0, 3)
        intArr shouldBe intArrayOf(1, 2, 3, 5)
    }
    "ch3-8" {
        fun quicksort(arr: IntArray, lo: Int, hi: Int) {
            if (lo >= hi || lo < 0) return
            val p = partition(arr, lo, hi)

            quicksort(arr, lo, p - 1)
            quicksort(arr, p + 1, hi)
        }
        assertThatParameterIsQuicksort(::quicksort)
    }

    fun assertThatParameterIsGcd(gcd: (Int, Int) -> Int) {
        gcd(20, 15) shouldBe 5
        gcd(12, 16) shouldBe 4
    }
    "ch3-9" {
        fun gcd(m: Int, n: Int): Int = when {
            n == 0 -> m
            else -> gcd(n, m % n)
        }

        assertThatParameterIsGcd(::gcd)
    }

    fun assertThatParameterIsFactorialMemo(factorialMemo: (Int) -> Int) {
        factorialMemo(4) shouldBe 24
    }
    "ch3-10" {
        var memo = Array(100, { -1 })
        fun factorialMemo(i: Int): Int = when {
            i == 0 -> 1
            i == 1 -> 1
            memo[i] != -1 -> memo[i]
            else -> {
                println("factorial($i)")
                memo[i] = i * factorialMemo(i - 1)
                memo[i]
            }
        }
        assertThatParameterIsFactorialMemo(::factorialMemo)
    }
    tailrec fun factorialFP(i: Int, acc: Int): Int = when (i) {
        1 -> acc
        else -> factorialFP(i - 1, acc * i)
    }

    fun assertThatParameterIsFactorialFP(factorialFP: (Int) -> Int) {
        factorialFP(5) shouldBe 120
    }
    "ch3-11" {
        fun factorialFP(i: Int): Int = factorialFP(i, 1)

        assertThatParameterIsFactorialFP(::factorialFP)
    }
    "ch3-12" { // 꼬리재귀 맞음
    }

    fun assertThatParameterIsPowerFunctionTailRec(power: (Double, Int) -> Double) {
        power(2.0, 0) shouldBe 1.0
        power(2.0, 3) shouldBe 8.0
        power(2.0, 10) shouldBe 1024.0
        power(3.0, 3) shouldBe 27.0
    }

    tailrec fun powerTailRec(x: Double, n: Int, acc: Double): Double = when (n) {
        0 -> 1.0
        1 -> x * acc
        else -> powerTailRec(x, n - 1, x * acc)
    }
    "ch3-13" {
        fun powerTailRec(x: Double, n: Int): Double = powerTailRec(x, n, 1.0)

        assertThatParameterIsPowerFunctionTailRec(::powerTailRec)
    }
    tailrec fun toBinaryTailrec(num: Int, acc: String = ""): String = when (num) {
        0 -> "0$acc"
        1 -> "1$acc"
        else -> {
            toBinaryTailrec(num / 2, "${num % 2}" + acc)
        }
    }

    fun assertThatParameterIsDecimalToBinaryTailrec(toBinary: (Int) -> String) {
        toBinaryTailrec(10) shouldBe "1010"
    }
    "ch3-14" {
        fun toBinary(num: Int): String = toBinaryTailrec(num, "")

        assertThatParameterIsDecimalToBinaryTailrec(::toBinary)
    }

    fun assertThatParameterIsReplicateTailrec(replicateTailrec: (Int, Int) -> List<Int>) {
        replicateTailrec(3, 5) shouldBe listOf(5, 5, 5)
    }

    tailrec fun replicateTailrec(num1: Int, num2: Int, acc: List<Int>): List<Int> = when (num1) {
        0 -> acc
        1 -> acc + num2
        else -> {
            replicateTailrec(num1 - 1, num2, acc + num2)
        }
    }
    "ch3-15" {
        fun replicateTailrec(num1: Int, num2: Int): List<Int> = replicateTailrec(num1, num2, listOf())
        assertThatParameterIsReplicateTailrec(::replicateTailrec)
    }

    fun assertThatParameterIsElemTailrec(elemTailrec: (Int, List<Int>, Boolean) -> Boolean) {
        elemTailrec(3, listOf(2, 3, 4), false) shouldBe true
        elemTailrec(5, listOf(1, 2, 3), false) shouldBe false
    }
    "ch3-16" {
        fun List<Int>.head() = first()
        fun List<Int>.tail() = drop(1)
        tailrec fun elemTailrec(num: Int, list: List<Int>, acc: Boolean): Boolean = when {
            list.isEmpty() -> false
            acc -> true
            num == list.head() -> elemTailrec(num, list.tail(), true)
            else -> elemTailrec(num, list.tail(), false)
        }

        assertThatParameterIsElemTailrec(::elemTailrec)
    }
    "ch3-17" {
        fun sqrtDivideBy2(n: Double): Double {
            fun divideBy2(n: Double): Double {
                val divided = n / 2
                println("into sqrtDivideBy2")
                val result = if (divided < 1) divided else sqrtDivideBy2(divided)
                println("out sqrtDivideBy2")
                return result
            }

            println("into divideBy2")
            val result = divideBy2(sqrt(n))
            println("out divideBy2")
            return result
        }
        println(sqrtDivideBy2(10.0))
    }
    "ch3-18" {
        fun sqrtDivideBy2(n: Double): Bounce<Double> {
            fun divideBy2(n: Double): Bounce<Double> {
                val divided = n / 2
                println("into sqrtDivideBy2")
                val result = if (divided < 1) Done(divided) else More { sqrtDivideBy2(divided) }
                println("out sqrtDivideBy2")
                return result
            }

            println("into divideBy2")
            val result = divideBy2(sqrt(n))
            println("out divideBy2")
            return result
        }

        println(trampoline(sqrtDivideBy2(10.0)))
    }

    "ch3-19" {
        fun factorialMT(i: BigDecimal, acc: BigDecimal): Bounce<BigDecimal> = when (i) {
            BigDecimal(1) -> Done(acc)
            else -> More {
                factorialMT(i - BigDecimal(1), acc * i)
            }
        }
        println(trampoline(factorialMT(BigDecimal(100000), BigDecimal(1))))
    }
})
