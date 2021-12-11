package org.kiworkshop.learningfpinkotlin.chapter5

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.MyFunList
import org.kiworkshop.learningfpinkotlin.emptyMyFunList
import org.kiworkshop.learningfpinkotlin.myFunListOf
import org.kiworkshop.learningfpinkotlin.take

class Practice6 : FreeSpec() {
    private fun checkImmutability(originalList: MyFunList<Int>) {
        originalList shouldBe myFunListOf(1, 2, 3)
    }

    init {
        """주어진 리스트에서 앞의 값이 n개의 값을 가진 리스트를 반환하는 take 함수를 구현하자.
           이때 원본 리스트가 바뀌지 않아야 하고, 새로운 리스트를 반환할 때마다 리스트를 생성하면 안 된다.
        """{
            val originalList = myFunListOf(1, 2, 3)
            val emptyList = emptyMyFunList<Int>()

            shouldThrow<NoSuchElementException> { emptyList.take(1) }

            originalList.take(1) shouldBe myFunListOf(1)
            originalList.take(2) shouldBe myFunListOf(1, 2)
            originalList.take(3) shouldBe myFunListOf(1, 2, 3)

            // 원본 리스트가 바뀌지 않는다.
            checkImmutability(originalList)

            // 새로운 리스트를 반환할 때 리스트를 생성하지 않는다.
            // 이 문장의 정확한 의미가 뭐지..? 그냥 단순 구현에서 매 재귀마다 리스트를 생성하지 말라는 뜻인건가?
            // drop과 달리 최소 한 번은 List를 만들어야 하는 것 같은데 책의 시그니처 예시에서도 acc가 쓰인다
        }
    }

}
