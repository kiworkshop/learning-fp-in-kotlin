package org.kiworkshop.learningfpinkotlin

// Code 5-38
sealed class FunStream<out T> {
    object Nil : FunStream<kotlin.Nothing>()
    data class Cons<out T>(val head: () -> T, val tail: () -> FunStream<T>) : FunStream<T>() {
        // Code 5-40
        override fun equals(other: Any?): Boolean =
            if (other is Cons<*>) {
                if (head() == other.head()) {
                    tail() == other.tail()
                } else {
                    false
                }
            } else {
                false
            }

        override fun hashCode(): Int {
            var result = head.hashCode()
            result = 31 * result + tail.hashCode()
            return result
        }
    }
}

// Code 5-39
fun <T> FunStream<T>.getHead(): T = when (this) {
    FunStream.Nil -> throw NoSuchElementException()
    is FunStream.Cons -> head()
}

// Code 5-39
fun <T> FunStream<T>.getTail(): FunStream<T> = when (this) {
    FunStream.Nil -> throw NoSuchElementException()
    is FunStream.Cons -> tail()
}

fun <T> funStreamOf(vararg elements: T): FunStream<T> = elements.toFunStream()

private fun <T> Array<out T>.toFunStream(): FunStream<T> = when {
    this.isEmpty() -> FunStream.Nil
    else -> FunStream.Cons({ this[0] }, { this.copyOfRange(1, this.size).toFunStream() })
}

tailrec fun <T, R> FunStream<T>.foldLeft(acc: R, f: (R, T) -> R): R = when (this) {
    FunStream.Nil -> acc
    is FunStream.Cons -> tail().foldLeft(f(acc, head()), f)
}

// Exercise 5-17
fun FunStream<Int>.sum(): Int = foldLeft(0) { acc, x -> acc + x }

// Exercise 5-18
fun FunStream<Int>.product(): Int = foldLeft(1) { acc, x -> acc * x }

// Exercise 5-19
fun <T> FunStream<T>.appendTail(value: T): FunStream<T> = when (this) {
    FunStream.Nil -> FunStream.Cons({ value }, { FunStream.Nil })
    is FunStream.Cons -> FunStream.Cons(head, { tail().appendTail(value) })
}

// Exercise 5-20
fun <T> FunStream<T>.filter(p: (T) -> Boolean): FunStream<T> = when (this) {
    FunStream.Nil -> this
    is FunStream.Cons -> if (p(head())) FunStream.Cons(head, { tail().filter(p) }) else tail().filter(p)
}

// Exercise 5-21
fun <T, R> FunStream<T>.map(f: (T) -> R): FunStream<R> = when (this) {
    FunStream.Nil -> FunStream.Nil
    is FunStream.Cons -> FunStream.Cons({ f(head()) }, { tail().map(f) })
}

// Code 5-42
fun <T> generateFunStream(seed: T, generate: (T) -> T): FunStream<T> =
    FunStream.Cons({ seed }, { generateFunStream(generate(seed), generate) })

// Code 5-44
tailrec fun <T> FunStream<T>.forEach(f: (T) -> Unit): Unit = when (this) {
    FunStream.Nil -> Unit
    is FunStream.Cons -> {
        f(head())
        tail().forEach(f)
    }
}

// Exercise 5-22
fun <T> FunStream<T>.take(n: Int): FunStream<T> = when {
    this === FunStream.Nil -> FunStream.Nil
    n <= 0 -> FunStream.Nil
    else -> FunStream.Cons({ this.getHead() }, { this.getTail().take(n - 1) })
}
