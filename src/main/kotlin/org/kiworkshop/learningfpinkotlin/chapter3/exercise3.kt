package org.kiworkshop.learningfpinkotlin.chapter3

import org.kiworkshop.learningfpinkotlin.head
import org.kiworkshop.learningfpinkotlin.plus
import org.kiworkshop.learningfpinkotlin.tail
import java.math.BigDecimal
import kotlin.math.sqrt

fun main() {

    println(fibo(10))
    println(power(5.0, 3))
    println(factorial(7))
    println(toBinary(64))
    println(replicate(3, 4))
    println(elem(1, listOf(0, 0, 2, 3, 4)))
    println(takeSequence(6, repeat(3)))
//    println(quicksort(listOf(3, 2, 4, 1, 5), 0, 4))
    println(factorialFP(7, 7))
    println(trailedPower(5.0, 3, 5.0))
    println(trailedToBinary(13, ""))
    println(trailedReplicate(3, 4, emptyList()))
    println(trailedElem(1, false, listOf(0, 0, 2, 3, 4)))
    println(trampoline(trampFactorial(5, BigDecimal(5))))
}

/*
* 명제: fiboRecursion(n)은 음이 아닌 정수 n에 대해서 n번째 피보나치 수를 리턴한다.
* n이 0인 경우, 0을 반환하므로 참이다.
* n이 1인 경우, 1을 반환하므로 참이다.
* 1보다 큰 양의 정수 k에 대해서, fiboRecursion(k) = fiboRecursion(k - 1) + fiboRecursion(k - 2)가 k번째
* 피보나치 수를 리턴한다고 가정한다.
*
* k+1번째 피보나치 수는 k와 k-1번째 피보나치 수의 합인데,
* fiboRecursion(k)과 fiboRecursion(k - 1)는 k, k-1번째 피보나치 수를 반환함으로
* fiboRecursion(k + 1) = fiboRecursion(k) + fiboRecursion(k - 1)는 k+1번째 피보나치 수를 리턴한다.
* */

fun fibo(n: Int): Int = when (n) {
    0 -> 0
    1 -> 1
    else -> fibo(n - 1) + fibo(n - 2)
}

/*
* 연습문제 3-2
* */
fun power(x: Double, n: Int): Double = when (n) {
    1 -> x
    else -> x * power(x, n - 1)
}

/*
* 연습문제 3-3
* */
fun factorial(n: Int): Int = when (n) {
    0 -> 1
    else -> n * factorial(n - 1)
}

/*
* 연습문제 3-4
* */
fun toBinary(n: Int): String = when (n) {
    0, 1 -> n.toString()
    else -> toBinary(n / 2) + (n % 2).toString()
}

/*
* 연습문제 3-5
* */
fun replicate(n: Int, element: Int): List<Int> = when (n) {
    1 -> listOf(element)
    else -> listOf(element) + replicate(n - 1, element)
}

/*
* 연습문제 3-6
* */
fun elem(num: Int, list: List<Int>): Boolean = when {
    list.size == 1 -> list.head() == num
    else -> list.head() == num || elem(num, list.tail())
}

/*
* 연습문제 3-7
* */
fun takeSequence(n: Int, sequence: Sequence<Int>): List<Int> = when {
    sequence.none() -> listOf()
    n == 0 -> listOf()
    else -> sequence.take(n).toList()
}

fun repeat(n: Int): Sequence<Int> = sequenceOf(n) + { repeat(n) }

/*
* 연습문제 3-8
* ,,ing
* */

/*
* 연습문제 3-9
* */
fun gcd(m: Int, n: Int): Int = when {
    m == 0 -> n
    n == 0 -> m
    else -> gcd(n, m % n)
}

/*
* 연습문제 3-10
* */
private var memo = Array(100) { -1 }
fun factorialWithMemo(n: Int): Int = when {
    0 == n -> 1
    memo[n] != -1 -> memo[n]
    else -> {
        memo[n] = n * factorial(n - 1)
        memo[n]
    }
}

/*
* 연습문제 3-11,12
* 'tailrec'은 언어차원의 어노테이션으로
* 꼬리재귀에 부합하지 않으면 경고 메시지를 준다.
* 꼬리재귀는 스택을 사용하지 않고, 반복문과 동일한 호출 과정을 가진다.
* */
tailrec fun factorialFP(n: Int, aac: Int): Int = when (n) {
    1 -> aac
    else -> factorialFP(n - 1, aac * (n - 1))
}

/*
* 연습문제 3-13
* */
tailrec fun trailedPower(x: Double, n: Int, aac: Double): Double = when (n) {
    1 -> aac
    else -> trailedPower(x, n - 1, x * aac)
}

/*
* 연습문제 3-14
* */
tailrec fun trailedToBinary(n: Int, acc: String): String = when (n) {
    0, 1 -> (acc + n.toString()).reversed()
//    else -> toBinary(n / 2) + (n % 2).toString()
    else -> trailedToBinary(n / 2, acc + (n % 2).toString())
}

/*
* 연습문제 3-15
* */
tailrec fun trailedReplicate(n: Int, element: Int, acc: List<Int>): List<Int> = when (n) {
    1 -> acc + listOf(element)
    else -> trailedReplicate(n - 1, element, acc + listOf(element))
}

/*
* 연습문제 3-16
* */
tailrec fun trailedElem(num: Int, isExist: Boolean, list: List<Int>): Boolean = when {
    list.size == 1 -> isExist || list.head() == num
    else -> trailedElem(num, isExist || list.head() == num, list.tail())
}

/*
* 연습문제 3-17
* */
fun mutualSqrt(n: Double): Double = when {
    n < 1 -> n
    else -> mutualDivide(sqrt(n))
}

fun mutualDivide(n: Double): Double = when (n) {
    0.0 -> n
    else -> mutualSqrt(n / 2)
}

/*
* 연습문제 3-18
* */
sealed class Bounce<A>
data class Done<A>(val result: A) : Bounce<A>()
data class More<A>(val thunk: () -> Bounce<A>) : Bounce<A>()

tailrec fun <A> trampoline(bounce: Bounce<A>): A = when (bounce) {
    is Done -> bounce.result
    is More -> trampoline(bounce.thunk())
}

fun trampMutualSqrt(n: Double): Bounce<Double> = when {
    n < 1 -> Done(n)
    else -> More { trampMutualDivide(sqrt(n)) }
}

fun trampMutualDivide(n: Double): Bounce<Double> = when (n) {
    0.0 -> Done(n)
    else -> More { trampMutualSqrt(sqrt(n)) }
}

/*
* 연습문제 3-19
* */
fun trampFactorial(n: Int, aac: BigDecimal): Bounce<BigDecimal> = when (n) {
    1 -> Done(aac)
    else -> More { trampFactorial(n - 1, aac * (n - 1).toBigDecimal()) }
}
