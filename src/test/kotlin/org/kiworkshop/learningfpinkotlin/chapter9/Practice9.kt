package org.kiworkshop.learningfpinkotlin.chapter9

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.ListMonoid
import org.kiworkshop.learningfpinkotlin.funListOf
import org.kiworkshop.learningfpinkotlin.mconcat

class Practice9 : FreeSpec() {
    init {
        """
           리스트 모노이드의 mconcat 함수를 [[1, 2], [3, 4], [5]]와 같은 중첩 리스트를 넣어서 테스트해 보자.
           테스트 결과를 확인하고, 동작 원리를 생각해 보자.
        """ {
            val funList = funListOf(funListOf(1, 2), funListOf(3, 4), funListOf(5))
            ListMonoid<Int>().mconcat(funList) shouldBe funListOf(1, 2, 3, 4, 5)
        }
    }
}
