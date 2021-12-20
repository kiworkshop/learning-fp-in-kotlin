package org.kiworkshop.learningfpinkotlin

import org.kiworkshop.learningfpinkotlin.FunList.*

sealed class FunList<out T> {
    object Nil : FunList<Nothing>()
    data class Cons<out T>(val head: T, val tail: FunList<T>) : FunList<T>()
}

fun <T> FunList<T>.addHead(head: T): FunList<T> = Cons(head, this)
//fun <T> FunList<T>.appendTail(value: T): FunList<T> = when (this) {
//    Nil -> Cons(value, Nil)
//    is Cons -> Cons(head, tail.appendTail(value))
//}

tailrec fun <T> FunList<T>.reverse(acc: FunList<T> = Nil): FunList<T> = when (this) {
    Nil -> acc
    is Cons -> tail.reverse(acc.addHead(head))
}

tailrec fun <T> FunList<T>.appendTail(value: T, acc: FunList<T> = Nil): FunList<T> = when (this) {
    Nil -> Cons(value, acc).reverse()
    is Cons -> tail.appendTail(value, acc.addHead(head))
}

fun <T> FunList<T>.getTail(): FunList<T> = when (this) {
    Nil -> throw NoSuchElementException()
    is Cons -> tail
}

fun <T> FunList<T>.getHead(): T = when (this) {
    Nil -> throw NoSuchElementException()
    is Cons -> head
}

tailrec fun <T> FunList<T>.filter(acc: FunList<T> = Nil, p: (T) -> Boolean): FunList<T> = when (this) {
    Nil -> acc.reverse()
    is Cons -> if (p(head)) {
        tail.filter(acc.addHead(head), p)
    } else {
        tail.filter(acc, p)
    }
}

tailrec fun <T> FunList<T>.drop(n: Int): FunList<T> = when (this) {
    Nil -> this
    is Cons -> when {
        n < 0 -> throw IllegalArgumentException()
        n == 0 -> this
        else -> tail.drop(n - 1)
    }
}

tailrec fun <T> FunList<T>.dropWhile(p: (T) -> Boolean): FunList<T> = when (this) {
    Nil -> this
    is Cons -> when {
        !p(head) -> this
        else -> tail.dropWhile(p)
    }
}

tailrec fun <T> FunList<T>.take(n: Int, acc: FunList<T> = Nil): FunList<T> = when (this) {
    Nil -> acc.reverse()
    is Cons -> when (n) {
        0 -> acc.reverse()
        else -> tail.take(n - 1, acc.addHead(head))
    }
}

tailrec fun <T> FunList<T>.takeWhile(acc: FunList<T> = Nil, p: (T) -> Boolean): FunList<T> = when (this) {
    Nil -> acc.reverse()
    is Cons -> when {
        !p(head) -> acc.reverse()
        else -> tail.takeWhile(acc.addHead(head), p)
    }
}

tailrec fun <T, R> FunList<T>.map(acc: FunList<R> = Nil, f: (T) -> R): FunList<R> = when (this) {
    Nil -> acc.reverse()
    is Cons -> tail.map(acc.addHead(f(head)), f)
}

fun <T> funListOf(vararg elements: T): FunList<T> = elements.toFunList()

private fun <T> Array<out T>.toFunList(): FunList<T> = when {
    this.isEmpty() -> Nil
    else -> Cons(this[0], this.copyOfRange(1, this.size).toFunList())
}

tailrec fun <T, R> FunList<T>.indexedMap(index: Int = 0, acc: FunList<R> = Nil, f: (Int, T) -> R): FunList<R> =
    when (this) {
        Nil -> acc.reverse()
        is Cons -> tail.indexedMap(index + 1, acc.addHead(f(index, head)), f)
    }

tailrec fun <T, R> FunList<T>.foldLeft(acc: R, f: (R, T) -> R): R = when (this) {
    Nil -> acc
    is Cons -> tail.foldLeft(f(acc, head), f)
}

fun FunList<Int>.maximumByFoldLeft(acc: Int = this.getHead()): Int = this.foldLeft(acc) { acc: Int, x ->
    if (acc < x) x else acc
}

fun <T> FunList<T>.filterByFoldLeft(acc: FunList<T> = Nil, p: (T) -> Boolean): FunList<T> =
    this.foldLeft(acc) { acc: FunList<T>, x ->
        if (p(x)) acc.appendTail(x) else acc
    }

fun <T, R> FunList<T>.foldRight(acc: R, f: (T, R) -> R): R = when (this) {
    Nil -> acc
    is Cons -> f(head, tail.foldRight(acc, f))
}

fun <T> FunList<T>.reverseByFoldRight(): FunList<T> = this.foldRight(Nil) { x, acc: FunList<T> ->
    acc.appendTail(x)
}

fun <T> FunList<T>.filterByFoldRight(p: (T) -> Boolean): FunList<T> = this.foldRight(Nil) { x, acc: FunList<T> ->
    if (p(x)) acc.addHead(x) else acc
}

tailrec fun <T, R> FunList<T>.zip(other: FunList<R>, acc: FunList<Pair<T, R>> = Nil): FunList<Pair<T, R>> = when {
    this === Nil || other === Nil -> acc.reverse()
    else -> this.getTail().zip(other.getTail(), acc.addHead(Pair(this.getHead(), other.getHead())))
}

tailrec fun <T1, T2, R> FunList<T1>.zipWith(f: (T1, T2) -> R, list: FunList<T2>, acc: FunList<R> = Nil): FunList<R> = when {
    this === Nil || list === Nil -> acc.reverse()
    else -> getTail().zipWith(f, list.getTail(), acc.addHead(f(getHead(), list.getHead())))
}

fun <T, R> FunList<T>.associate(f: (T) -> Pair<T, R>): Map<T, R> = when {
    this === Nil -> mapOf()
    else -> mapOf(f(getHead())) + getTail().associate(f)
}

fun <T, K> FunList<T>.groupBy(f: (T) -> K): Map<K, FunList<T>> = when {
    this === Nil -> mapOf()
    else -> mapOf(f(getHead()) to funListOf(getHead())) + getTail().groupBy(f)
}