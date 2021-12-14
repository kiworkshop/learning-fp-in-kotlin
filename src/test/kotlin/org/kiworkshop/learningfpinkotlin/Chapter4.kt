package org.kiworkshop.learningfpinkotlin

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe

class Chapter4 : StringSpec({
    "Exercise 4-1" {
        class PartialFunction<in P, out R>(
            private val condition: (P) -> Boolean,
            private val f: (P) -> R
        ) : (P) -> R {
            override fun invoke(p: P): R = when {
                condition(p) -> f(p)
                else -> throw IllegalArgumentException("$p isn't supported.")
            }

            fun invokeOrElse(p: P, default: @UnsafeVariance R): R = if (condition(p)) f(p) else default
            fun orElse(that: PartialFunction<@UnsafeVariance P, @UnsafeVariance R>): PartialFunction<P, R> {
                return PartialFunction(
                    { p -> this.condition(p) || that.condition(p) },
                    { p ->
                        when {
                            this.condition(p) -> this.f(p)
                            else -> that.f(p)
                        }
                    }
                )
            }
        }

        val condition: (Int) -> Boolean = { it in 1..3 }
        val body: (Int) -> String = { "$it is in 1..3" }
        val default = "don't worry"
        val oneTowThree = PartialFunction(condition, body)

        oneTowThree.invokeOrElse(2, default) shouldBe "2 is in 1..3"
        oneTowThree.invokeOrElse(4, default) shouldBe "don't worry"

        val oneTowThreeOrElse = oneTowThree.orElse(PartialFunction({ it in 4..10 }, { "$it is in 4..10" }))
        oneTowThreeOrElse(2) shouldBe "2 is in 1..3"
        oneTowThreeOrElse(10) shouldBe "10 is in 4..10"
        shouldThrow<IllegalArgumentException> {
            oneTowThreeOrElse(11)
        }
    }

    "Exercise 4-2" {
        fun <P1, P2, P3, R> ((P1, P2, P3) -> R).partial1(p1: P1): (P2, P3) -> R =
            { p2, p3 -> this(p1, p2, p3) }

        fun <P1, P2, P3, R> ((P1, P2, P3) -> R).partial2(p2: P2): (P1, P3) -> R =
            { p1, p3 -> this(p1, p2, p3) }

        fun <P1, P2, P3, R> ((P1, P2, P3) -> R).partial3(p3: P3): (P1, P2) -> R =
            { p1, p2 -> this(p1, p2, p3) }

        val func = { a: String, b: String, c: String -> a + b + c }

        val paf1 = func.partial1("Hello")
        paf1("My", "World") shouldBe "HelloMyWorld"
        val paf2 = func.partial2("My")
        paf2("Hello", "World") shouldBe "HelloMyWorld"
        val paf3 = func.partial3("World")
        paf3("Hello", "My") shouldBe "HelloMyWorld"
    }

    "Exercise 4-3" {
        fun maxNum(a: Int) = { b: Int -> if (a >= b) a else b }

        maxNum(5)(10) shouldBe 10
        maxNum(10)(5) shouldBe 10
    }

    "Exercise 4-4" {
        fun <P1, P2, R> ((P1, P2) -> R).curried(): (P1) -> (P2) -> R =
            { p1: P1 -> { p2: P2 -> this(p1, p2) } }

        val minNum = { a: Int, b: Int -> if (a >= b) b else a }
        val curried = minNum.curried()

        curried(5)(10) shouldBe 5
        curried(10)(5) shouldBe 5
    }

    "Exercise 4-5" {
        val max = { i: List<Int> -> i.maxOrNull() }
        val power = { i: Int? -> if (i != null) i * i else null }

        power(max(listOf(1, 2, 3))) shouldBe 9
        power(max(listOf())) shouldBe null
    }

    "Exercise 4-6" {
        infix fun <F, G, R> ((F) -> R).compose(g: (G) -> F): (G) -> R = { gInput: G -> this(g(gInput)) }

        val max = { i: List<Int> -> i.maxOrNull() }
        val power = { i: Int? -> if (i != null) i * i else null }

        val composed = power compose max

        composed(listOf(1, 2, 3)) shouldBe 9
        composed(listOf()) shouldBe null
    }

    "Exercise 4-7" {
        tailrec fun <P> takeWhile(list: List<P>, acc: List<P> = listOf(), func: (P) -> Boolean): List<P> = when {
            list.isEmpty() -> acc
            else -> takeWhile(list.tail(), acc + if (func(list.head())) listOf(list.head()) else listOf(), func)
        }

        takeWhile(listOf(1, 2, 3, 4, 5, 6)) { it % 2 == 0 } shouldContainExactly listOf(2, 4, 6)
    }

    "Exercise 4-8" {
        fun <P> takeWhile(sequence: Sequence<P>, func: (P) -> Boolean): List<P> {
            tailrec fun <P> recursive(iterator: Iterator<P>, acc: List<P> = listOf(), func: (P) -> Boolean): List<P> {
                return when {
                    !iterator.hasNext() -> acc
                    else -> {
                        val next = iterator.next()
                        when {
                            !func(next) -> acc
                            else -> recursive(iterator, acc + next, func)
                        }
                    }
                }
            }

            return recursive(sequence.iterator(), listOf(), func)
        }

        takeWhile(generateSequence(1) { it + 1 }) { it in 1..5 } shouldContainExactly listOf(1, 2, 3, 4, 5)
    }
})
