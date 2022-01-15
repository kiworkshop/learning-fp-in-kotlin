package org.kiworkshop.learningfpinkotlin.chapter8

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.chapter8.Practice3.FunList
import org.kiworkshop.learningfpinkotlin.compose
import org.kiworkshop.learningfpinkotlin.curried

class Practice8 : FreeSpec() {
    init {
        "연습문제 8-3에서 만든 리스트 애플리케이티브 펑터가 합성 법칙을 만족하는지 확인해 보자."{
            val listAf1 = funListOf({ x: Int -> x * 2 })
            val listAf2 = funListOf({ x: Int -> x + 3 })
            val listAf3 = funListOf(5)
            val leftList = FunList.pure(compose<Int, Int, Int>().curried()) apply listAf1 apply listAf2 apply listAf3
            val rightList = listAf1 apply (listAf2 apply listAf3)
            leftList.toString() shouldBe rightList.toString()
        }
    }
}
