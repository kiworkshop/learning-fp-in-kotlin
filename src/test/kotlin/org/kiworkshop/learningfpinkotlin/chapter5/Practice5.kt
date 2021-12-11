package org.kiworkshop.learningfpinkotlin.chapter5

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.MyFunList
import org.kiworkshop.learningfpinkotlin.MyFunList.Nil
import org.kiworkshop.learningfpinkotlin.dropWhile
import org.kiworkshop.learningfpinkotlin.emptyMyFunList
import org.kiworkshop.learningfpinkotlin.getHead
import org.kiworkshop.learningfpinkotlin.getTail
import org.kiworkshop.learningfpinkotlin.myFunListOf

class Practice5 : FreeSpec() {
    private fun checkImmutability(originalList: MyFunList<Int>) {
        originalList.getHead() shouldBe 1
        originalList.getTail().getHead() shouldBe 2
        originalList.getTail().getTail().getHead() shouldBe 3
    }

    init {
        """다음과 같이 동작하는 dropWhile 함수를 구현하자.
           타입 T를 입력 받아 Boolean을 반환하는 함수 p를 입력받는다.
           리스트의 앞에서부터 함수 p를 만족하기 전까지 drop을 하고, 나머지 값들의 리스트를 반환한다.
           이때 원본 리스트가 바뀌지 않아야 하고, 새로운 리스트를 반환할 때마다 리스트를 생성하면 안 된다.
        """{
            val originalList = myFunListOf(1, 2, 3)
            val emptyList = emptyMyFunList<Int>()

            emptyList.dropWhile { true } shouldBe Nil

            val listLessThan2 = originalList.dropWhile { it < 2 }
            listLessThan2.getHead() shouldBe 2
            listLessThan2.getTail().getHead() shouldBe 3

            // 아무것도 drop 안되는 케이스
            originalList.dropWhile { it > 100 } shouldBe originalList

            // 모두 drop되는 케이스
            originalList.dropWhile { true } shouldBe Nil

            // 원본 리스트가 바뀌지 않는다.
            checkImmutability(originalList)

            // 새로운 리스트를 반환할 때 리스트를 생성하지 않는다.
            listLessThan2 shouldBe originalList.getTail()

            val listLessThan3 = originalList.dropWhile { it < 3 }
            listLessThan3 shouldBe originalList.getTail().getTail()
        }
    }

}
