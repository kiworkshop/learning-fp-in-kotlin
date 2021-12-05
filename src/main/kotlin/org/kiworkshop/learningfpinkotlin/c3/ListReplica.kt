package org.kiworkshop.learningfpinkotlin.c3

object ListReplica {

    fun replicate(n: Int, element: Int): List<Int> {
        if (n == 0) {
            return emptyList()
        }
        return replicate(n - 1, element) + element
    }

    fun replicateImproved(n: Int, element: Int): List<Int> {
        return replicate(n, element, emptyList())
    }

    tailrec fun replicate(n: Int, element: Int, acc: List<Int> = emptyList()): List<Int> {
        if (n == 0) {
            return acc
        }
        return replicate(n - 1, element, acc + element)
    }
}
