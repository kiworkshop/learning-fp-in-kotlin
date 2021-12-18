package org.kiworkshop.learningfpinkotlin.chapter5

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.appendTail
import org.kiworkshop.learningfpinkotlin.emptyMyFunStream
import org.kiworkshop.learningfpinkotlin.myFunStreamOf

class Practice19 : FreeSpec() {

    init {
        "FunList에서 작성했던 appendTail 함수를 FunStream에도 추가하자."{
            myFunStreamOf(1, 2).appendTail(3) shouldBe myFunStreamOf(1, 2, 3)
            myFunStreamOf(2, 1).appendTail(3).appendTail(5) shouldBe myFunStreamOf(2, 1, 3, 5)
            emptyMyFunStream().appendTail(1) shouldBe myFunStreamOf(1)
        }
    }
}
