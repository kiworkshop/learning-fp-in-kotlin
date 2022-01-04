package org.kiworkshop.learningfpinkotlin

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Chapter4 : StringSpec({
    "Exercise 1: invokeOrElse / orElse" {
        class PartialFunction<P, R>(
            private val condition: (P) -> Boolean,
            private val f: (P) -> R
        ) : (P) -> R {

            // 인스턴스 이름을 함수로 보고 invoke를 실행함
            override fun invoke(p: P): R = when {
                condition(p) -> f(p)
                else -> throw IllegalArgumentException("$p is not supported.")
            }

            fun isDefinedAt(p: P): Boolean = condition(p)

            // 입력값 p가 조건에 맞지 않을 때 기본값 default 반환
            fun invokeOrElse(p: P, default: R): R = when {
                condition(p) -> f(p)
                else -> default
            }

            // 입력값 p가 조건에 맞으면 this 반환, 아닐경우 that 반환
            fun orElse(that: PartialFunction<P, R>): PartialFunction<P, R> = PartialFunction({
                this.isDefinedAt(it) || that.isDefinedAt(it)
            }, {
                when {
                    this.isDefinedAt(it) -> this(it)
                    that.isDefinedAt(it) -> that(it)
                    else -> throw IllegalArgumentException("$it is not supported")
                }
            })
        }

        val isEven = PartialFunction<Int, String>({ it.rem(2) == 0 }, { "$it is even" })
        isEven.invokeOrElse(3, "it's an odd number") shouldBe "it's an odd number"

        val isOdd = PartialFunction<Int, String>({ it.rem(2) == 1 }, { "$it is odd" })
        isEven.orElse(isOdd)(3) shouldBe "3 is odd"
    }

    "Exercise 2: 매개변수 3개를 받는 부분 적용 함수" {
        // page 101
        fun <P1, P2, R> ((P1, P2) -> R).partial1(p1: P1): (P2) -> R {
            // p1은 이미 적용되었다. (page 102) == 클로저에 올라가 있다.
            return { p2 ->
                // p2는 마지막에 적용
                this(p1, p2)
            }
        }

        fun <P1, P2, R> ((P1, P2) -> R).partial2(p2: P2): (P1) -> R {
            // p2는 이미 적용되었다.
            return { p1 ->
                // p1는 마지막에 적용
                this(p1, p2)
            }
        }

        fun <P1, P2, P3, R> ((P1, P2, P3) -> R).partialThree(p1: P1): (P2, P3) -> R {
            // p1은 이미 적용됨
            return { p2: P2, p3: P3 ->
                // p3 는 마지막에 적용
                this(p1, p2, p3)
            }
        }
    }

    "Exercise 3: 두개의 매개변수를 받아서 큰 값을 반환하는 max 함수를 커링을 사용해 구현" {
        fun curriedMax(p1: Int): (Int) -> (Int) = { p2 ->
            kotlin.math.max(p1, p2)
        }
        curriedMax(3)(4) shouldBe 7
    }

    "Exercise 4: 두개의 매개변수를 받아서 큰 값을 반환하는 max 함수를 curried 함수를 사용해 구현" {
        fun <P1, P2, R> ((P1, P2) -> R).curried(): (P1) -> (P2) -> R = { p1: P1 ->
            { p2: P2 -> this(p1, p2) }
        }

        val customMin = { a: Int, b: Int ->
            when {
                a <= b -> a
                else -> b
            }
        }
        customMin.curried()(3)(5) shouldBe 3
    }

    "Exercise 5: 숫자의 리스트를 받아서 최댓값의 제곱을 구하는 함수. max / power 함수 합성" {
        val max = { i: List<Int> -> i.maxOrNull()!! }
        val power = { i: Int -> i * i }

        power(max(listOf(1, 2, 3, 4, 5))) shouldBe 25
    }

    "Exercise 6: compose를 사용해 다시 작성" {
        infix fun <F, G, R> ((F) -> R).compose(g: (G) -> F): (G) -> R {
            return { gInput: G -> this(g(gInput)) }
        }

        val max = { i: List<Int> -> i.maxOrNull()!! }
        val power = { i: Int -> i * i }

        val composed = power compose max
        composed(listOf(3, 2, 1)) shouldBe 9
    }

    "Exercise 7: 리스트에 조건 함수 적용하여 참인 요소의 리스트를 반환" {
        tailrec fun <P1> takeWhile(list: List<P1>, acc: List<P1> = listOf(), evaluate: (P1) -> Boolean): List<P1> =
            when {
                list.isEmpty() -> acc
                evaluate(list.head()) -> takeWhile(
                    list.tail(),
                    acc + list.head(),
                    evaluate,
                )
                else -> acc
            }

        fun compareFunc(i: Int): Boolean = i < 3
        takeWhile(listOf(4, 3, 1, 2, 5)) { it < 3 } shouldBe listOf(1, 2)
    }

    "Exercise 8: sequence로 바꿔서 구현" {
        tailrec fun <P1> takeWhile(
            evaluate: (P1) -> Boolean,
            sequence: Sequence<P1>,
            acc: List<P1> = listOf()
        ): List<P1> =
            when {
                sequence.none() -> acc
                else -> {
                    takeWhile(
                        evaluate,
                        sequence.drop(1),
                        acc + if (evaluate(sequence.first())) listOf(sequence.first()) else listOf()
                    )
                }
            }

        fun compareFunc(i: Int): Boolean = i < 3
        takeWhile(::compareFunc, generateSequence(1) { x -> x + 1 }) shouldBe listOf(1, 2)
    }
})
