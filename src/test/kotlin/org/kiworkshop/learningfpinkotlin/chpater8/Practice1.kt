package org.kiworkshop.learningfpinkotlin.chpater8

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.curried
import org.kiworkshop.learningfpinkotlin.functorFunListOf

class Practice1 : FreeSpec() {
    init {
        """
           7장에서 만든 리스트 펑터를 사용해서 product 함수에 [1, 2, 3, 4]를 적용한 부분 적용 함수의 리스트를 만들어 보자.
           만들어진 리스트에 여러 가지 값을 넣어서 테스트해 보자.
           예를 들어 5를 넣으면 각 리스트 값에 5를 곱한 결과인 [5, 10, 15, 20]이 되어야 한다.
        """{
            val product: (Int, Int) -> Int = { a, b -> a * b }
            val curriedProduct = product.curried()
            val funList = functorFunListOf(1, 2, 3, 4)
            val appliedFunList = funList.fmap(curriedProduct)

            appliedFunList.fmap { it(5) } shouldBe functorFunListOf(5, 10, 15, 20)
            appliedFunList.fmap { it(1) } shouldBe funList
            appliedFunList.fmap { it(2) } shouldBe functorFunListOf(2, 4, 6, 8)
            appliedFunList.fmap { it(0) } shouldBe functorFunListOf(0, 0, 0, 0)
        }
    }
}
