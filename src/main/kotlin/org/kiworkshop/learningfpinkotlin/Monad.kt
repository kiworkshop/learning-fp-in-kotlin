package org.kiworkshop.learningfpinkotlin

// Code 10-1
interface Monad<out A> : Functor<A> {
    fun <V> pure(value: V): Monad<V>
    override fun <B> fmap(f: (A) -> B): Monad<B> = flatMap { a -> pure(f(a)) }
    infix fun <B> flatMap(f: (A) -> Monad<B>): Monad<B>
    infix fun <B> leadTo(m: Monad<B>): Monad<B> = flatMap { m }
}

// Code 10-2
sealed class MaybeMonad<out A> : Monad<A> {

    companion object {
        fun <V> pure(value: V): MaybeMonad<V> = Just(0).pure(value)
    }

    override fun <V> pure(value: V): MaybeMonad<V> = Just(value)

    override fun <B> fmap(f: (A) -> B): MaybeMonad<B> = super.fmap(f) as MaybeMonad<B>

    override infix fun <B> flatMap(f: (A) -> Monad<B>): MaybeMonad<B> = when (this) {
        is Just -> try {
            f(value) as MaybeMonad<B>
        } catch (e: ClassCastException) {
            Nothing
        }
        Nothing -> Nothing
    }
}

data class Just<out A>(val value: A) : MaybeMonad<A>() {
    override fun toString(): String = "Just($value)"
}

object Nothing : MaybeMonad<kotlin.Nothing>() {
    override fun toString(): String = "Nothing"
}

infix fun <A, B> MaybeMonad<(A) -> B>.apply(f: MaybeMonad<A>): MaybeMonad<B> = when (this) {
    is Just -> f.fmap(value)
    Nothing -> Nothing
}

// Exercise 10-1
sealed class FunListMonad<out A> : Monad<A> {

    companion object {
        fun <V> pure(value: V): FunListMonad<V> = Cons(0, Nil).pure(value)
    }

    override fun <V> pure(value: V): FunListMonad<V> = when (this) {
        is Cons -> Cons(value, Nil)
        Nil -> Nil
    }

    override fun <B> fmap(f: (A) -> B): FunListMonad<B> = super.fmap(f) as FunListMonad<B>

    override infix fun <B> flatMap(f: (A) -> Monad<B>): FunListMonad<B> = when (this) {
        is Cons -> try {
            f(head) as FunListMonad<B> mappend tail.flatMap(f)
        } catch (e: ClassCastException) {
            Nil
        }
        Nil -> Nil
    }

    infix fun <A> FunListMonad<A>.mappend(other: FunListMonad<A>): FunListMonad<A> = when (this) {
        is Cons -> when (other) {
            Nil -> this
            is Cons -> Cons(this.head, this.tail.mappend(other))
        }
        Nil -> other
    }

//    infix fun <B> leadTo(m: FunListMonad<B>): FunListMonad<B> = flatMap { m }
}

data class Cons<out A>(val head: A, val tail: FunListMonad<A>) : FunListMonad<A>() {
    override fun toString(): String = "Cons($head, $tail)"
}

object Nil : FunListMonad<kotlin.Nothing>() {
    override fun toString(): String = "Nil"
}

infix fun <A, B> FunListMonad<(A) -> B>.apply(f: FunListMonad<A>): FunListMonad<B> = when (this) {
    is Cons -> f.fmap(head) mappend tail.apply(f)
    is Nil -> Nil
}