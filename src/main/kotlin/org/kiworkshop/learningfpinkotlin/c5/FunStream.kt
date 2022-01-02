package org.kiworkshop.learningfpinkotlin.c5

sealed class FunStream<out T> {
    object Nil : FunStream<Nothing>()
    data class Cons<out T>(val head: () -> T, val tail: () -> FunStream<T>) : FunStream<T>() {
        override fun equals(other: Any?): Boolean {
            if (other is Cons<*>) {
                if (head() == other.head()) {
                    return tail() == other.tail()
                }
            }
            return false
        }

        override fun hashCode(): Int {
            return head.hashCode() * 31 + tail.hashCode()
        }
    }
}

fun <T> funStreamOf(vararg elements: T) = elements.toFunStream()

fun <T> Array<out T>.toFunStream(): FunStream<T> = when {
    isEmpty() -> FunStream.Nil
    else -> FunStream.Cons({ this[0] }, { copyOfRange(1, size).toFunStream() })
}

fun <T> Collection<T>.toFunStream(): FunStream<T> {
    val iterator = iterator()
    tailrec fun funStream(acc: FunStream<T> = FunStream.Nil): FunStream<T> {
        if (!iterator.hasNext()) {
            return acc.reverse()
        }
        return funStream(acc.addHead(iterator.next()))
    }
    return funStream()
}

fun <T> FunStream<T>.getHead(): T = when (this) {
    FunStream.Nil -> throw NoSuchElementException()
    is FunStream.Cons -> head()
}

fun <T> FunStream<T>.getTail(): FunStream<T> = when (this) {
    FunStream.Nil -> throw NoSuchElementException()
    is FunStream.Cons -> tail()
}

fun <T> FunStream<T>.addHead(value: T): FunStream<T> = FunStream.Cons({ value }, { this })

tailrec fun <T> FunStream<T>.reverse(acc: FunStream<T> = FunStream.Nil): FunStream<T> = when (this) {
    FunStream.Nil -> acc
    is FunStream.Cons -> tail().reverse(acc.addHead(head()))
}

tailrec fun <T> FunStream<T>.appendTail(value: T, acc: FunStream<T> = FunStream.Nil): FunStream<T> = when (this) {
    FunStream.Nil -> FunStream.Cons({ value }, { acc }).reverse()
    is FunStream.Cons -> tail().appendTail(value, acc.addHead(head()))
}

tailrec fun <T, R> FunStream<T>.foldLeft(acc: R, operation: (R, T) -> R): R = when (this) {
    FunStream.Nil -> acc
    is FunStream.Cons -> tail().foldLeft(operation(acc, head()), operation)
}

fun FunStream<Int>.sum(): Int = foldLeft(0) { acc, element -> acc + element }

fun FunStream<Int>.product(): Int = foldLeft(1) { acc, element -> acc * element }

@Suppress("NON_TAIL_RECURSIVE_CALL")
tailrec fun <T> FunStream<T>.filter(predicate: (T) -> Boolean): FunStream<T> = when (this) {
    FunStream.Nil -> this
    is FunStream.Cons -> when {
        predicate(head()) -> FunStream.Cons(head) { tail().filter(predicate) }
        else -> tail().filter(predicate)
    }
}

fun <T, R> FunStream<T>.map(transform: (T) -> R): FunStream<R> = when (this) {
    FunStream.Nil -> FunStream.Nil
    is FunStream.Cons -> FunStream.Cons({ transform(head()) }) { tail().map(transform) }
}

fun <T> FunStream<T>.take(n: Int): FunStream<T> {
    if (n < 0) throw IllegalArgumentException()
    if (n == 0) return FunStream.Nil
    return when (this) {
        FunStream.Nil -> FunStream.Nil
        is FunStream.Cons -> FunStream.Cons(head) { tail().take(n - 1) }
    }
}

fun <T> generateFunStream(seed: T, generate: (T) -> T): FunStream<T> =
    FunStream.Cons({ seed }, { generateFunStream(generate(seed), generate) })
