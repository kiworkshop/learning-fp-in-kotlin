package org.kiworkshop.learningfpinkotlin.chapter5

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.emptyMyFunList
import org.kiworkshop.learningfpinkotlin.myFunListOf
import org.kiworkshop.learningfpinkotlin.zip

class Practice13 : FreeSpec() {

    init {
        "zip 함수는 3장에서 이미 설명했다. 여기서는 직접 FunList에 zip 함수를 작성해 보자."{
            myFunListOf(1, 2, 3).zip(myFunListOf(4, 5)) shouldBe myFunListOf(1 to 4, 2 to 5)
            myFunListOf(1, 2, 3).zip(myFunListOf(4, 5, 6)) shouldBe myFunListOf(1 to 4, 2 to 5, 3 to 6)
            myFunListOf(1).zip(myFunListOf(4, 5)) shouldBe myFunListOf(1 to 4)
            emptyMyFunList<Int>().zip(myFunListOf(1, 2, 3)) shouldBe emptyMyFunList()
            myFunListOf(1).zip(emptyMyFunList<Int>()) shouldBe emptyMyFunList()
        }
    }
}
