package org.kiworkshop.learningfpinkotlin.chapter8

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.Functor

class Practice6 : FreeSpec() {
    sealed class ZipList<out A> : Functor<A> {
        abstract override fun <B> fmap(f: (A) -> B): ZipList<B>

        companion object
    }

    object Nil : ZipList<Nothing>() {
        override fun <B> fmap(f: (Nothing) -> B): ZipList<B> = Nil
    }

    data class Cons<A>(val head: A, val tail: ZipList<A>) : ZipList<A>() {
        override fun <B> fmap(f: (A) -> B): ZipList<B> = Cons(f(head), tail.fmap(f))
    }

    fun <T> funListOf(vararg elements: T): ZipList<T> = elements.toFunList()

    fun <T> Array<out T>.toFunList(): ZipList<T> = when {
        this.isEmpty() -> Nil
        else -> Cons(this[0], this.copyOfRange(1, this.size).toFunList())
    }

    fun <A> ZipList.Companion.pure(value: A): ZipList<A> = Cons(value, Nil)

    infix fun <A, B> ZipList<(A) -> B>.apply(f: ZipList<A>): ZipList<B> = when (this) {
        is Cons -> when (f) {
            is Cons -> Cons(head(f.head), tail apply f.tail)
            is Nil -> Nil
        }
        is Nil -> Nil
    }

    init {
        """
           연습문제 8-5를 통해서 작성한 두 리스트를 apply 함수로 적용하면 모든 가능한 조합의 리스트가 반환된다는 것을 확인했다.
           이번에는 리스트의 동일한 위치의 함수와, 값끼리 적용되는 집리스트(ZipList) 애플리케이티브 펑터를 만들고 테스트해 보자.
           두 리스트의 길이가 다른 경우, 반환되는 리스트는 둘 중 짧은 리스트의 길이와 같다.
           예를 들어 [(*5), (+10)]과 [10, 20, 30]을 적용하면 [50, 30]이 반환된다.
        """{
            val leftList = funListOf<(Int) -> Int>({ it * 5 }, { it + 10 })
            val rightList = funListOf(10, 20, 30)

            leftList apply rightList shouldBe funListOf(50, 30)
        }
    }
}
