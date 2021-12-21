package org.kiworkshop.learningfpinkotlin.chapter5

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.MyFunList
import org.kiworkshop.learningfpinkotlin.emptyMyFunList
import org.kiworkshop.learningfpinkotlin.myFunListOf
import org.kiworkshop.learningfpinkotlin.takeWhile

class Practice7 : FreeSpec() {
    private fun checkImmutability(originalList: MyFunList<Int>) {
        originalList shouldBe myFunListOf(1, 2, 3, 2)
    }

    init {
        """다음과 같이 동작하는 takeWhile 함수를 구현하자.
           타입 T를 입력 받아 Boolean을 반환하는 함수 p를 입력받는다.
           리스트의 앞에서부터 함수 p를 만족하는 값들의 리스트를 반환한다(모든 값이 함수 p를 만족하지 않는다면 원본 List를 반환). -> 문제가 이상하다. 빈 List를 반환하는 것이 맞지 않나?
           이때 원본 리스트가 바뀌지 않아야 하고, 새로운 리스트를 반환할 때마다 리스트를 생성하지 않아야 한다.
        """{
            val originalList = myFunListOf(1, 2, 3, 2)
            val emptyList = emptyMyFunList<Int>()

            emptyList.takeWhile { false } shouldBe emptyMyFunList()
            emptyList.takeWhile { true } shouldBe emptyMyFunList()

            val listLessThan2 = originalList.takeWhile { it < 2 }
            listLessThan2 shouldBe myFunListOf(1)

            // 아무것도 take 안되는 케이스
            // 문제에서는 원본을 반환하라고 되어있지만 takeWhile 의미에 맞지 않음. 빈 List를 반환하는 것이 적절
            // 저자의 의도가 궁금해 저자의 연습 문제 해답을 찾아봤는데 해답 또한 빈 List를 반환
            originalList.takeWhile { it > 100 } shouldBe emptyMyFunList()

            // 모두 take되는 케이스
            originalList.takeWhile { true } shouldBe myFunListOf(1, 2, 3, 2)

            // 원본 리스트가 바뀌지 않는다.
            checkImmutability(originalList)

            // 새로운 리스트를 반환할 때 리스트를 생성하지 않는다.
            // 이 또한 어떠한 의미를 가지는지 해석하기 어렵다
        }
    }
}
