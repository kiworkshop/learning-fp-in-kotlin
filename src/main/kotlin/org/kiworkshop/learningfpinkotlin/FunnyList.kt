package org.kiworkshop.learningfpinkotlin

import kotlin.math.max

sealed class FunnyList<out T> {
    object Nil : FunnyList<Nothing>()
    data class Cons<out T>(val head: T, val tail: FunnyList<T>) : FunnyList<T>()
}

fun <T> FunnyList<T>.addHead(head: T): FunnyList<T> = FunnyList.Cons(head, this)
fun <T> FunnyList<T>.appendTail(value: T): FunnyList<T> = when (this) {
    FunnyList.Nil -> FunnyList.Cons(value, FunnyList.Nil)
    is FunnyList.Cons -> FunnyList.Cons(head, tail.appendTail(value))
}

tailrec fun <T> FunnyList<T>.appendTail(value: T, acc: FunnyList<T> = FunnyList.Nil):
    FunnyList<T> = when (this) {
    FunnyList.Nil -> FunnyList.Cons(value, acc).reverse()
    is FunnyList.Cons -> tail.appendTail(value, acc.addHead(head))
}

tailrec fun <T> FunnyList<T>.reverse(acc: FunnyList<T> = FunnyList.Nil): FunnyList<T> = when (this) {
    FunnyList.Nil -> acc
    is FunnyList.Cons -> tail.reverse(acc.addHead(head))
}

tailrec fun <T> FunnyList<T>.filter(acc: FunnyList<T> = FunnyList.Nil, p: (T) -> Boolean): FunnyList<T> = when (this) {
    FunnyList.Nil -> acc.reverse()
    is FunnyList.Cons -> if (p(head)) {
        tail.filter(acc.addHead(head), p)
    } else {
        tail.filter(acc, p)
    }
}

fun <T> FunnyList<T>.getTail(): FunnyList<T> = when (this) {
    FunnyList.Nil -> throw NoSuchElementException()
    is FunnyList.Cons -> tail
}

fun <T> FunnyList<T>.getHead(): T = when (this) {
    FunnyList.Nil -> throw NoSuchElementException()
    is FunnyList.Cons -> this.head
}

tailrec fun <T> FunnyList<T>.drop(n: Int): FunnyList<T> = when (n) {
    0 -> this
    else -> when (this) {
        FunnyList.Nil -> throw NoSuchElementException()
        is FunnyList.Cons -> tail.drop(n - 1)
    }
}

tailrec fun <T> FunnyList<T>.dropWhile(p: (T) -> Boolean): FunnyList<T> = when (this) {
    FunnyList.Nil -> this
    is FunnyList.Cons -> if (p(head)) {
        this
    } else {
        tail.dropWhile(p)
    }
}

tailrec fun <T> FunnyList<T>.take(n: Int, acc: FunnyList<T> = FunnyList.Nil): FunnyList<T> = when (n) {
    0 -> acc.reverse()
    else -> when (this) {
        FunnyList.Nil -> throw NoSuchElementException()
        is FunnyList.Cons -> tail.take(n - 1, acc.addHead(head))
    }
}

tailrec fun <T> FunnyList<T>.takeWhile(acc: FunnyList<T> = FunnyList.Nil, p: (T) -> Boolean): FunnyList<T> =
    when (this) {
        FunnyList.Nil -> if (acc == FunnyList.Nil) {
            this
        } else {
            acc.reverse()
        }
        is FunnyList.Cons -> if (p(head)) {
            tail.takeWhile(acc.addHead(head), p)
        } else {
            tail.takeWhile(acc, p)
        }
    }

tailrec fun <T, R> FunnyList<T>.map(acc: FunnyList<R> = FunnyList.Nil, f: (T) -> R): FunnyList<R> = when (this) {
    FunnyList.Nil -> acc.reverse()
    is FunnyList.Cons -> tail.map(acc.addHead(f(head)), f)
}

tailrec fun <T, R> FunnyList<T>.indexedMap(
    index: Int = 0,
    acc: FunnyList<R> = FunnyList.Nil,
    f: (Int, T) -> R
): FunnyList<R> = when (this) {
    FunnyList.Nil -> acc.reverse()
    is FunnyList.Cons -> tail.indexedMap(index = index + 1, acc.addHead(f(index, head)), f)
}

tailrec fun <T, R> FunnyList<T>.foldLeft(acc: R, f: (R, T) -> R): R = when (this) {
    FunnyList.Nil -> acc
    is FunnyList.Cons -> tail.foldLeft(f(acc, head), f)
}

fun sumByFoldLeft(list: FunnyList<Int>): Int = list.foldLeft(0) { acc, x -> acc + x }
fun FunnyList<Int>.sumByFoldLeft(list: FunnyList<Int>) = list.foldLeft(0) { acc, x -> acc + x }
fun FunnyList<Char>.toUpper(list: FunnyList<Char>) = list.foldLeft(FunnyList.Nil) { acc: FunnyList<Char>, char: Char ->
    acc.appendTail(char.uppercaseChar())
}

fun <T, R> FunnyList<T>.mapByFoldLeft(f: (T) -> R): FunnyList<R> =
    this.foldLeft(FunnyList.Nil) { acc: FunnyList<R>, x ->
        acc.appendTail(f(x))
    }

fun FunnyList<Int>.maximumByFoldLeft(): Int = this.foldLeft(0) { acc, x -> max(acc, x) }
fun <T> FunnyList<T>.filterByFoldLeft(p: (T) -> Boolean): FunnyList<T> =
    this.foldLeft(FunnyList.Nil) { acc: FunnyList<T>, x ->
        if (p(x)) acc.appendTail(x) else acc
    }

fun <T, R> FunnyList<T>.foldRight(acc: R, f: (T, R) -> R): R = when (this) {
    FunnyList.Nil -> acc
    is FunnyList.Cons -> {
        f(head, tail.foldRight(acc, f))
    }
}

fun <T> FunnyList<T>.reverseByFoldRight(): FunnyList<T> =
    this.foldRight(FunnyList.Nil) { x, acc: FunnyList<T> -> acc.addHead(x) }

fun <T> FunnyList<T>.filterByFoldRight(p: (T) -> Boolean): FunnyList<T> =
    this.foldRight(FunnyList.Nil) { x, acc: FunnyList<T> ->
        if (p(x)) acc.appendTail(x) else acc
    }

fun <T> funnyListOf(vararg elements: T): FunnyList<T> = elements.toFunnyList()
private fun <T> Array<out T>.toFunnyList(): FunnyList<T> = when {
    this.isEmpty() -> FunnyList.Nil
    else -> FunnyList.Cons(this[0], this.copyOfRange(1, this.size).toFunnyList())
}
