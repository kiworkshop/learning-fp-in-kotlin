package org.kiworkshop.learningfpinkotlin

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Chapter3 : FunSpec({

    xcontext("P.64 연습문제 3-1") {
        test("재귀로 구현한 피보나치 문제를 수학적 귀납법으로 증명해보자.") {
            """
                   * 명제: func(n)은 음이 아닌 정수 n에 대해서 n번째 피보나치 값을 올바르게 계산한다.
                   * n=0 인 경우, 0을 반환하므로 참이다.
                   * 임의의 양의 정수 k에 대해서 n < k인 경우, 0에서 n번째 각각의 피보나치 값을 올바르게 계산해서 반환한다고 가정한다.
                   * n = k인 경우 func(k-1)을 호출할 때 위 가정에 의해서 func(k-1)은 k-1 번째 피보나치 값을 올바르게 반환한다. func(k-2) 또한 동일하다. 
                     func(n)은 func(k-1)과 func(k-2)를 더한 값을 반환한다. 그래서 func(n)은 n번째 피보나치 값을 올바르게 반환한다.
            """.trimIndent()
        }
    }

    context("P.64 연습문제 3-2") {
        test("X의 n 승을 구하는 함수를 재귀로 구현해보자.") {
            fun power(x: Double, n: Int): Double = when (n) {
                0 -> 1.0
                else -> x * power(x, n - 1)
            }

            power(2.0, 0) shouldBe 1.0
            power(2.0, 1) shouldBe 2.0
            power(2.0, 5) shouldBe 32.0
            power(2.0, 10) shouldBe 1024.0
        }
    }

    context("P.65 연습문제 3-3") {
        test("입력 n의 펙토리얼인 n!를 구하는 함수를 재귀로 구현해보자.") {
            fun factorial(n: Int): Int = when (n) {
                0 -> 1
                else -> {
                    println("factorial($n)")
                    n * factorial(n - 1)
                }
            }

            factorial(0) shouldBe 1
            println("============")
            factorial(1) shouldBe 1
            println("============")
            factorial(3) shouldBe 6
            println("============")
            factorial(10) shouldBe 3628800
        }
    }

    context("P.68 연습문제 3-4") {
        test("10진수 숫자를 입력받아서 2진수 문자열을 변환하는 함수를 작성하라.") {
            fun toBinary(n: Int): String = when (n) {
                0 -> "0"
                1 -> "1"
                else -> toBinary(n / 2) + (n % 2).toString()
            }

            toBinary(0) shouldBe "0"
            toBinary(1) shouldBe "1"
            toBinary(8) shouldBe "1000"
            toBinary(100) shouldBe "1100100"
        }
    }

    context("P.68 연습문제 3-5") {
        test("숫자를 두개 입력받은 후 두 번째 숫자를 첫 번째 숫자만큼 가지고 있는 리스트를 반환하는 함수를 만들어보자. 예를 들어 replicate(3, 5)를 입력하면 5가 3개 있는 리스트 [5, 5, 5]를 반환한다.") {
            fun replicate(n: Int, element: Int): List<Int> = when (n) {
                0 -> emptyList()
                else -> replicate(n - 1, element).plus(element)
            }

            replicate(3, 5) shouldBe listOf(5, 5, 5)
        }
    }

    context("P.69 연습문제 3-6") {
        test("입력값 n이 리스트에 존재하는지 확인하는 함수를 재귀로 작성해보자.") {
            fun elem(num: Int, list: List<Int>): Boolean = when {
                list.isEmpty() -> false
                else -> num == list.head() || elem(num, list.tail())
            }

            elem(3, listOf(1, 2, 3)) shouldBe true
            elem(3, listOf(1, 1, 1)) shouldBe false
        }
    }

    context("P.71 연습문제 3-6") {

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

        test("코드 3-9 take 함수를 참고하여 repeat 함수를 테스트하기 위해서 사용한 takeSequence 함수를 작성해보자. 그리고 repeat 함수가 잘 동작하는지 테스트 해보자.") {
            fun repeat(n: Int): Sequence<Int> = sequenceOf(n) + { repeat(n) }

            // TODO: 2021/12/06 잘 모르겠네...ㅇㅅㅇ
//            fun takeSequence(n: Int, sequence: Sequence<Int>): List<Int> {
//            }
        }
    }

    context("P.72 연습문제 3-8") {
        test("퀵정렬 알고리즘의 quicksort 함수를 작성해보자.") {
            // TODO: 2021/12/06 임시 스킵
        }
    }

    context("P.72 연습문제 3-9") {
        test("최대공약수를 구하는 gcd 함수를 작성해보자.") {
            fun gcd(n: Int, m: Int): Int = when (n) {
                0 -> m
                else -> gcd(m % n, n)
            }

            gcd(4, 6) shouldBe 2
            gcd(10, 12) shouldBe 2
            gcd(3, 9) shouldBe 3
        }
    }

    context("P.74 연습문제 3-10") {
        test("연습문제 3-3에서 작성한 factorial 함수를 메모이제이션을 사용해서 개선해보라.") {
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

            factorial(0) shouldBe 1
            println("============")
            factorial(1) shouldBe 1
            println("============")
            factorial(3) shouldBe 6
            println("============")
            factorial(10) shouldBe 3628800
        }
    }

    context("P.76 연습문제 3-11") {
        test("연습문제 3-10에서 작성한 factorial 함수를 함수형 프로그래밍에 적합한 방식으로 개선해보라.") {
            tailrec fun factorial(n: Int, number: Int): Int = when (n) {
                0, 1 -> number
                else -> factorial(n - 1, n * number)
            }

            fun factorial(n: Int) = factorial(n, 1)

            factorial(0) shouldBe 1
            factorial(1) shouldBe 1
            factorial(3) shouldBe 6
            factorial(10) shouldBe 3628800
        }
    }

    context("P.77 연습문제 3-12") {
        test("연습문제 3-11에서 작성한 factorial 함수가 꼬리 재귀인지 확인해보자. 만약 꼬리 재귀가 아니라면 최적화되도록 수정하자.") {
            fun factorial(n: Int): Int {
                tailrec fun factorial(n: Int, number: Int): Int = when (n) {
                    0, 1 -> number
                    else -> factorial(n - 1, n * number)
                }

                return factorial(n, 1)
            }

            factorial(0) shouldBe 1
            factorial(1) shouldBe 1
            factorial(3) shouldBe 6
            factorial(10) shouldBe 3628800
        }
    }

    context("P.77 연습문제 3-13") {
        test("연습문제 3-2에서 작성한 power 함수가 꼬리 재귀인지 확인해보자. 만약 꼬리 재귀가 아니라면 최적화되도록 수정하자.") {
            fun power(x: Double, n: Int): Double {
                tailrec fun power(x: Double, n: Int, acc: Double): Double = when (n) {
                    0 -> acc
                    else -> power(x, n - 1, acc * x)
                }
                return power(x, n, 1.0)
            }

            power(2.0, 0) shouldBe 1.0
            power(2.0, 1) shouldBe 2.0
            power(2.0, 5) shouldBe 32.0
            power(2.0, 10) shouldBe 1024.0
        }
    }

    context("P.81 연습문제 3-14") {
        test("연습문제 3-4에서 작성한 toBinary 함수가 꼬리 재귀인지 확인해보자. 만약 꼬리 재귀가 아니라면 개선해보자.") {

            fun toBinary(n: Int): String {
                tailrec fun toBinary(n: Int, acc: String): String = when (n) {
                    0 -> "0"
                    1 -> "1$acc"
                    else -> toBinary(n / 2, (n % 2).toString() + acc)
                }

                return toBinary(n, "")
            }

            toBinary(0) shouldBe "0"
            toBinary(1) shouldBe "1"
            toBinary(8) shouldBe "1000"
            toBinary(100) shouldBe "1100100"
        }
    }

    context("P.81 연습문제 3-15") {
        test("연습문제 3-5에서 작성한 replicate 함수가 꼬리 재귀인지 확인해보자. 만약 꼬리 재귀가 아니라면 개선해보자.") {
            fun replicate(n: Int, element: Int): List<Int> {
                tailrec fun replicate(n: Int, element: Int, acc: List<Int>): List<Int> = when (n) {
                    0 -> emptyList()
                    1 -> acc.plus(element)
                    else -> replicate(n - 1, element, acc.plus(element))
                }

                return replicate(n, element, emptyList())
            }

            replicate(3, 5) shouldBe listOf(5, 5, 5)
        }
    }
})
