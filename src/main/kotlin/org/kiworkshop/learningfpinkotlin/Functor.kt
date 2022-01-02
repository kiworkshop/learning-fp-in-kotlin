package org.kiworkshop.learningfpinkotlin

// Code 7-3
interface Functor<out A> {
    fun <B> fmap(f: (A) -> B): Functor<B>
}

// Code 7-6
sealed class Maybe<out A> : Functor<A> {

    abstract override fun toString(): String

    abstract override fun <B> fmap(f: (A) -> B): Maybe<B>
}

// Code 7-7
data class Just<out A>(val value: A) : Maybe<A>() {

    override fun toString(): String = "Just($value)"

    override fun <B> fmap(f: (A) -> B): Maybe<B> = Just(f(value))
}

// Code 7-8
object Nothing : Maybe<kotlin.Nothing>() {

    override fun toString(): String = "Nothing"

    override fun <B> fmap(f: (kotlin.Nothing) -> B): Maybe<B> = Nothing
}

// Exercise 7-1
sealed class FunListFunctor<out A> : Functor<A> {

    abstract override fun toString(): String

    abstract override fun <B> fmap(f: (A) -> B): FunListFunctor<B>

    abstract fun first(): A

    abstract val size: Int

    data class Cons<out A>(val head: A, val tail: FunListFunctor<A>) : FunListFunctor<A>() {

        override fun toString(): String = "Cons($head, $tail)"

        override fun <B> fmap(f: (A) -> B): FunListFunctor<B> = Cons(f(head), tail.fmap(f))

        override fun first(): A = head

        override val size: Int get() = 1 + tail.size
    }

    object Nil : FunListFunctor<kotlin.Nothing>() {

        override fun toString(): String = "Nil"

        override fun <B> fmap(f: (kotlin.Nothing) -> B): FunListFunctor<B> = Nil

        override fun first(): kotlin.Nothing = throw NoSuchElementException("List is empty.")

        override val size: Int = 0
    }
}

fun <T> funListFunctorOf(vararg elements: T): FunListFunctor<T> = elements.toFunListFunctor()

private fun <T> Array<out T>.toFunListFunctor(): FunListFunctor<T> = when {
    this.isEmpty() -> FunListFunctor.Nil
    else -> FunListFunctor.Cons(this[0], this.copyOfRange(1, this.size).toFunListFunctor())
}
