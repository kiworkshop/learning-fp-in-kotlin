package org.kiworkshop.learningfpinkotlin

sealed class AFunList<out A> : Applicative<A> {
    companion object {
        fun <V> pure(value: V): ACons<V> = ACons(0, ANil).pure(value)
    }

    abstract override fun <B> fmap(f: (A) -> B): AFunList<B>
    override fun <V> pure(value: V): ACons<V> = ACons(value, ANil)
    abstract override fun <B> apply(ff: Applicative<(A) -> B>): AFunList<B>
}

object ANil : AFunList<kotlin.Nothing>() {
    override fun <B> fmap(f: (kotlin.Nothing) -> B): AFunList<B> = ANil
    override fun <B> apply(ff: Applicative<(kotlin.Nothing) -> B>): AFunList<B> = ANil
}

data class ACons<A>(val head: A, val tail: AFunList<A>) : AFunList<A>() {
    override fun <B> fmap(f: (A) -> B): AFunList<B> = ACons(f(head), tail.fmap(f))
    override fun <B> apply(ff: Applicative<(A) -> B>): AFunList<B> = when (ff) {
        is ACons -> ACons(ff.head(head), tail apply ff)
        else -> ANil
    }
}
