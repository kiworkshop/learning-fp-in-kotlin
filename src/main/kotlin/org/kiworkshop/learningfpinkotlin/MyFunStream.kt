package org.kiworkshop.learningfpinkotlin

import org.kiworkshop.learningfpinkotlin.MyFunStream.Cons
import org.kiworkshop.learningfpinkotlin.MyFunStream.Nil

sealed class MyFunStream<out T> {
    object Nil : MyFunStream<Nothing>()
    data class Cons<out T>(val head: () -> T, val tail: () -> MyFunStream<T>) : MyFunStream<T>() {
        override fun equals(other: Any?): Boolean =
            if (other is Cons<*>) {
                if (head() == other.head()) tail() == other.tail() else false
            } else {
                false
            }

        override fun hashCode(): Int {
            return 31 * head.hashCode() * tail.hashCode()
        }

        override fun toString(): String {
            val evalTail = getTail()
            val evalHead = getHead()
            if (evalTail === Nil) {
                return evalHead.toString()
            }
            return evalHead.toString() + ", " + evalTail.toString()
        }
    }
}

fun <T> myFunStreamOf(vararg elements: T) = elements.toMyFunStream()
fun <T> emptyMyFunStream() = Nil as MyFunStream<T>

tailrec fun <T> Array<out T>.toMyFunStream(
    index: Int = this.size - 1,
    acc: MyFunStream<T> = Nil
): MyFunStream<T> = when {
    index < 0 -> acc
    else -> this.toMyFunStream(index - 1, acc.addHead(this[index]))
}

fun <T> MyFunStream<T>.getHead(): T = when (this) {
    Nil -> throw NoSuchElementException()
    is Cons -> {
        println("getHead Eval ${head()}")
        head()
    }
}

fun <T> MyFunStream<T>.getTail(): MyFunStream<T> = when (this) {
    Nil -> throw NoSuchElementException()
    is Cons -> tail()
}

fun <T> MyFunStream<T>.addHead(value: T): MyFunStream<T> = Cons({ value }) { this }
fun <T> MyFunStream<T>.addHeadWithoutEval(valueFn: () -> T): MyFunStream<T> = Cons(valueFn) { this }

tailrec fun <T> MyFunStream<T>.appendTail(value: T, acc: MyFunStream<T> = Nil): MyFunStream<T> = when (this) {
    Nil -> Cons({ value }, { acc }).reverse()
    is Cons -> getTail().appendTail(value, acc.addHeadWithoutEval(head))
}

fun <T> MyFunStream<T>.reverse(acc: MyFunStream<T> = Nil): MyFunStream<T> = when (this) {
    Nil -> acc
    is Cons -> getTail().reverse(acc.addHeadWithoutEval(head))
}

tailrec fun MyFunStream<Int>.sum(acc: Int = 0): Int = when (this) {
    Nil -> acc
    is Cons -> getTail().sum(acc + getHead())
}

tailrec fun MyFunStream<Int>.product(acc: Int = 1): Int = when (this) {
    Nil -> acc
    is Cons -> getTail().product(acc * getHead())
}

fun <T> MyFunStream<T>.findSatisfiedValue(p: (T) -> Boolean): MyFunStream<T> = when (this) {
    Nil -> Nil
    is Cons -> {
        val evaluatedHead = getHead()
        if (p(evaluatedHead)) {
            Cons({ evaluatedHead }, this.tail)
        } else {
            getTail().findSatisfiedValue(p)
        }
    }
}

fun <T> MyFunStream<T>.filter(p: (T) -> Boolean): MyFunStream<T> = when (this) {
    Nil -> Nil
    is Cons -> {
        val firstSatisfiedValue = this.findSatisfiedValue(p)
        if (firstSatisfiedValue === Nil) Nil
        else
            Cons({
                firstSatisfiedValue.getHead()
            }, {
                firstSatisfiedValue.getTail().filter(p)
            })
    }
}
