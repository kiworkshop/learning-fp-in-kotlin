package org.kiworkshop.learningfpinkotlin

import org.kiworkshop.learningfpinkotlin.MyFunStream.Cons
import org.kiworkshop.learningfpinkotlin.MyFunStream.Nil

sealed class MyFunStream<out T> {
    object Nil : MyFunStream<Nothing>()
    data class Cons<out T>(val head: () -> T, val tail: () -> MyFunStream<T>) : MyFunStream<T>()
}

fun <T> myFunStreamOf(vararg elements: T) = elements.toMyFunStream()
fun <T> emptyMyFunStream() = Nil

tailrec fun <T> Array<out T>.toMyFunStream(
    index: Int = this.size - 1,
    acc: MyFunStream<T> = Nil
): MyFunStream<T> = when {
    index < 0 -> acc
    else -> this.toMyFunStream(index - 1, acc.addHead(this[index]))
}

fun <T> MyFunStream<T>.getHead(): T = when (this) {
    Nil -> throw NoSuchElementException()
    is Cons -> head()
}

fun <T> MyFunStream<T>.getTail(): MyFunStream<T> = when (this) {
    Nil -> throw NoSuchElementException()
    is Cons -> tail()
}

fun <T> MyFunStream<T>.addHead(value: T): MyFunStream<T> = Cons({ value }, { this })

tailrec fun MyFunStream<Int>.sum(acc: Int = 0): Int = when (this) {
    Nil -> acc
    is Cons -> getTail().sum(acc + getHead())
}
