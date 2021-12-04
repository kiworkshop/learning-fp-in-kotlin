package org.kiworkshop.learningfpinkotlin.c3

object ListReplica {

    fun replicate(n: Int, element: Int): List<Int> {
        if (n == 0) {
            return emptyList()
        }
        return replicate(n - 1, element) + element
    }
}
