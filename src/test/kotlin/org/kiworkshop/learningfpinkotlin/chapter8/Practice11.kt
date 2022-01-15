package org.kiworkshop.learningfpinkotlin.chapter8

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.chapter8.Practice3.FunList

class Practice11 : FreeSpec() {
    init {
        "연습문제 8-3에서 만든 리스트 애플리케이티브 펑터가 pure(function) apply af = af.fmap(function)을 만족하는지 확인해 보자."{
            val x = 15
            val function = { y : Int -> y * 2}
            val listAf = FunList.pure(x)
            val leftList = FunList.pure(function) apply listAf
            val rightList = listAf.fmap(function)
            leftList.toString() shouldBe rightList.toString()
        }
    }
}
