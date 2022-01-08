package org.kiworkshop.learningfpinkotlin.chpater8

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.chpater8.Practice3.Cons
import org.kiworkshop.learningfpinkotlin.chpater8.Practice3.FunList
import org.kiworkshop.learningfpinkotlin.chpater8.Practice3.Nil

class Practice7 : FreeSpec() {
    fun identity() = { x: Int -> x }

    init {
        "연습문제 8-3에서 만든 리스트 애플리케이티브 펑터가 항등 법칙을 만족하는지 확인해 보자."{
            val listAf = Cons(1, Cons(2, Cons(3, Nil)))
            val leftList = FunList.pure(identity()) apply listAf

            leftList.toString() shouldBe listAf.toString()
        }
    }
}
