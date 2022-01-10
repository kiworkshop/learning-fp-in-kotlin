package org.kiworkshop.learningfpinkotlin

sealed class Tree<out A> : Functor<A> {
    abstract override fun <B> fmap(f: (A) -> B): Functor<B>

    companion object
}

data class Node<out A>(val value: A, val forest: List<Node<A>> = emptyList()) : Tree<A>() {
    override fun toString(): String = "$value $forest"

    override fun <B> fmap(f: (A) -> B): Node<B> = Node(f(value), forest.map { it.fmap(f) })
}

fun <A> Tree.Companion.pure(value: A) = Node(value)

infix fun <A, B> Node<(A) -> B>.apply(node: Node<A>): Node<B> = Node(
    value(node.value),
    node.forest.map { it.fmap(value) } + forest.map { it.apply(node) }
)
