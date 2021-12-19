package org.kiworkshop.learningfpinkotlin.c4

fun getMax(l: List<Int>): Int = maxOrNull(l, null) ?: throw IllegalArgumentException("list has no max value")

fun power(base: Int, power: Int): Int = power(base, power, 1)

private tailrec fun maxOrNull(l: List<Int>, max: Int?): Int? {
    if (l.isEmpty()) {
        return max
    }
    val target = l.first()
    val newMax = when {
        max == null -> target
        target > max -> target
        else -> max
    }
    return maxOrNull(l.drop(1), newMax)
}

private tailrec fun power(base: Int, power: Int, acc: Int): Int {
    if (power == 0) {
        return acc
    }
    return power(base, power - 1, acc * base)
}
