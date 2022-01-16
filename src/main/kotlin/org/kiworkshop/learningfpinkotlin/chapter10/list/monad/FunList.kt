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

fun <T> FunList.Companion.pure(value: T): FunList<T> = Cons(value, Nil)

infix fun <T, R> FunList<(T) -> R>.apply(f: FunList<T>): FunList<R> = when (this) {
    Nil -> Nil
    is Cons -> f.fmap(head) mappend (tail apply f)
}

infix fun <T, R> FunList<T>._apply(f: FunList<(T) -> R>): FunList<R> = when (this) {
    Nil -> Nil
    is Cons -> f.fmap { it(head) } mappend (tail _apply f)
}

fun main() {
    val list1 = funListOf(1, 2, 3)
    val list2 = funListOf(5, 10, 15, 20)
    val list3 = funListOf<(Int) -> Int>({ it * 2 }, { it + 1 }, { it - 10 })

    // 책이랑 결과가 다르다
    println(list1 _apply list3)
    println(list2 _apply list3)
}
