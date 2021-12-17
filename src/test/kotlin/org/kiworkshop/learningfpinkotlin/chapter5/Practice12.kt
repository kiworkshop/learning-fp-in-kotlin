package org.kiworkshop.learningfpinkotlin.chapter5

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.emptyMyFunList
import org.kiworkshop.learningfpinkotlin.filterByFoldRight
import org.kiworkshop.learningfpinkotlin.myFunListOf

class Practice12 : FreeSpec() {

    init {
        "filter 함수를 foldRight 함수를 사용해서 다시 작성해 보자."{
            myFunListOf(1, 2, 3).filterByFoldRight { it % 2 == 0 } shouldBe myFunListOf(2)
            myFunListOf(1, 2, 3, 4).filterByFoldRight { it % 2 != 0 } shouldBe myFunListOf(1, 3)
            emptyMyFunList<Int>().filterByFoldRight { it % 2 == 0 } shouldBe emptyMyFunList()
            myFunListOf(1, 2, 3, 4, 5, 6, 7).filterByFoldRight { it < 5 } shouldBe myFunListOf(1, 2, 3, 4)
        }
    }
}
