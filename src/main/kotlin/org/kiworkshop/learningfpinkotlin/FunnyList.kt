package org.kiworkshop.learningfpinkotlin

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
