package org.kiworkshop.learningfpinkotlin.c8

import org.kiworkshop.learningfpinkotlin.c7.Functor

sealed interface ZipFunList<out A> : Functor<A> {

    companion object;

    override fun <B> fmap(f: (A) -> B): ZipFunList<B>
}

object ZNil : ZipFunList<Nothing> {
    override fun <B> fmap(f: (Nothing) -> B): ZipFunList<B> = ZNil
}

data class ZCons<out A>(val head: A, val tail: ZipFunList<A>) : ZipFunList<A> {
    override fun <B> fmap(f: (A) -> B): ZipFunList<B> = ZCons(f(head), tail.fmap(f))
}

fun <A> ZipFunList.Companion.pure(value: A) = ZCons(value, ZNil)

infix fun <A> ZipFunList<A>.append(other: ZipFunList<A>): ZipFunList<A> {
    tailrec fun ZipFunList<A>.appendReverse(acc: ZipFunList<A>): ZipFunList<A> = when (this) {
        ZNil -> acc
        is ZCons -> tail.appendReverse(acc.addHead(head))
    }

    return reverse().appendReverse(other)
}

infix fun <A, B> ZipFunList<(A) -> B>.apply(f: ZipFunList<A>): ZipFunList<B> = when (this) {
    ZNil -> ZNil
    is ZCons -> when (f) {
        ZNil -> ZNil
        is ZCons -> ZCons(head(f.head), tail apply f.tail)
    }
}

fun <T> ZipFunList<T>.addHead(head: T): ZipFunList<T> = ZCons(head, this)

fun <T> zipFunListOf(vararg elements: T): ZipFunList<T> = elements.toMutableList().toZipFunList()

tailrec fun <T> Collection<T>.toZipFunList(acc: ZipFunList<T> = ZNil): ZipFunList<T> = when {
    isEmpty() -> acc.reverse()
    else -> drop(1).toZipFunList(acc.addHead(first()))
}

tailrec fun <T> ZipFunList<T>.reverse(acc: ZipFunList<T> = ZNil): ZipFunList<T> = when (this) {
    ZNil -> acc
    is ZCons -> tail.reverse(acc.addHead(head))
}
