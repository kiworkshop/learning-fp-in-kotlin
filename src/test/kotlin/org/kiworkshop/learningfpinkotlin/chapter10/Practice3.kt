package org.kiworkshop.learningfpinkotlin.chapter10

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Practice3 : FreeSpec() {
    class A4(val b: Maybe<B4>)
    class B4(val c: Maybe<C4>)
    class C4(val d: D4)
    class D4(val value: Maybe<String>)

    private fun getValueOfD4(a: A4): Maybe<String> {
        return a.b.flatMap { it.c }
            .fmap { it.d }
            .flatMap { it.value }
    }

    init {
        """
           다음과 같이 중첩된 클래스 중간에 널이 될 수 없는 프로퍼티 D4가 있다고 가정해 보자. 
           이때 A4에서 D4의 값 value를 얻기 위한 함수 getValueOfD4를 작성하고 테스트해 보자. 
        """ {
            val a = A4(Just(B4(Just(C4(D4(Just("test")))))))
            val result = when (val value = getValueOfD4(a)) {
                is Just -> value.value
                Nothing -> ""
            }
            result shouldBe "test"
        }
    }
}
