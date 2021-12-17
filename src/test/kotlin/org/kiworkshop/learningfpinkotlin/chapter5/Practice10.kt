package org.kiworkshop.learningfpinkotlin.chapter5

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.emptyMyFunList
import org.kiworkshop.learningfpinkotlin.filterByFoldLeft
import org.kiworkshop.learningfpinkotlin.myFunListOf

class Practice10 : FreeSpec() {

    init {
        "filter 함수를 foldLeft 함수를 사용해서 다시 작성해보자."{
            myFunListOf(1, 2, 3).filterByFoldLeft { it % 2 == 0 } shouldBe myFunListOf(2)
            myFunListOf(1, 2, 3, 4).filterByFoldLeft { it % 2 != 0 } shouldBe myFunListOf(1, 3)
            emptyMyFunList<Int>().filterByFoldLeft { it % 2 == 0 } shouldBe emptyMyFunList()
            myFunListOf(1, 2, 3, 4, 5, 6, 7).filterByFoldLeft { it < 5 } shouldBe myFunListOf(1, 2, 3, 4)
        }
    }
}
