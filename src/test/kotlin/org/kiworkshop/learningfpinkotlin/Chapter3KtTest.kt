package org.kiworkshop.learningfpinkotlin

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe

class Chapter3KtTest : FunSpec({

    test("Exercise 3-2") {
        power(3.0, 0) shouldBe 1.0
        power(3.0, 4) shouldBe 81.0
        power(2.4, 2) shouldBe 5.76
        power(2.0, 8) shouldBe 256.0
    }

    test("Exercise 3-3") {
//        factorial(0) shouldBe 1
//        factorial(1) shouldBe 1
//        factorial(2) shouldBe 2
//        factorial(3) shouldBe 6
        factorial(10) shouldBe (10 * 9 * 8 * 7 * 6 * 5 * 4 * 3 * 2 * 1)
        factorial(10) shouldBe (10 * 9 * 8 * 7 * 6 * 5 * 4 * 3 * 2 * 1)
    }

    test("Exercise 3-4") {
        toBinary(1) shouldBe "1"
        toBinary(2) shouldBe "10"
        toBinary(3) shouldBe "11"
        toBinary(4) shouldBe "100"
        toBinary(5) shouldBe "101"
        toBinary(6) shouldBe "110"
    }

    test("Exercise 3-5") {
        replicate(3, 5) shouldBe listOf(5, 5, 5)
        replicate(0, 2) shouldBe listOf()
        replicate(1, 3) shouldBe listOf(3)
        replicate(4, 0) shouldBe listOf(0, 0, 0, 0)
    }

    test("Exercise 3-6") {
        elem(3, listOf(1, 5, 3, 2)) shouldBe true
        elem(7, listOf(0, 3, 5)) shouldBe false
    }

//    fun repeat(n: Int): Sequence<Int> = generateSequence(n) { it }
//    fun repeat(n: Int): Sequence<Int> = sequenceOf(n) + repeat(n)
    fun repeat(n: Int): Sequence<Int> = sequenceOf(n) + { repeat(n) }
    test("Exercise 3-7") {
        takeSequence(5, repeat(3)) shouldBe listOf(3, 3, 3, 3, 3)
        takeSequence(0, repeat(4)) shouldBe listOf()
        takeSequence(1, repeat(7)) shouldBe listOf(7)
    }

    test("Exercise 3-8") {
        val l1 = mutableListOf(2, 4, 1, 5, 3)
        partition(l1, 0, 4) shouldBe 2
        l1 shouldBe listOf(2, 1, 3, 5, 4)

        val l2 = mutableListOf(1, 2, 3)
        partition(l2, 0, 2) shouldBe 2
        l2 shouldBe listOf(1, 2, 3)

        quicksort(listOf(2, 4, 1, 5, 3), 0, 4) shouldBe listOf(1, 2, 3, 4, 5)
        quicksort(listOf(5, 4, 3, 2, 1), 0, 4) shouldBe listOf(1, 2, 3, 4, 5)
        quicksort(listOf(1), 0, 0) shouldBe listOf(1)
        quicksort(listOf(2, 1), 0, 1) shouldBe listOf(1, 2)
        quicksort(listOf(1, 5, 2), 0, 2) shouldBe listOf(1, 2, 5)
    }

    test("Exercise 3-9") {
        gcd(2, 4) shouldBe 2
        gcd(4, 2) shouldBe 2
        gcd(4, 8) shouldBe 4
        gcd(8, 4) shouldBe 4
        gcd(3, 5) shouldBe 1
    }

    test("Exercise 3-10") {
        factorialMemoization(10) shouldBe (10 * 9 * 8 * 7 * 6 * 5 * 4 * 3 * 2 * 1)
        memo[10] shouldBe (10 * 9 * 8 * 7 * 6 * 5 * 4 * 3 * 2 * 1)
        factorialMemoization(10) shouldBe (10 * 9 * 8 * 7 * 6 * 5 * 4 * 3 * 2 * 1)
    }

    test("Exercise 3-11") {

    }

    test("Exercise 3-12") {

    }

    test("Exercise 3-13") {

    }

    test("Exercise 3-14") {

    }

    test("Exercise 3-15") {

    }

    test("Exercise 3-16") {

    }

    test("Exercise 3-17") {

    }

    test("Exercise 3-18") {

    }

    test("Exercise 3-19") {

    }
})
