package org.kiworkshop.learningfpinkotlin.chapter5

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.emptyMyFunList
import org.kiworkshop.learningfpinkotlin.indexedMap
import org.kiworkshop.learningfpinkotlin.myFunListOf

class Practice8 : FreeSpec() {

    init {
        "앞서 작성한 map 함수에서 고차 함수가 값들의 순서값(인덱스)도 같이 받아 올 수 있는 indexMap 함수를 만들자."{
            val list = myFunListOf(1, 2, 3)
            list.indexedMap { index, value -> index + value } shouldBe myFunListOf(1, 3, 5)

            emptyMyFunList<Int>().indexedMap { index, value -> index + value } shouldBe emptyMyFunList()
        }
    }
}
