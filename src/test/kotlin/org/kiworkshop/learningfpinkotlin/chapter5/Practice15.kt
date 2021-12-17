package org.kiworkshop.learningfpinkotlin.chapter5

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.emptyMyFunList
import org.kiworkshop.learningfpinkotlin.groupBy
import org.kiworkshop.learningfpinkotlin.myFunListOf

class Practice15 : FreeSpec() {

    init {
        "FunList의 값들을 입력 받은 키 생성 함수를 기준으로 맵을 생성하는 groupBy 함수를 작성해 보자."{
            myFunListOf(1, 2, 3).groupBy { it % 2 } shouldBe mapOf(0 to myFunListOf(2), 1 to myFunListOf(1, 3))
            myFunListOf(1, 3).groupBy { it % 2 } shouldBe mapOf(1 to myFunListOf(1, 3))
            myFunListOf(1, 2, 3).groupBy { it } shouldBe mapOf(
                1 to myFunListOf(1),
                2 to myFunListOf(2),
                3 to myFunListOf(3)
            )
            emptyMyFunList<Int>().groupBy { it % 2 } shouldBe emptyMap()
        }
    }
}
