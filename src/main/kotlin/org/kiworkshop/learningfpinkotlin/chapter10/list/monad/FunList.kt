package org.kiworkshop.learningfpinkotlin.chapter10.list.monad

sealed class FunList<out T>
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
