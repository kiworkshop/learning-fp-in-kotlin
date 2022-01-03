package org.kiworkshop.learningfpinkotlin.chapter7


/*
* 연습문제 7-1
* */
interface Functor<out A> {
    fun <B> fmap(f: (A) -> B): Functor<B>
}

sealed class FunList<out A> : Functor<A> {
    object Nil : FunList<Nothing>()
    data class Cons<out A>(val head: A, val tail: FunList<A>) : FunList<A>()

    override fun <B> fmap(f: (A) -> B): FunList<B> = when (this) {
        Nil -> Nil
        is Cons -> Cons(f(head), tail.fmap(f))
    }

    fun <A> FunList<A>.first(): A = when (this) {
        Nil -> throw NoSuchElementException()
        is Cons -> head
    }

    fun <A> FunList<A>.size(): Int {
        var count = 0
        var current = this
        while (current is Cons<A>) {
            count += 1
            current = current.tail
        }
        return count
    }


}