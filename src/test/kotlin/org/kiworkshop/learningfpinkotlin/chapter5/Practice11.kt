package org.kiworkshop.learningfpinkotlin.chapter5

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.emptyMyFunList
import org.kiworkshop.learningfpinkotlin.myFunListOf
import org.kiworkshop.learningfpinkotlin.reverseByFoldRight

class Practice11 : FreeSpec() {

    init {
        "3장에서 작성한 reverse 함수를 foldRight 함수를 사용해서 다시 작성해 보자."{
            myFunListOf(1, 2, 3).reverseByFoldRight() shouldBe myFunListOf(3, 2, 1)
            myFunListOf(1, 4, 6, 78, 9).reverseByFoldRight() shouldBe myFunListOf(9, 78, 6, 4, 1)
            myFunListOf(1).reverseByFoldRight() shouldBe myFunListOf(1)
            emptyMyFunList<Int>().reverseByFoldRight() shouldBe emptyMyFunList()
        }
    }
}
