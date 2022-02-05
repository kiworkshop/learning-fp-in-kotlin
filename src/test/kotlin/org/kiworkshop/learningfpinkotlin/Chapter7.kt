package org.kiworkshop.learningfpinkotlin

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Chapter7 : FunSpec({

    context("P.180 연습문제 7-1") {
        test(
            "5장에서 만든 FunList를 Functor의 인스턴스로 만들어보자." +
                    "FunList에 이미 map 함수등이 존재하지만, fmap, first, size와 같은 기본적인 기능만 제공하는 형태로 다시 작성하라"
        ) {
            val functorList = FCons(1, FCons(2, FCons(3, FCons(4, FNil))))

            functorList.fmap { it * 2 }.toList() shouldBe listOf(2, 4, 6, 8)
        }
    }

    context("P.190 연습문제 7-2") {
        test("연습문제에서 만들어본 리스트 펑터인 FunList가 펑터의 법칙을 만족하는지 확인해보자.") {
            val functorList = FCons(1, FCons(2, FCons(3, FCons(4, FNil))))

            // 제 1 법칙
            fun <T> identity(x: T) = x

            FNil.fmap { identity(it) } shouldBe identity(FNil)
            functorList.fmap { identity(it) } shouldBe identity(functorList)

            // 제 2 법칙
            val f = { a: Int -> a + 1 }
            val g = { b: Int -> b * 2 }

            FNil.fmap(f compose g) shouldBe FNil.fmap(f).fmap(g)
            functorList.fmap(f compose g) shouldBe functorList.fmap(g).fmap(f)
        }
    }
})
