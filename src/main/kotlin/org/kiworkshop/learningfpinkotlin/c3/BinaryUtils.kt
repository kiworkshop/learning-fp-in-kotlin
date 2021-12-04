package org.kiworkshop.learningfpinkotlin.c3

object BinaryUtils {

    fun toBinary(n: Int): String {
        if (n == 0) {
            return "0"
        }
        if (n == 1) {
            return "1"
        }
        return toBinary(n / 2) + "${n % 2}"
    }
}
