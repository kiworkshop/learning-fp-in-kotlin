package org.kiworkshop.learningfpinkotlin.c3

object ListExist {

    fun elem(element: Int, list: List<Int>): Boolean {
        if (list.isEmpty()) {
            return false
        }
        return element == list.first() || elem(element, list.drop(1))
    }

    fun elemImproved(element: Int, list: List<Int>): Boolean {
        return elem(element, list, false)
    }

    private tailrec fun elem(element: Int, list: List<Int>, acc: Boolean): Boolean {
        if (list.isEmpty()) {
            return acc
        }
        val result = (list.first() == element)
        return elem(element, list.drop(1), acc || result)
    }
}
