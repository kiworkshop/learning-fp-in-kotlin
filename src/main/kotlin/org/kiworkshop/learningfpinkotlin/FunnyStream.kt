package org.kiworkshop.learningfpinkotlin

import org.kiworkshop.learningfpinkotlin.FunnyStream.Cons
import org.kiworkshop.learningfpinkotlin.FunnyStream.Nil

sealed class FunnyStream<out T> {
    object Nil : FunnyStream<Nothing>()
    data class Cons<out T>(val head: () -> T, val tail: () -> FunnyStream<T>) : FunnyStream<T>()
}

fun <T> FunnyStream<T>.getHead(): T = when (this) {
    Nil -> throw NoSuchElementException()
    is Cons -> head()
}

fun <T> FunnyStream<T>.getTail(): FunnyStream<T> = when (this) {
    Nil -> throw NoSuchElementException()
    is Cons -> tail()
}

fun <T> funnyStreamOf(vararg elements: T): FunnyStream<T> = elements.toFunnyStream()

private fun <T> Array<out T>.toFunnyStream(): FunnyStream<T> = when {
    this.isEmpty() -> Nil
    else -> Cons({ this[0] }) { this.copyOfRange(1, this.size).toFunnyStream() }
}

fun FunnyStream<Int>.sum(): Int = when (this) {
    Nil -> 0
    is Cons -> head() + tail().sum()
}

fun FunnyStream<Int>.product(): Int = when (this) {
    Nil -> 1
    is Cons -> head() * tail().product()
}

fun <T> FunnyStream<T>.appendTail(value: T): FunnyStream<T> = when (this) {
    Nil -> Cons({ value }, { Nil })
    is Cons -> Cons({ head() }, { tail().appendTail(value) })
}

fun <T> FunnyStream<T>.filter(p: (T) -> Boolean): FunnyStream<T> = when (this) {
    Nil -> this
    is Cons -> if (p(head())) Cons({ head() }, { tail() }) else tail().filter(p)
}

fun <T, R> FunnyStream<T>.map(f: (T) -> R): FunnyStream<R> = when (this) {
    Nil -> Nil
    is Cons -> Cons({ f(head()) }, { tail().map(f) })
}

fun <T> generateFunnyStream(seed: T, generate: (T) -> T): FunnyStream<T> =
    FunnyStream.Cons({ seed }) { generateFunnyStream(generate(seed), generate) }

tailrec fun <T> FunnyStream<T>.forEach(f: (T) -> Unit): Unit = when (this) {
    Nil -> Unit
    is Cons -> {
        f(head())
        tail().forEach(f)
    }
}

fun <T> FunnyStream<T>.take(n: Int): FunnyStream<T> = when (n) {
    0 -> Nil
    else -> when (this) {
        Nil -> Nil
        is Cons -> Cons({ head() }, { tail().take(n - 1) })
    }
}
