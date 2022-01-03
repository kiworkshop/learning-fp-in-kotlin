package org.kiworkshop.learningfpinkotlin.practice

import io.kotest.core.spec.style.StringSpec
import org.kiworkshop.learningfpinkotlin.compose
import org.kiworkshop.learningfpinkotlin.curried

class Chapter7 : StringSpec({
    "Code 7-7" {
        val just = Just("Hello").fmap { it.plus(" world!") }
        assert(just.toString() == "Just(Hello world!)")
    }

    "Code 7-9" {
        val value = Just(10).fmap { it + 10 }
        println(value)
        val nothing = FNothing
        nothing.fmap { a: Int -> a + 10 }
        println(nothing)
    }

    "Code 7-13" {
        val tree = treeOf(1, treeOf(2, treeOf(3), treeOf(4)), treeOf(5, treeOf(6), treeOf(7)))
        println(tree)
        val transformedTree = tree.fmap { it + 1 }
        println(transformedTree)
    }

    "Code 7-16" {
        fun divideTenByN(n: Int): Either<String, Int> = try {
            Right(10 / n)
        } catch (e: ArithmeticException) {
            Left("divide by zero")
        }

        println(divideTenByN(5))
        println(divideTenByN(0))
        println(divideTenByN(5).fmap { r -> r * 2 })
        println(divideTenByN(0).fmap { r -> r * 2 })
    }

    "CODE 7-18" {
        val f = { a: Int -> a + 1 }
        val g = { b: Int -> b * 2 }

        val fg = UnaryFunction(g).fmap(f)
        assert(fg.invoke(5) == 11)
    }

    "Code 7-21" {
        val g = { a: Int -> a * 2 }
        val k = { b: Int -> Just(b) }
        val kg = UnaryFunction(g).fmap(k)
        assert(kg.invoke(5) == Just(10))
    }
    "Code 7-24" {
        assert(FNothing.fmap { identity(it) } == identity(FNothing))
        assert(Just(5).fmap { identity(it) } == identity(Just(5)))

        assert(Node(1).fmap { identity(it) } == identity(Node(1)))
        assert(EmptyTree.fmap { identity(it) } == identity(EmptyTree))

        assert(Left("Error").fmap { identity(it) } == identity(Left("Error")))
        assert(Right(5).fmap { identity(it) } == identity(Right(5)))
    }
    "Code 7-27" {
        val f = { a: Int -> a + 1 }
        val g = { b: Int -> b * 2 }
        val nothingLeft = FNothing.fmap(f compose g)
        val nothingRight = FNothing.fmap(g).fmap(f)
        assert(nothingLeft == nothingRight)
        val justLeft = Just(5).fmap(f compose g)
        val justRight = Just(5).fmap(g).fmap(f)
        assert(justLeft == justRight)
    }
    "Code 7-31" {
        val justCounter = JustCounter(10, 3)
            .fmap { it + 10 }
            .fmap { it * 2 }
        assert(justCounter == JustCounter(40, 5))
        val nothing = NothingCounter
        assert(nothing == NothingCounter)
    }
    "Code 7-32" {
        println(NothingCounter.fmap { identity(it) } == identity(NothingCounter))
        println(JustCounter(5, 0).fmap { identity(it) } == identity(JustCounter(5, 0)))

        val f = { a: Int -> a + 1 }
        val g = { b: Int -> b * 2 }
        val nothingLeft = NothingCounter.fmap { f compose g }
        val nothingRight = NothingCounter.fmap(g).fmap(f)
        println(nothingLeft == nothingRight)

        val justLeft = JustCounter(5, 0).fmap { f compose g }
        val justRight = JustCounter(5, 0).fmap(g).fmap(f)
        println(justLeft == justRight)
    }
    "Code 7-34" {
        val product: (Int, Int) -> Int = { x: Int, y: Int -> x * y }
        val curriedProduct: (Int) -> (Int) -> Int = product.curried()
        val maybeProductTen: Maybe<(Int) -> Int> = Just(10).fmap(curriedProduct)
        println(maybeProductTen.fmap { it(5) } == Just(50))
    }
    "Example 7-1" {
        println(funcListOf(3).fmap { it * 2 } == funcListOf(6))
    }

    "Example 7-2" {
        println(NilF.fmap { identity(it) } == NilF)
        println(funcListOf(5).fmap { identity(it) } == ConF(5))

        val f = { a: Int -> a + 1 }
        val g = { b: Int -> b * 2 }

        val funcLeft = funcListOf(5).fmap(f compose g)
        val funcRight = funcListOf(5).fmap(g).fmap(f)
        println(funcLeft == funcRight)
        val nilLeft = NilF.fmap(f compose g)
        val nilRight = NilF.fmap(g).fmap(f)
        println(nilLeft == nilRight)
    }
})

