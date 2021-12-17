package org.kiworkshop.learningfpinkotlin.chapter5

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.associate
import org.kiworkshop.learningfpinkotlin.emptyMyFunList
import org.kiworkshop.learningfpinkotlin.myFunListOf

class Practice14 : FreeSpec() {

    init {
        """
           zip 함수는 리스트와 리스트를 조합해서 리스트를 생성하는 함수다.
           여기서는 리스트의 값을 입력받은 조합 함수에 의해서 연관 자료구조인 맵을 생성하는 associate 함수를 작성해 보자. 
        """{
            myFunListOf(1, 2, 3).associate { it to it + 1 } shouldBe mapOf(1 to 2, 2 to 3, 3 to 4)
            myFunListOf(1, 3, 5).associate { it % 2 to it % 3 } shouldBe mapOf(1 to 1, 1 to 0, 1 to 2)
            emptyMyFunList<Int>().associate { it to it } shouldBe emptyMap()
        }
    }
}
