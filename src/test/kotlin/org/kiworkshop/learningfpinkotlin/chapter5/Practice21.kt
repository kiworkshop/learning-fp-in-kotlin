package org.kiworkshop.learningfpinkotlin.chapter5

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.emptyMyFunStream
import org.kiworkshop.learningfpinkotlin.map
import org.kiworkshop.learningfpinkotlin.myFunStreamOf

class Practice21 : FreeSpec() {

    init {
        "FunList에서 작성했던 map 함수를 FunStream에도 추가하자."{
            myFunStreamOf(1, 2, 3, 4, 5).map { it + 1 } shouldBe myFunStreamOf(2, 3, 4, 5, 6)
            myFunStreamOf(2, 1, 3, 4, 5).map { it * 2 } shouldBe myFunStreamOf(4, 2, 6, 8, 10)
            emptyMyFunStream<Int>().map { it * 3 } shouldBe emptyMyFunStream()
        }
    }
}
