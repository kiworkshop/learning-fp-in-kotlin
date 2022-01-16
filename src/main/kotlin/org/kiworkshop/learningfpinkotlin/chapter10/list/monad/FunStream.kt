package org.kiworkshop.learningfpinkotlin.chapter10.list.monad

sealed class FunStream<out T> {
    object Nil : FunStream<kotlin.Nothing>() {
        override fun toString(): String = "[]"
    }

    data class Cons<out T>(val head: () -> T, val tail: () -> FunStream<T>) : FunStream<T>() {
        override fun toString(): String = "[${foldLeft("") { acc, x -> "$acc, $x" }.drop(2)}]"
    }
}

fun <T> funStreamOf(vararg elements: T): FunList<T> = elements.toFunStream()
fun <T> emptyFunStream() = funStreamOf<Nothing>()

private fun <T> Array<out T>.toFunStream(): FunList<T> = when {
    this.isEmpty() -> Nil
    else -> Cons(this[0], this.copyOfRange(1, this.size).toFunStream())
}

tailrec fun <T, R> FunStream<T>.foldLeft(acc: R, f: (R, T) -> R): R = when (this) {
    FunStream.Nil -> acc
    is FunStream.Cons -> tail().foldLeft(f(acc, head()), f)
}
