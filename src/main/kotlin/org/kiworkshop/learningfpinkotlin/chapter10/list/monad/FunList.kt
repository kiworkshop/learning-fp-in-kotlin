package org.kiworkshop.learningfpinkotlin.chapter10.list.monad

sealed class FunList<out T> {
    companion object
}

object Nil : FunList<kotlin.Nothing>() {
    override fun toString(): String = "[]"
}

data class Cons<out T>(val head: T, val tail: FunList<T>) : FunList<T>() {
    override fun toString(): String = "[${foldLeft("") { acc, x -> "$acc, $x" }.drop(2)}]"
}

fun <T> funListOf(vararg elements: T): FunList<T> = elements.toFunList()

private fun <T> Array<out T>.toFunList(): FunList<T> = when {
    this.isEmpty() -> Nil
    else -> Cons(this[0], this.copyOfRange(1, this.size).toFunList())
}

tailrec fun <T, R> FunList<T>.foldLeft(acc: R, f: (R, T) -> R): R = when (this) {
    Nil -> acc
    is Cons -> tail.foldLeft(f(acc, head), f)
}

fun <T> FunList<T>.mempty() = Nil

infix fun <T> FunList<T>.mappend(other: FunList<T>): FunList<T> = when (this) {
    Nil -> other
    is Cons -> Cons(head, tail mappend other)
}

infix fun <T, R> FunList<T>.fmap(f: (T) -> R): FunList<R> = when (this) {
    Nil -> Nil
    is Cons -> Cons(f(head), tail fmap f)
}

/*
infix fun <T, R> FunList<T>.fmap(f: (T) -> R): FunList<R> = flatMap { Cons(f(it), Nil) }
*/

fun <T> FunList.Companion.pure(value: T): FunList<T> = Cons(value, Nil)

infix fun <T, R> FunList<(T) -> R>.apply(f: FunList<T>): FunList<R> = when (this) {
    Nil -> Nil
    is Cons -> f.fmap(head) mappend (tail apply f)
}

infix fun <T, R> FunList<T>._apply(f: FunList<(T) -> R>): FunList<R> = when (this) {
    Nil -> Nil
    is Cons -> f.fmap { it(head) } mappend (tail _apply f)
}

infix fun <T, R> FunList<T>.flatMap(f: (T) -> FunList<R>): FunList<R> = when (this) {
    Nil -> Nil
    is Cons -> f(head) mappend (tail flatMap f)
}

// infix fun <T, R> FunList<T>.flatMap(f: (T) -> FunList<R>) : FunList<R> = fmap(f).flatten()

fun <T, R> FunList<T>.foldRight(acc: R, f: (T, R) -> R): R = when (this) {
    Nil -> acc
    is Cons -> f(head, tail.foldRight(acc, f))
}

fun <T> FunList<FunList<T>>.flatten(): FunList<T> = foldRight(mempty()) { t, r: FunList<T> ->
    t mappend r
}

tailrec fun <T> FunList<T>.contains(element: T): Boolean = when (this) {
    Nil -> false
    is Cons -> if (element == head) true else tail.contains(element)
}

fun <T> FunList<T>.distinct(): FunList<T> =
    foldLeft(Nil as FunList<T>) { acc, x -> if (acc.contains(x)) acc else Cons(x, acc) }

tailrec fun <T> FunList<T>.reverse(acc: FunList<T> = Nil): FunList<T> = when (this) {
    Nil -> acc
    is Cons -> tail.reverse(Cons(head, acc))
}

fun main() {
    val list1 = funListOf(1, 2, 3)
    val list2 = funListOf(5, 10, 15, 20)
    val list3 = funListOf<(Int) -> Int>({ it * 2 }, { it + 1 }, { it - 10 })

    // 책이랑 결과가 다르다
    println(list1 _apply list3)
    println(list2 _apply list3)
}
