package org.kiworkshop.learningfpinkotlin.chapter5

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.generateMyFunStream
import org.kiworkshop.learningfpinkotlin.sum
import org.kiworkshop.learningfpinkotlin.take

class Practice22 : FreeSpec() {

    init {
        """
           FunStream에서 필요한 값을 가져오는 take 함수를 추가하자. 
           FunStream은 무한대를 표현한 컬렉션이다. take 함수를 사용하여 값을 5개 가져온 후 합계를 구해 보자.
        """{
            generateMyFunStream(1) { it + 1 }.take(5).sum() shouldBe 15
            generateMyFunStream(1) { it + 2 }.take(5).sum() shouldBe 25
        }
    }
}
