package org.kiworkshop.learningfpinkotlin.c3

class ImprovedFactorial(private val n: Int) {

    private val cache: MutableMap<Int, Int> = mutableMapOf()

    fun get(): Int {
        return factorial(n)
    }

    private fun factorial(n: Int): Int {
        cache[n]?.let { return it }
        if (n == 0) return 1

        return n * factorial(n - 1)
    }
}
