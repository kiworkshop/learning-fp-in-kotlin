
package org.kiworkshop.learningfpinkotlin

interface Functor<out A> {
    fun <B> fmap(f: (A) -> B): Functor<B>
}

sealed class FunList<out A> : Functor<A> {
    abstract override fun <B> fmap(f: (A) -> B): FunList<B>
    abstract fun first(): A
    abstract fun size(): Int
}

//object Nil : FunList<Nothing>() {
//
//    override fun <B> fmap(f: (Nothing) -> B): FunList<B> = Nil
//
//    override fun first(): Nothing = throw NoSuchElementException()
//
//    override fun size(): Int = 0
//}
//
//data class Cons<A>(val head: A, val tail: FunList<A>) : FunList<A>() {
//
//    override fun <B> fmap(f: (A) -> B): FunList<B> = Cons(f(head), tail.fmap(f))
//
//    override fun first() = head
//
//    override fun size(): Int = 1 + tail.size()
//}
//
//fun <T> identity(x: T): T = x