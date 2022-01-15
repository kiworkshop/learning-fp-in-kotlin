package org.kiworkshop.learningfpinkotlin

sealed class MyTree<out A> : Functor<A> {
    abstract override fun <B> fmap(f: (A) -> B): Functor<B>

    companion object
}

data class MyNode<out A>(val value: A, val forest: List<MyNode<A>> = emptyList()) : MyTree<A>() {
    override fun toString(): String = if (forest.isEmpty()) "$value" else "$value $forest"

    override fun <B> fmap(f: (A) -> B): MyNode<B> = MyNode(f(value), forest.map { it.fmap(f) })
}

fun <A> MyTree.Companion.pure(value: A) = MyNode(value)

infix fun <A, B> MyNode<(A) -> B>.apply(node: MyNode<A>): MyNode<B> = MyNode(
    value(node.value),
    node.forest.map { it.fmap(value) } + forest.map { it apply node }
)

infix fun <A> MyNode<A>.append(node: MyNode<A>): MyNode<A> = MyNode(value, forest + node)

fun main() {
    val tree = MyNode(1, listOf(MyNode(2), MyNode(3)))
    println(tree.fmap { it * 2 })
    println(MyTree.pure({ x: Int -> x * 2 }) apply tree)

    println(
        MyTree.pure({ x: Int, y: Int -> x * y }.curried())
            apply MyNode(1, listOf(MyNode(2), MyNode(3)))
            apply MyNode(4, listOf(MyNode(5), MyNode(6)))
    )
}
