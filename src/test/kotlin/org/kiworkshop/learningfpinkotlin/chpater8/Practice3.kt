package org.kiworkshop.learningfpinkotlin.chpater8

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.Functor

class Practice3 : FreeSpec() {
    sealed class FunList<out A> : Functor<A> {
        abstract override fun <B> fmap(f: (A) -> B): FunList<B>

        companion object
    }

    object Nil : FunList<Nothing>() {
        override fun <B> fmap(f: (Nothing) -> B): FunList<B> = Nil
    }

    data class Cons<A>(val head: A, val tail: FunList<A>) : FunList<A>() {
        override fun <B> fmap(f: (A) -> B): FunList<B> = Cons(f(head), tail.fmap(f))
    }

    fun <T> funListOf(vararg elements: T): FunList<T> = elements.toFunList()

    fun <T> Array<out T>.toFunList(): FunList<T> = when {
        this.isEmpty() -> Nil
        else -> Cons(this[0], this.copyOfRange(1, this.size).toFunList())
    }

    init {
        "펑터를 상속받은 리스트를 만들고, pure와 apply를 확장 함수로 작성해서 리스트 애플리케이티프 펑터를 만든 후 테스트해 보자."{
            val list = funListOf(1, 2, 3, 4)

            FunList.pure(5) shouldBe funListOf(5)
            list append funListOf(3) shouldBe funListOf(1, 2, 3, 4, 3)
            funListOf({ a: Int -> a * 2 }) apply list shouldBe funListOf(2, 4, 6, 8)
        }
    }
}

fun <A> Practice3.FunList.Companion.pure(value: A): Practice3.FunList<A> = Practice3.Cons(value, Practice3.Nil)

infix fun <A> Practice3.FunList<A>.append(other: Practice3.FunList<A>): Practice3.FunList<A> = when (this) {
    is Practice3.Cons -> Practice3.Cons(this.head, this.tail append other)
    is Practice3.Nil -> other
}

infix fun <A, B> Practice3.FunList<(A) -> B>.apply(f: Practice3.FunList<A>): Practice3.FunList<B> = when (this) {
    is Practice3.Cons -> f.fmap(head)
    is Practice3.Nil -> Practice3.Nil
}
