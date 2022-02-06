package org.kiworkshop.learningfpinkotlin

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Chapter8 : FunSpec({

    context("P.196 연습문제 8-1") {
        test(
            "7장에서 만든 리스트 펑터를 사용해서 product 함수에 [1, 2, 3, 4]를 적용한 부분 적용 함수의 리스트를 만들어보자." +
                    "만들어진 리스트에 여러가지 값을 넣어서 테스트해보자. 예를 들어 5를 넣으면 각 리스트 값에 5를 곱한 결과인 [5, 10, 15, 20]이 돼야한다."
        ) {
            // FunListApplicativeFunctor.kt
            val functorList: FunctorList<(Int) -> Int> = FCons(1, FCons(2, FCons(3, FCons(4, FNil))))
                .fmap { { x: Int -> it * x } }

            val partialAppliedFunctorList = functorList.fmap { it(5) } as FunctorList<Int>
            partialAppliedFunctorList.toList() shouldBe listOf(5, 10, 15, 20)
        }
    }

    context("P.201 연습문제 8-2") {
        test("7장에서 만든 리스트 펑터를 Applicative의 인스턴스로 만들고 테스트해보자.") {
            // FunListApplicativeFunctor.kt
            (FCons(1, FCons(2, FCons(3, FCons(4, FNil))))
                    apply FunctorList.pure { x: Int -> x * 2 })
                .toList() shouldBe listOf(2, 4, 6, 8)
        }
    }

    context("P.205 연습문제 8-3") {
        test("펑터를 상속받은 리스트를 만들고 pure, apply를 확장 함수로 작성해서 리스트 애플리케이티브 펑터를 만든 후 테스트해보자.") {
            // FunList.kt
            (FunList.pure { x: Int -> x * 2 } applyOnlyOnce funListOf(1, 2, 3, 4))
                .toList() shouldBe listOf(2, 4, 6, 8)
            (funListOf<(Int) -> Int>({ it * 2 }, { it * 3 }) apply funListOf(1, 2, 3, 4))
                .toList() shouldBe listOf(2, 4, 6, 8, 3, 6, 9, 12)
        }
    }
})
