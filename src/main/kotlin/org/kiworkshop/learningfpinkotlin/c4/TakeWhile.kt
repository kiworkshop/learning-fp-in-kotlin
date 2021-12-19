package org.kiworkshop.learningfpinkotlin.c4

fun <T> List<T>.takeWhile(condition: (T) -> Boolean): List<T> = takeWhile(this.iterator(), condition)

fun <T> Sequence<T>.takeWhile(condition: (T) -> Boolean): List<T> = takeWhile(this.iterator(), condition)

tailrec fun <T> takeWhile(iterator: Iterator<T>, condition: (T) -> Boolean, acc: List<T> = emptyList()): List<T> {
    if (!iterator.hasNext()) {
        return acc
    }
    val target = iterator.next()
    if (!condition(target)) {
        return acc
    }
    return takeWhile(iterator, condition, acc + target)
}
