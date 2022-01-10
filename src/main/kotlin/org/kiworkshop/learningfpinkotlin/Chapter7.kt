package org.kiworkshop.learningfpinkotlin

class Chapter7

// interface Functor<A> {
//    fun <B> fmap(f: (A) -> B): Functor<B>
// }

//  sealed class Maybe<A> : Functor<A> {
//    abstract override fun toString(): String
//    abstract override fun <B> fmap(f: (A) -> B): Maybe<B>
//
//    data class Just<A>(val value: A) : Maybe<A>() {
//        override fun toString(): String = "Just($value)"
//        override fun <B> fmap(f: (A) -> B): Maybe<B> = Just(f(value))
//    }
//
//    object Nothing : Maybe<kotlin.Nothing>() {
//        override fun toString(): String = "Nothing"
//        override fun <B> fmap(f: (kotlin.Nothing) -> B): Maybe<B> = Nothing
//    }
//  }

// sealed class Tree<A> : Functor<A> {
//    abstract override fun toString(): String
//    abstract override fun <B> fmap(f: (A) -> B): Tree<B>
// }
//
// object EmptyTree : Tree<kotlin.Nothing>() {
//    override fun toString(): String = "E"
//
//    override fun <B> fmap(f: (kotlin.Nothing) -> B): Tree<B> = EmptyTree
// }
//
// data class Node<A>(val value: A, val left: Tree<A>, val right: Tree<A>) : Tree<A>() {
//    override fun toString(): String = "N $value $left $right"
//
//    override fun <B> fmap(f: (A) -> B): Tree<B> = Node(f(value), left.fmap(f), right.fmap(f))
// }

// sealed class Either<L, R> : Functor<R> {
//    abstract override fun <R2> fmap(f: (R) -> R2): Either<L, R2>
// }
//
// data class Left<L>(val value: L) : Either<L, kotlin.Nothing>() {
//    override fun <R2> fmap(f: (kotlin.Nothing) -> R2): Either<L, R2> = this
// }
//
// data class Right<R>(val value: R) : Either<kotlin.Nothing, R>() {
//    override fun <R2> fmap(f: (R) -> R2): Either<kotlin.Nothing, R2> = Right(f(value))
// }
