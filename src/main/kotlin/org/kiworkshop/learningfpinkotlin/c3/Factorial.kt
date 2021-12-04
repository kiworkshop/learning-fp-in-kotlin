package org.kiworkshop.learningfpinkotlin.c3

class Factorial(private val n: Int) {

    fun get(): Int {
        return factorial(n)
    }

    private fun factorial(n: Int): Int {
        if (n == 0) return 1

        return n * factorial(n - 1)
    }
}
