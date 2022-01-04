package org.kiworkshop.learningfpinkotlin

// list
fun String.head(): Char = first()
fun String.tail(): String = drop(1)

fun <T> List<T>.head(): T = first()
fun <T> List<T>.tail(): List<T> = drop(1)

// trampoline
sealed class Bounce<A>
data class Done<A>(val result: A) : Bounce<A>()
data class More<A>(val thunk: () -> Bounce<A>) : Bounce<A>()

tailrec fun <A> trampoline(bounce: Bounce<A>): A {
    println("trampoline called")

    return when (bounce) {
        is Done -> bounce.result
        is More -> trampoline(bounce.thunk())
    }
}

// partial function
class PartialFunction<in P, out R>(
    private val condition: (P) -> Boolean,
    private val f: (P) -> R
) : (P) -> R {
    override fun invoke(p: P): R = when {
        condition(p) -> f(p)
        else -> throw IllegalArgumentException("$p isn't supported.")
    }

    fun isDefinedAt(p: P): Boolean = condition(p)

    fun invokeOrElse(p: P, default: @UnsafeVariance R): R = if (condition(p)) f(p) else default
    fun orElse(that: PartialFunction<@UnsafeVariance P, @UnsafeVariance R>): PartialFunction<P, R> =
        PartialFunction(
            condition = { p -> this.condition(p) || that.condition(p) },
            f = { p ->
                when {
                    this.condition(p) -> this.f(p)
                    that.condition(p) -> that.f(p)
                    else -> throw IllegalArgumentException("$p isn't supported.")
                }
            }
        )
}

fun <P, R> ((P) -> R).toPartialFunction(definedAt: (P) -> Boolean): PartialFunction<P, R> =
    PartialFunction(definedAt, this)

infix fun <F, G, R> ((F) -> R).compose(g: (G) -> F): (G) -> R = { gInput: G -> this(g(gInput)) }

fun <P1, P2, R> ((P1, P2) -> R).curried(): (P1) -> (P2) -> R =
    { p1 -> { p2 -> this(p1, p2) } }

infix fun <F, G, H, R> ((F, G) -> R).compose(h: (H) -> F): (H, G) -> R =
    { hInput: H, gInput: G -> this(h(hInput), gInput) }

sealed class Tree<out T>
object Nil : Tree<Nothing>()
data class Leaf<out T>(val value: T, val left: Tree<T>, val right: Tree<T>) : Tree<T>()

fun Tree<Int>.insert(elem: Int): Tree<Int> = when (this) {
    Nil -> Leaf(elem, Nil, Nil)
    is Leaf -> when {
        elem <= value -> Leaf(value, left.insert(elem), right)
        else -> Leaf(value, left, right.insert(elem))
    }
}

tailrec fun Tree<Int>.insertRecur(elem: Int): Tree<Int> = when (this) {
    Nil -> Leaf(elem, Nil, Nil)
    is Leaf -> when {
        elem <= value -> Leaf(value, left.insert(elem), right)
        else -> Leaf(value, left, right.insert(elem))
    }
}

fun Tree<Int>.contains(elem: Int): Boolean = when (this) {
    Nil -> false
    is Leaf -> when (value) {
        elem -> true
        else -> when {
            elem <= value -> left.contains(elem)
            else -> right.contains(elem)
        }
    }
}
