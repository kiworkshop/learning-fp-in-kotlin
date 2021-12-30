package org.kiworkshop.learningfpinkotlin

sealed class Tree<out A> : Functor<A>, Foldable<A> {
    abstract override fun <B> fmap(f: (A) -> B): Tree<B>

    companion object
}

data class Node<out A>(val value: A, val forest: List<Node<A>> = emptyList()) : Tree<A>() {
    override fun toString(): String = "$value $forest"

    override fun <B> fmap(f: (A) -> B): Node<B> = Node(f(value), forest.map { it.fmap(f) })
   
    override fun <B> foldLeft(acc: B, f: (B, A) -> B): B {
        val accumulatorProcessed = when {
            forest.isEmpty() -> acc
            else -> forest.fold(acc) { innerAcc, node ->
                node.foldLeft(innerAcc, f)
            }
        }

        return f(accumulatorProcessed, value)
    }
}

fun <A> Tree.Companion.pure(value: A) = Node(value)

infix fun <A, B> Node<(A) -> B>.apply(node: Node<A>): Node<B> = Node(
    value(node.value),
    node.forest.map { it.fmap(value) } + forest.map { it.apply(node) }
)

fun <A, B, R> Tree.Companion.liftA2(binaryFunction: (A, B) -> R) =
    { f1: Node<A>, f2: Node<B> -> Tree.pure(binaryFunction.curried()) apply f1 apply f2 }
