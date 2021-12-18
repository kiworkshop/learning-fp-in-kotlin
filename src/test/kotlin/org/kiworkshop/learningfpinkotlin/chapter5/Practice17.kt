package org.kiworkshop.learningfpinkotlin.chapter5

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.emptyMyFunStream
import org.kiworkshop.learningfpinkotlin.myFunStreamOf
import org.kiworkshop.learningfpinkotlin.sum

class Practice17 : FreeSpec() {

    init {
        "FunList에서 작성했던 sum 함수를 FunStream에도 추가하자."{
            myFunStreamOf(1, 2, 3).sum() shouldBe 6
            myFunStreamOf(1).sum() shouldBe 1
            myFunStreamOf(1, -1, 0).sum() shouldBe 0
            emptyMyFunStream().sum() shouldBe 0
        }
    }
}