interface Functor<out A> {
    fun <B> fmap(f: (A) -> B): Functor<B>
}

sealed class Maybe<out A> : Functor<A> {
    abstract override fun toString(): String
    abstract override fun <B> fmap(f: (A) -> B): Maybe<B>
}

data class Just<out A>(val value: A) : Maybe<A>() {
    override fun toString(): String = "Just($value)"
    override fun <B> fmap(f: (A) -> B): Maybe<B> = Just(f(value))
}

object FNothing : Maybe<Nothing>() {
    override fun toString(): String = "Nothing"
    override fun <B> fmap(f: (Nothing) -> B): Maybe<B> = FNothing
}

sealed class FuncList<out A> : Functor<A> {
    abstract override fun toString(): String
    abstract override fun <B> fmap(f: (A) -> B): FuncList<B>
}

object NilF : FuncList<Nothing>() {
    override fun toString(): String = "Nil"
    override fun <B> fmap(f: (Nothing) -> B): FuncList<B> = this
}

data class ConF<out A>(val value: A, val tail: FuncList<A> = NilF) : FuncList<A>() {
    override fun toString(): String = "($value, $tail)"
    override fun <B> fmap(f: (A) -> B): FuncList<B> = ConF(f(value), tail.fmap(f))
}

fun <T> funcListOf(value: T, tail: FuncList<T> = NilF): FuncList<T> =
    ConF(value, tail)

sealed class TreeF<out A> : Functor<A> {
    abstract override fun toString(): String
    abstract override fun <B> fmap(f: (A) -> B): TreeF<B>
}

object EmptyTree : TreeF<Nothing>() {
    override fun toString(): String = "E"
    override fun <B> fmap(f: (Nothing) -> B): TreeF<B> = EmptyTree
}

data class Node<out A>(val value: A, val leftTree: TreeF<A> = EmptyTree, val rightTree: TreeF<A> = EmptyTree) :
    TreeF<A>() {
    override fun toString(): String = "(N $value $leftTree $rightTree)"
    override fun <B> fmap(f: (A) -> B): TreeF<B> = Node(f(value), leftTree.fmap(f), rightTree.fmap(f))
}

fun <T> treeOf(value: T, leftTree: TreeF<T> = EmptyTree, rightTree: TreeF<T> = EmptyTree): TreeF<T> =
    Node(value, leftTree, rightTree)

sealed class Either<out L, out R> : Functor<R> {
    abstract override fun <R2> fmap(f: (R) -> R2): Either<L, R2>
}

data class Left<out L>(val value: L) : Either<L, Nothing>() {
    override fun <R2> fmap(f: (Nothing) -> R2): Either<L, R2> = this
}

data class Right<out R>(val value: R) : Either<Nothing, R>() {
    override fun <R2> fmap(f: (R) -> R2): Either<Nothing, R2> = Right(f(value))
}

data class UnaryFunction<in T, out R>(val g: (T) -> R) : Functor<R> {
    override fun <R2> fmap(f: (R) -> R2) = UnaryFunction<T, R2> { x: T -> (f compose g)(x) }

    fun invoke(input: T): R = g(input)
}

fun <T> identity(x: T): T = x

// fmap(f compose g) == fmap(f) compose fmap(g)
infix fun <F, G, R> ((F) -> R).compose(g: (G) -> F): (G) -> R {
    return { gInput: G -> this(g(gInput)) }
}

sealed class MaybeCounter<out A> : Functor<A> {
    abstract override fun toString(): String
    abstract override fun <B> fmap(f: (A) -> B): MaybeCounter<B>
}

data class JustCounter<out A>(val value: A, val count: Int) : MaybeCounter<A>() {
    override fun toString(): String = "JustCounter($value, $count)"
    override fun <B> fmap(f: (A) -> B): MaybeCounter<B> = JustCounter(f(value), count.inc())
}

object NothingCounter : MaybeCounter<Nothing>() {
    override fun toString(): String = "NothingCounter"
    override fun <B> fmap(f: (Nothing) -> B): MaybeCounter<B> = NothingCounter
}
