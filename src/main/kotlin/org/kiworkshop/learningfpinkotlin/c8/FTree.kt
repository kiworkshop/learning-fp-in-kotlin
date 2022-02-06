package org.kiworkshop.learningfpinkotlin.c8

import org.kiworkshop.learningfpinkotlin.c7.Functor

sealed interface FTree<out A> : Functor<A> {

    companion object;

    override fun <B> fmap(f: (A) -> B): FTree<B>
}

data class FNode<out A>(val value: A, val forest: List<FNode<A>> = emptyList()) : FTree<A> {

    override fun <B> fmap(f: (A) -> B): FNode<B> = FNode(f(value), forest.map { it.fmap(f) })

    override fun toString() = "$value $forest"
}

fun <A> FTree.Companion.pure(value: A): FNode<A> = FNode(value)

infix fun <A, B> FNode<(A) -> B>.apply(node: FNode<A>): FNode<B> = FNode(
    value = value(node.value),
    forest = node.forest.map { it.fmap(value) } + forest.map { it apply node }
)
