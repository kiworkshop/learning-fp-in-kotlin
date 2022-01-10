package org.kiworkshop.learningfpinkotlin

sealed class AMaybe<out A> : Applicative<A> {
    companion object {
        fun <V> pure(value: V): Applicative<V> = AJust(0).pure(value)
    }

    override fun <V> pure(value: V): Applicative<V> = AJust(value)

    abstract override fun <B> apply(ff: Applicative<(A) -> B>): AMaybe<B>
}

data class AJust<out A>(val value: A) : AMaybe<A>() {
    override fun toString(): String = "AJust($value)"

    override fun <B> apply(ff: Applicative<(A) -> B>): AMaybe<B> {
        return when (ff) {
            is AJust -> fmap(ff.value)
            else -> ANothing
        }
    }

    override fun <B> fmap(f: (A) -> B): AMaybe<B> = AJust(f(value))
}

object ANothing : AMaybe<kotlin.Nothing>() {
    override fun <B> apply(ff: Applicative<(kotlin.Nothing) -> B>): AMaybe<B> = ANothing

    override fun <B> fmap(f: (kotlin.Nothing) -> B): AMaybe<B> = ANothing
}
