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

fun <T> FunnyList<T>.getTail(): FunnyList<T> = when (this) {
    FunnyList.Nil -> throw NoSuchElementException()
    is FunnyList.Cons -> tail
}

fun <T> FunnyList<T>.getHead(): T = when (this) {
    FunnyList.Nil -> throw NoSuchElementException()
    is FunnyList.Cons -> this.head
}
