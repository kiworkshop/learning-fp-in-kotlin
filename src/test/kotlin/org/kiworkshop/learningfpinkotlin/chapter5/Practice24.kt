package org.kiworkshop.learningfpinkotlin.chapter5

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.emptyMyFunList
import org.kiworkshop.learningfpinkotlin.myFunListOf
import org.kiworkshop.learningfpinkotlin.toString
import org.kiworkshop.learningfpinkotlin.toStringByFoldLeft
import kotlin.math.sqrt

class Practice24 : FreeSpec() {

    private tailrec fun solve(n: Int = 1, acc: Double = 0.0) : Int = when {
        acc > 1000 -> n - 1
        else -> solve(n + 1, acc + sqrt(n.toDouble()))
    }

    init {
        "모든 자연수의 제곱근의 합이 1000을 넘으려면 자연수가 몇 개 필요한지 계산하는 함수를 작성해 보자."{
            solve() shouldBe 131
        }
    }
}
