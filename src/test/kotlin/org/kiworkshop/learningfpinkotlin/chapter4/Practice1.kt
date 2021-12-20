package org.kiworkshop.learningfpinkotlin.chapter4

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class PartialFunction<P, R>(
    private val condition: (P) -> Boolean,
    private val f: (P) -> R
) : (P) -> R {
    override fun invoke(p: P): R = when {
        condition(p) -> f(p)
        else -> throw IllegalArgumentException("$p isn't supported")
    }

    fun isDefinedAt(p: P): Boolean = condition(p)
    fun invokeOrElse(p: P, default: R): R = if (condition(p)) f(p) else default

    fun orElse(that: PartialFunction<P, R>): PartialFunction<P, R> = PartialFunction(
        condition = { condition(it) || that.condition(it) },
        f = {
            when {
                condition(it) -> f(it)
                that.condition(it) -> that.f(it)
                else -> throw IllegalArgumentException("$it isn't supported")
            }
        }
    )
}

class Practice1 : FreeSpec() {

    init {
        """ 코드 4-12에서 구현한 PartialFunction 클래스에 invokeOrElse 함수와 orElse 함수를 추가해 보자. 각 함수의 프로토타입은 다음과 같다.
            
            fun invokeOrElse(p: P, default: R): R
            fun orElse(that: PartialFunction<P, R>): PartialFunction<P, R>
            
            invokeOrElse 함수는 입력값 p가 조건에 맞지 않을 때 기본값 default를 반환한다.
            orElse 함수는 PartialFunction의 입력값 p가 조건에 맞으면 PartialFuncion을 그대로(this) 반환하고,
            조건에 맞지 않으면 that을 반환한다.
            """{
            val partialFunction1 = PartialFunction<Int, Int>({ it % 2 == 0 }, { it * 2 })
            val partialFunction2 = PartialFunction<Int, Int>({ it % 3 == 0 }, { it * 3 })
            partialFunction1.invoke(2) shouldBe 4
            partialFunction2.invoke(3) shouldBe 9
            partialFunction1.invokeOrElse(2, 5) shouldBe 4
            partialFunction1.invokeOrElse(3, 5) shouldBe 5
            partialFunction2.invokeOrElse(6, 10) shouldBe 18
            partialFunction2.invokeOrElse(2, 10) shouldBe 10
            val composedPartialFunction = partialFunction1.orElse(partialFunction2)
            composedPartialFunction.invoke(2) shouldBe 4
            composedPartialFunction.invoke(3) shouldBe 9
            shouldThrow<IllegalArgumentException> { composedPartialFunction.invoke(5) }
        }
    }
}
