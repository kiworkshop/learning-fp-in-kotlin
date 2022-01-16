package org.kiworkshop.learningfpinkotlin.chapter10.list.monad

sealed class FunStream<out T> {
    object Nil : FunStream<kotlin.Nothing>() {
        override fun toString(): String = "[]"
    }

    data class Cons<out T>(val head: () -> T, val tail: () -> FunStream<T>) : FunStream<T>() {
        override fun toString(): String = "[${foldLeft("") { acc, x -> "$acc, $x" }.drop(2)}]"

        override fun equals(other: Any?): Boolean =
            if (other is Cons<*>) {
                if (head() == other.head()) tail() == other.tail()
                else false
            } else false

        override fun hashCode(): Int {
            var result = head.hashCode()
            result = 31 * result + tail.hashCode()
            return result
        }
    }
}

fun <T> funStreamOf(vararg elements: T): FunStream<T> = elements.toFunStream()
fun <T> emptyFunStream() = funStreamOf<Nothing>()

private fun <T> Array<out T>.toFunStream(): FunStream<T> = when {
    this.isEmpty() -> FunStream.Nil
    else -> FunStream.Cons({ this[0] }, { this.copyOfRange(1, this.size).toFunStream() })
}

tailrec fun <T, R> FunStream<T>.foldLeft(acc: R, f: (R, T) -> R): R = when (this) {
    FunStream.Nil -> acc
    is FunStream.Cons -> tail().foldLeft(f(acc, head()), f)
}

fun <T> FunStream<T>.printFunStream() = toString()

fun <T> FunStream<T>.mempty() = FunStream.Nil

/*
infix fun <T> FunStream<T>.mappend(other: FunStream<T>): FunStream<T> = when(this) {
    FunStream.Nil -> other
    is FunStream.Cons -> FunStream.Cons(head) { tail() mappend other }
}
*/

infix fun <T> FunStream<T>.mappend(other: FunStream<T>): FunStream<T> = when {
    this == FunStream.Nil -> other
    other == FunStream.Nil -> this
    this is FunStream.Cons -> FunStream.Cons(head) { tail() mappend other }
    else -> FunStream.Nil
}
