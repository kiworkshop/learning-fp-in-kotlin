package org.kiworkshop.learningfpinkotlin.c5

sealed class FunList<out T> {
    object Nil : FunList<Nothing>()
    data class Cons<out T>(val head: T, val tail: FunList<T>) : FunList<T>()
}

fun <T> FunList<T>.addHead(head: T): FunList<T> = FunList.Cons(head, this)

tailrec fun <T> FunList<T>.reverse(acc: FunList<T> = FunList.Nil): FunList<T> = when (this) {
    FunList.Nil -> acc
    is FunList.Cons -> tail.reverse(acc.addHead(head))
}

tailrec fun <T> FunList<T>.appendTail(value: T, acc: FunList<T> = FunList.Nil): FunList<T> = when (this) {
    FunList.Nil -> FunList.Cons(value, acc).reverse()
    is FunList.Cons -> tail.appendTail(value, acc.addHead(head))
}

fun <T> FunList<T>.getHead(): T = when (this) {
    FunList.Nil -> throw NoSuchElementException()
    is FunList.Cons -> head
}

fun <T> FunList<T>.getTail(): FunList<T> = when (this) {
    FunList.Nil -> throw NoSuchElementException()
    is FunList.Cons -> tail
}

tailrec fun <T> FunList<T>.filter(acc: FunList<T> = FunList.Nil, predicate: (T) -> Boolean): FunList<T> = when (this) {
    FunList.Nil -> acc.reverse()
    is FunList.Cons -> when {
        predicate(head) -> tail.filter(acc.addHead(head), predicate)
        else -> tail.filter(acc, predicate)
    }
}

tailrec fun <T> FunList<T>.drop(n: Int, acc: FunList<T> = FunList.Nil): FunList<T> = when (this) {
    FunList.Nil -> acc.reverse()
    is FunList.Cons -> when {
        n > 0 -> tail.drop(n - 1, acc)
        else -> tail.drop(0, acc.addHead(head))
    }
}

tailrec fun <T> FunList<T>.dropWhile(predicate: (T) -> Boolean): FunList<T> = when (this) {
    FunList.Nil -> FunList.Nil
    is FunList.Cons -> when {
        !predicate(head) -> this
        else -> tail.dropWhile(predicate)
    }
}

tailrec fun <T> FunList<T>.take(n: Int, acc: FunList<T> = FunList.Nil): FunList<T> = when (this) {
    FunList.Nil -> acc.reverse()
    is FunList.Cons -> when {
        n > 0 -> tail.take(n - 1, acc.addHead(head))
        else -> acc.reverse()
    }
}

tailrec fun <T> FunList<T>.takeWhile(
    acc: FunList<T> = FunList.Nil,
    predicate: (T) -> Boolean,
): FunList<T> = when (this) {
    FunList.Nil -> acc.reverse()
    is FunList.Cons -> when {
        !predicate(head) -> acc.reverse()
        else -> tail.takeWhile(acc.addHead(head), predicate)
    }
}

tailrec fun <T, R> FunList<T>.map(acc: FunList<R> = FunList.Nil, transform: (T) -> R): FunList<R> = when (this) {
    FunList.Nil -> acc.reverse()
    is FunList.Cons -> tail.map(acc.addHead(transform(head)), transform)
}

fun <T> funListOf(vararg elements: T) = elements.toFunList()

tailrec fun <T> Array<out T>.toFunList(acc: FunList<T> = FunList.Nil): FunList<T> = when {
    isEmpty() -> acc.reverse()
    else -> copyOfRange(1, size).toFunList(acc.addHead(this[0]))
}

fun <T> Collection<T>.toFunList(): FunList<T> {
    val iterator = iterator()
    tailrec fun funList(acc: FunList<T> = FunList.Nil): FunList<T> {
        if (!iterator.hasNext()) {
            return acc.reverse()
        }
        return funList(acc.addHead(iterator.next()))
    }
    return funList()
}

tailrec fun <T, R> FunList<T>.indexedMap(
    index: Int = 0,
    acc: FunList<R> = FunList.Nil,
    transform: (Int, T) -> R,
): FunList<R> = when (this) {
    FunList.Nil -> acc.reverse()
    is FunList.Cons -> tail.indexedMap(index + 1, acc.addHead(transform(index, head)), transform)
}

tailrec fun <T, R> FunList<T>.foldLeft(acc: R, operation: (R, T) -> R): R = when (this) {
    FunList.Nil -> acc
    is FunList.Cons -> tail.foldLeft(operation(acc, head), operation)
}

fun <T, R> FunList<T>.foldRight(acc: R, operation: (T, R) -> R): R = when (this) {
    FunList.Nil -> acc
    is FunList.Cons -> operation(head, tail.foldRight(acc, operation))
}

tailrec fun <T, R> FunList<T>.zip(
    other: FunList<R>,
    acc: FunList<Pair<T, R>> = FunList.Nil,
): FunList<Pair<T, R>> = when {
    this == FunList.Nil || other == FunList.Nil -> acc.reverse()
    else -> getTail().zip(other.getTail(), acc.addHead(getHead() to other.getHead()))
}

fun <T, R> FunList<T>.associate(transform: (T) -> Pair<T, R>): Map<T, R> = foldLeft(emptyMap()) { acc, element ->
    acc.plus(transform(element))
}

fun <T, K> FunList<T>.groupBy(keySelector: (T) -> K): Map<K, FunList<T>> =
    foldRight(emptyMap()) { element: T, acc: Map<K, FunList<T>> ->
        val key = keySelector(element)
        val funList = acc[key] ?: FunList.Nil
        acc.plus(key to funList.addHead(element))
    }

tailrec fun <T> FunList<T>.toString(acc: String): String = when (this) {
    FunList.Nil -> "[${acc.drop(2)}]"
    is FunList.Cons -> tail.toString("$acc, ${head.toString()}")
}

fun <T> FunList<T>.concat(value: FunList<T>): FunList<T> {
    tailrec fun concat(list: FunList<T>, other: FunList<T>): FunList<T> = when (list) {
        FunList.Nil -> other
        is FunList.Cons -> concat(list.tail, other.addHead(list.head))
    }
    return concat(reverse(), value)
}
