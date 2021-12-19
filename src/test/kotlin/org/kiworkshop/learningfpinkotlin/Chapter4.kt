package org.kiworkshop.learningfpinkotlin

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe

class Chapter4 : FunSpec({

    xcontext("P.99 연습문제 4-1") {
        test("PartialFunction 클래스에 invokeOrElse 함수와 orElse 함수를 추가해보자.") {
            class PartialFunction<in P, out R>(
                private val condition: (P) -> Boolean,
                private val f: (P) -> R
            ) : (P) -> R {

                override fun invoke(p: P): R = when {
                    condition(p) -> f(p)
                    else -> throw IllegalArgumentException("$p is not supported!")
                }

                fun invokeOrElse(p: P, default: @UnsafeVariance R): R = when {
                    condition(p) -> f(p)
                    else -> default
                }

                fun orElse(that: PartialFunction<@UnsafeVariance P, @UnsafeVariance R>): PartialFunction<P, R> =
                    PartialFunction(
                        condition = { this.condition(it) || that.condition(it) },
                        f = {
                            when {
                                this.condition(it) -> this.f(it)
                                that.condition(it) -> that.f(it)
                                else -> throw IllegalStateException("never happened!")
                            }
                        }
                    )

                fun isDefinedAt(p: P): Boolean = condition(p)
            }
        }
    }

    context("P.102 연습문제 4-2") {

        // 매개변수 2개를 받는 함수의 부분 적용 함수를 정의한 확장 함수
        fun <P1, P2, R> ((P1, P2) -> R).partial1(p1: P1): (P2) -> R = { this(p1, it) }
        fun <P1, P2, R> ((P1, P2) -> R).partial2(p2: P2): (P1) -> R = { this(it, p2) }

        // 위 부분 적용 함수를 사용하는 예시
        val func = { a: String, b: String -> a + b }
        val partiallyAppliedFunc1 = func.partial1("Hello")
        val result1 = partiallyAppliedFunc1("World!")

        result1 shouldBe "HelloWorld!"

        val partiallyAppliedFunc2 = func.partial2("World!")
        val result2 = partiallyAppliedFunc2("Hello")

        result2 shouldBe "HelloWorld!"

        test("매개변수 3개를 받는 부분 적용 함수 3개를 직접 구현하라.") {

            // 매개변수 4개를 받는 함수의 매개변수 3개를 받는 부분 적용 함수를 정의한 확장 함수
            fun <P1, P2, P3, P4, R> ((P1, P2, P3, P4) -> R).partial1(p1: P1, p2: P2, p3: P3): (P4) -> R =
                { this(p1, p2, p3, it) }

            fun <P1, P2, P3, P4, R> ((P1, P2, P3, P4) -> R).partial2(p1: P1, p2: P2, p4: P4): (P3) -> R =
                { this(p1, p2, it, p4) }

            fun <P1, P2, P3, P4, R> ((P1, P2, P3, P4) -> R).partial3(p1: P1, p3: P3, p4: P4): (P2) -> R =
                { this(p1, it, p3, p4) }

            val exampleFunc = { a: String, b: String, c: String, d: String -> a + b + c + d }

            val partiallyAppliedExampleFunc1 = exampleFunc.partial1("a", "b", "c")
            val result3 = partiallyAppliedExampleFunc1("d")

            result3 shouldBe "abcd"

            val partiallyAppliedExampleFunc2 = exampleFunc.partial2("a", "b", "d")
            val result4 = partiallyAppliedExampleFunc2("c")

            result4 shouldBe "abcd"

            val partiallyAppliedExampleFunc3 = exampleFunc.partial3("a", "c", "d")
            val result5 = partiallyAppliedExampleFunc3("b")

            result5 shouldBe "abcd"
        }
    }

    context("P.104 연습문제 4-3") {
        test("두 개의 매개변수를 받아서 큰 값을 반환하는 max 함수를, 커링을 사용할 수 있도록 구현하라.") {
            fun max(a: Int): (Int) -> Int = { b: Int -> if (a > b) a else b }

            max(10)(5) shouldBe 10
            max(11)(10) shouldBe 11
        }
    }

    context("P.105 연습문제 4-4") {
        test("두 개의 매개변수를 받아서 작은 값을 반환하는 min 함수를 curried 함수를 사용해서 작성하라.") {
            fun min(a: Int, b: Int) = if (a > b) b else a
            val curriedMin = ::min.curried()

            curriedMin(10)(5) shouldBe 5
            curriedMin(11)(10) shouldBe 10
        }
    }

    context("P.107 연습문제 4-5, 4-6") {

        fun max(list: List<Int>): Int {
            tailrec fun max(list: List<Int>, max: Int): Int = when {
                list.isEmpty() -> max
                else -> {
                    val head = list.head()
                    max(list.tail(), if (head > max) head else max)
                }
            }
            return max(list, Int.MIN_VALUE)
        }

        fun secondPower(x: Int): Int {
            tailrec fun power(x: Int, n: Int, acc: Int): Int = when (n) {
                0 -> acc
                else -> power(x, n - 1, acc * x)
            }
            return power(x, 2, 1)
        }

        test("4-5. 숫자(Int)의 리스트를 받아서 최댓값의 제곱을 구하는 함수를 작성해보자. 이때 반드시 max 함수와 power 함수를 만들어 합성해야한다.") {
            fun maxPower(list: List<Int>): Int = secondPower(max(list))

            maxPower(listOf(1, 2, 3, 4, 5)) shouldBe 25
            maxPower(listOf(-1, -2, -3, -4, -5)) shouldBe 1
        }

        test("4-6. 연습문제 4-5에서 작성한 함수를 compose 함수를 사용해서 다시 작성해보자.") {
            val composed = ::secondPower compose ::max

            composed(listOf(1, 2, 3, 4, 5)) shouldBe 25
            composed(listOf(-1, -2, -3, -4, -5)) shouldBe 1
        }
    }

    context(" P.111 연습문제 4-7, 4-8") {
        test(
            "4-7. 리스트의 값을 조건 함수에 적용했을 때, 결괏값이 참인 값의 리스트를 반환하는 takeWhile 함수를 꼬리 재귀로 작성해보자." +
                "예를 들어 입력 리스트가 1,2,3,4,5 로 구성되어 있을 때 조건 함수가 3보다 작은 값이면 1과 2로 구성된 리스트로 반환한다."
        ) {
            tailrec fun takeWhile(list: List<Int>, condition: (Int) -> Boolean, acc: List<Int> = listOf()): List<Int> =
                when {
                    list.isEmpty() -> acc
                    else -> {
                        val head: Int = list.head()
                        takeWhile(list.tail(), condition, if (condition(head)) acc + head else acc)
                    }
                }

            takeWhile(listOf(1, 2, 3, 4, 5), { it < 3 }) shouldBe listOf(1, 2)
            takeWhile(listOf(1, 2, 3, 4, 5), { it >= 3 }) shouldBe listOf(3, 4, 5)
        }

        test("4-8. 연습문제 4-7에서 작성한 takeWhile 를 수정하여, 무한대를 입력받을 수 있는 takeWhile 를 꼬리 재귀로 작성해보자.") {

            fun takeWhile(sequence: Sequence<Int>, condition: (Int) -> Boolean): List<Int> {
                val iterator = sequence.iterator()

                tailrec fun takeWhile(acc: List<Int>): List<Int> {
                    val next = iterator.next()
                    return when {
                        !condition(next) -> acc
                        else -> {
                            takeWhile(if (condition(next)) acc + next else acc)
                        }
                    }
                }

                return takeWhile(listOf())
            }

            takeWhile(generateSequence(1) { it + 1 }) { it <= 3 } shouldContainExactly listOf(1, 2, 3)
        }
    }
})
