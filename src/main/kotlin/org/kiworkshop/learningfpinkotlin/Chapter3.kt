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

// Exercise 3-8
fun quicksort(list: List<Int>, low: Int, high: Int): List<Int> {
    if (low == high) {
        return listOf(list[low])
    }

    if (low > high || low < 0) {
        return listOf()
    }

    val ml = list.toMutableList()
    val p = partition(ml, low, high)
    return quicksort(ml, low, p - 1) + listOf(ml[p]) + quicksort(ml, p + 1, high)
}

fun partition(list: MutableList<Int>, low: Int, high: Int): Int {
    val pivot = list[high]
    var i = low - 1
    for (j in low..high) {
        if (list[j] < pivot) {
            i += 1
            val temp = list[j]
            list[j] = list[i]
            list[i] = temp
        }
    }
    i += 1
    val temp = list[i]
    list[i] = list[high]
    list[high] = temp

    return i
}
