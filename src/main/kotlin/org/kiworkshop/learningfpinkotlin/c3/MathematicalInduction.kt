package org.kiworkshop.learningfpinkotlin.c3

interface MathematicalInduction {

    val statement: String

    fun baseStep(): String

    fun assumptionStep(): String

    fun inductiveStep(): String
}

class Fibonacci : MathematicalInduction {

    override val statement: String = """
        |func(0) = 0, func(1) = 1이고
        |func(n) = func(n - 1) + func(n - 2) (단, n >=2 인 정수)로 정의된 func가 주어졌을 때
        |func(n)은 n번째 피보나치 수이다. (n >= 0인 정수)
    """.trimMargin()

    override fun baseStep(): String {
        return "피보나치 수열의 0번째 수는 0, 1번째 수는 1이고 명제에서 func(0) = 0, func(1) = 1이 주어졌으므로 n = 0, 1일 때 성립한다"
    }

    override fun assumptionStep(): String {
        return "k >= 2인 정수 k에 대하여, func(k)는 k번째 피보나치 수라 가정한다."
    }

    override fun inductiveStep(): String {
        return """
            |func(k - 1)과 func(k - 2)는 각각 k-1, k-2번째 피보나치 수이다.
            |이 때, 피보나치 수열의 정의에 따라 func(k)는 func(k - 1)과 func(k - 2)의 합이다.
            |따라서 주어진 func에 대하여 func(n)은 n번째 피보나치 수이다.
        """.trimMargin()
    }
}
