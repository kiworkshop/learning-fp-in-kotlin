package org.kiworkshop.learningfpinkotlin.chapter7

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.Cons
import org.kiworkshop.learningfpinkotlin.FunctorFunList
import org.kiworkshop.learningfpinkotlin.Nil
import org.kiworkshop.learningfpinkotlin.compose
import org.kiworkshop.learningfpinkotlin.identity

class Practice2 : FreeSpec() {
    init {
        "연습문제에서 만들어 본 리스트 펑터인 FunList가 펑터의 법칙을 만족하는지 확인해 보자." - {
            val funList = Cons(1, Nil)
            val funList2 = Cons(1, Cons(2, Nil))
            val emptyFunLit = Nil as FunctorFunList<Int>

            "1법칙" {
                funList.fmap(::identity) shouldBe identity(funList)
                funList2.fmap(::identity) shouldBe identity(funList2)
                emptyFunLit.fmap(::identity) shouldBe identity(emptyFunLit)
            }

            "2법칙" {
                val f = { a: Int -> a + 1 }
                val g = { b: Int -> b * 2 }

                funList.fmap(f compose g) shouldBe funList.fmap(g).fmap(f)
                funList2.fmap(f compose g) shouldBe funList2.fmap(g).fmap(f)
                emptyFunLit.fmap(f compose g) shouldBe emptyFunLit.fmap(g).fmap(f)
            }
        }
    }
}
