package org.kiworkshop.learningfpinkotlin;

import org.kiworkshop.learningfpinkotlin.ApplicativeFunList.ACons
import org.kiworkshop.learningfpinkotlin.ApplicativeFunList.ANil
import kotlin.Nothing

sealed class ApplicativeFunList<out T> : Applicative<T> {
    companion object {
        fun <V> pure(value : V) : ACons<V> = ACons(0, ANil).pure(value)
    }

    abstract override fun <B> fmap(f: (T) -> B): ApplicativeFunList<B>
    override fun <V> pure(value: V): ACons<V> = ACons(value, ANil)

    abstract override fun <B> apply(ff: Applicative<(T) -> B>): ApplicativeFunList<B>

    abstract fun first(): T
    abstract fun size() : Int

    data class ACons<out T>(val head: T, val tail: ApplicativeFunList<T>) : ApplicativeFunList<T>() {
        override fun <B> fmap(f: (T) -> B): ApplicativeFunList<B> = ACons(f(head), tail.fmap(f))

        override fun <B> apply(ff: Applicative<(T) -> B>): ApplicativeFunList<B> = when (ff) {
            is ACons -> ACons(ff.head(head), tail apply ff)
            else -> ANil
        }

        override fun first(): T = head
        override fun size(): Int = 1 + tail.size()
    }

    object ANil: ApplicativeFunList<Nothing>() {
        override fun <B> fmap(f: (Nothing) -> B): ApplicativeFunList<B> = ANil
        override fun <B> apply(ff: Applicative<(Nothing) -> B>): ApplicativeFunList<B> = ANil
        override fun first(): Nothing = throw NoSuchElementException()
        override fun size(): Int  = 0
    }
}

fun <T> applicativeFunListOf(vararg elements: T): ApplicativeFunList<T> = elements.toApplicativeFunList()

fun <T> Array<out T>.toApplicativeFunList(): ApplicativeFunList<T> = when {
    this.isEmpty() -> ANil
    else -> ACons(this[0], this.copyOfRange(1, this.size).toApplicativeFunList())
}
