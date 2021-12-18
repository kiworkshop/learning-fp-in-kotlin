package org.kiworkshop.learningfpinkotlin.chapter5

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.emptyMyFunStream
import org.kiworkshop.learningfpinkotlin.filter
import org.kiworkshop.learningfpinkotlin.getHead
import org.kiworkshop.learningfpinkotlin.myFunStreamOf

class Practice20 : FreeSpec() {

    init {
        "FunList에서 작성했던 filter 함수를 FunStream에도 추가하자."{
            myFunStreamOf(1, 2, 3, 4, 5).filter { it % 2 == 0 } shouldBe myFunStreamOf(2, 4)
            myFunStreamOf(2, 1, 3, 4, 5).filter { it % 2 != 0 } shouldBe myFunStreamOf(1, 3, 5)
            myFunStreamOf(1).filter { it % 2 == 0 } shouldBe emptyMyFunStream()
        }
    }
}
