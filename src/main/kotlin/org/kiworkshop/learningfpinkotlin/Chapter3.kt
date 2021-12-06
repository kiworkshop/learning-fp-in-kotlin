package org.kiworkshop.learningfpinkotlin

// Exercise 3-2
fun power(x: Double, n: Int): Double = when {
    n <= 0 -> 1.0
    else -> x * power(x, n - 1)
}

// Exercise 3-3
fun factorial(n: Int): Int = when {
    n <= 0 -> 1
    else -> n * factorial(n - 1)
}

// Exercise 3-4
fun toBinary(n: Int): String = when {
    n <= 0 -> ""
    else -> toBinary(n / 2) + (n % 2).toString()
}

// Exercise 3-5
fun replicate(n: Int, element: Int): List<Int> = when {
    n <= 0 -> listOf()
    else -> listOf(element) + replicate(n - 1, element)
}

// Exercise 3-6
fun elem(num: Int, list: List<Int>): Boolean = when {
    list.isEmpty() -> false
    else -> num == list.first() || elem(num, list.drop(1))
}

// Exercise 3-7
operator fun <T> Sequence<T>.plus(other: () -> Sequence<T>) = object : Sequence<T> {
    private val thisIterator: Iterator<T> by lazy { this@plus.iterator() }
    private val otherIterator: Iterator<T> by lazy { other().iterator() }
    override fun iterator(): Iterator<T> = object : Iterator<T> {
        override fun next(): T =
            if (thisIterator.hasNext())
                thisIterator.next()
            else
                otherIterator.next()

        override fun hasNext() : Boolean = thisIterator.hasNext() || otherIterator.hasNext()
    }
}

fun takeSequence(n: Int, sequence: Sequence<Int>): List<Int> = when {
    n <= 0 -> listOf()
    sequence.none() -> listOf()
    else -> listOf(sequence.iterator().next()) + takeSequence(n - 1, sequence)
}