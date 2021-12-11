package org.kiworkshop.learningfpinkotlin.chapter5

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.MyFunList
import org.kiworkshop.learningfpinkotlin.MyFunList.Nil
import org.kiworkshop.learningfpinkotlin.drop
import org.kiworkshop.learningfpinkotlin.emptyMyFunList
import org.kiworkshop.learningfpinkotlin.getHead
import org.kiworkshop.learningfpinkotlin.getTail
import org.kiworkshop.learningfpinkotlin.myFunListOf

class Practice4 : FreeSpec() {
    private fun checkImmutability(originalList: MyFunList<Int>) {
        originalList.getHead() shouldBe 1
        originalList.getTail().getHead() shouldBe 2
        originalList.getTail().getTail().getHead() shouldBe 3
    }

    init {
        """주어진 리스트에서 앞의 값이 n개 제외된 리스트를 반환하는 drop 함수를 구현하자.
           이때 원본 리스트가 바뀌지 않아야 하고, 새로운 리스트를 반환할 때마다 리스트를 생성하면 안 된다.
        """{
            val originalList = myFunListOf(1, 2, 3)
            val emptyList = emptyMyFunList<Int>()

            shouldThrow<NoSuchElementException> { emptyList.drop(1) }

            originalList.drop(1).getHead() shouldBe 2
            originalList.drop(2).getHead() shouldBe 3
            originalList.drop(3) shouldBe Nil

            // 원본 리스트가 바뀌지 않는다.
            checkImmutability(originalList)

            // 새로운 리스트를 반환할 때 리스트를 생성하지 않는다.
            originalList.drop(1) shouldBe originalList.getTail()
            originalList.drop(2) shouldBe originalList.getTail().getTail()
            originalList.drop(3) shouldBe originalList.getTail().getTail().getTail()
        }
    }

}
