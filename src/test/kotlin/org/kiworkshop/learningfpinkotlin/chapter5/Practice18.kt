package org.kiworkshop.learningfpinkotlin.chapter5

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.emptyMyFunStream
import org.kiworkshop.learningfpinkotlin.myFunStreamOf
import org.kiworkshop.learningfpinkotlin.product

class Practice18 : FreeSpec() {

    init {
        "FunList에서 작성했던 product 함수를 FunStream에도 추가하자."{
            myFunStreamOf(1, 2, 3).product() shouldBe 6
            myFunStreamOf(1).product() shouldBe 1
            myFunStreamOf(1, -1, 0).product() shouldBe 0
            emptyMyFunStream<Int>().product() shouldBe 1 // 이상하데 어떻게 처리해야 할까
        }
    }
}
