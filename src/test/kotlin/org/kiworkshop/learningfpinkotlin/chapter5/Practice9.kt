package org.kiworkshop.learningfpinkotlin.chapter5

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.maximumByFoldLeft
import org.kiworkshop.learningfpinkotlin.myFunListOf

class Practice9 : FreeSpec() {

    init {
        "3장에서 작성한 maximum 함수를 foldLeft 함수를 사용해서 다시 작성해 보자."{
            myFunListOf(1, 2, 3).maximumByFoldLeft() shouldBe 3
            myFunListOf(3, 2, 1).maximumByFoldLeft() shouldBe 3
            myFunListOf(1, 1, 1).maximumByFoldLeft() shouldBe 1
            myFunListOf(3, 12, 21, 12).maximumByFoldLeft() shouldBe 21
        }
    }
}
