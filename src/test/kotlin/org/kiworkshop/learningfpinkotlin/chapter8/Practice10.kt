package org.kiworkshop.learningfpinkotlin.chapter8

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.chapter8.Practice3.FunList
import org.kiworkshop.learningfpinkotlin.of

class Practice10 : FreeSpec() {
    init {
        "연습문제 8-3에서 만든 리스트 애플리케이티브 펑터가 교환 법칙을 만족하는지 확인해 보자."{
            val x = 15
            val listAf = FunList.pure { y: Int -> y * 2 }
            val leftList = listAf apply FunList.pure(x)
            val rightList = FunList.pure(of<Int, Int>(x)) apply listAf
            leftList.toString() shouldBe rightList.toString()
        }
    }
}
