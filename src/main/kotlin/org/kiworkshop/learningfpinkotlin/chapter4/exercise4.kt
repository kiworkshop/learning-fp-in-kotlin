package org.kiworkshop.learningfpinkotlin.chapter4

import org.kiworkshop.learningfpinkotlin.compose
import org.kiworkshop.learningfpinkotlin.head
import org.kiworkshop.learningfpinkotlin.tail
import java.lang.Math.pow
import kotlin.math.max
import kotlin.math.min

/*
* 부분 함수 만들기
* 연습문제 4-1
* */
class PartialFunction<P, R>(
    private val condition: (P) -> Boolean,
    private val f: (P) -> R
) : (P) -> R {

    override fun invoke(p: P): R = when {
        condition(p) -> f(p)
        else -> throw IllegalArgumentException("$p isn't supported.")
    }

    fun isDefinedAt(p: P): Boolean = condition(p)

    fun invokeOrElse(p: P, default: R): R = if (condition(p)) f(p) else default

    fun orElse(that: PartialFunction<P, R>): PartialFunction<P, R> =
        PartialFunction(
            { p -> if (this.isDefinedAt(p)) this.condition(p) else that.condition(p) },
            { p -> if (this.isDefinedAt(p)) this.f(p) else that.f(p) }
        )
}

/*
* 연습문제 4-2
* */
fun <P1, P2, P3, R> ((P1, P2, P3) -> R).partial1(p1: P1): (P2, P3) -> R {
    return { p2, p3 -> this(p1, p2, p3) }
}

fun <P1, P2, P3, R> ((P1, P2, P3) -> R).partial2(p2: P2): (P1, P3) -> R {
    return { p1, p3 -> this(p1, p2, p3) }
}

fun <P1, P2, P3, R> ((P1, P2, P3) -> R).partial3(p3: P3): (P1, P2) -> R {
    return { p1, p2 -> this(p1, p2, p3) }
}

/*
* 연습문제 4-3
* */
fun getMax(a: Int) = { b: Int -> max(a, b) }

/*
* 연습문제 4-4
* */
fun <P1, P2, R> ((P1, P2) -> R).curried(): (P1) -> (P2) -> R =
    { p1: P1 -> { p2: P2 -> this(p1, p2) } }

val getMin = { a: Int, b: Int -> min(a, b) }.curried()

/*
* 연습문제 4-5
* */
val getMax: ((List<Int>) -> Int) = { list: List<Int> -> list.maxOrNull() ?: 0 }
val getPower: ((Int) -> Int) = { int: Int -> pow(int.toDouble(), 2.0).toInt() }
fun getMaxPower(list: List<Int>): Int = getPower(getMax(list))

/*
* 연습문제 4-6
* */
infix fun <F, G, R> ((F) -> R).compose(g: (G) -> F): (G) -> R {
    return { gInput: G -> this(g(gInput)) }
}

val composed = getPower compose getMax

/*
* 연습문제 4-7
* */
tailrec fun <P> takeWhile(func: (P) -> Boolean, list: List<P>, acc: List<P> = listOf()): List<P> = when {
    list.isEmpty() -> acc
    else -> {
        var takeWhileList = acc
        if (func(list.head()))
            takeWhileList = acc + listOf(list.head())
        takeWhile(func, list.tail(), takeWhileList)
    }
}

/*
* 연습문제 4-8
* */
tailrec fun <P> takeWhileSeq(func: (P) -> Boolean, list: Sequence<P>, acc: Sequence<P> = sequenceOf()): Sequence<P> =
    when {
        list.none() -> acc
        !func(list.head()) -> acc
        else -> takeWhileSeq(func, list.tail(), acc + list.head())
    }
