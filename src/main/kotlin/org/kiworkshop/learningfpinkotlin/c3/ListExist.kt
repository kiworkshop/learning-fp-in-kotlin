package org.kiworkshop.learningfpinkotlin.c3

object ListExist {

    fun elem(element: Int, list: List<Int>): Boolean {
        if (list.isEmpty()) {
            return false
        }
        return element == list.first() || elem(element, list.drop(1))
    }
}
