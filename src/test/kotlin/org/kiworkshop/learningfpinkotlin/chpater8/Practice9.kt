package org.kiworkshop.learningfpinkotlin.chpater8

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.chpater8.*
import org.kiworkshop.learningfpinkotlin.chpater8.Practice3.FunList

class Practice9 : FreeSpec() {
    init {
        "연습문제 8-3에서 만든 리스트 애플리케이티브 펑터가 준동형 사상 법칙을 만족하는지 확인해 보자."{
            val function = { x: Int -> x * 2 }
            val x = 15
            val leftMaybe = FunList.pure(function) apply FunList.pure(x)
            val rightMaybe = FunList.pure(function(x))
            leftMaybe.toString() shouldBe rightMaybe.toString()
        }
    }
}
