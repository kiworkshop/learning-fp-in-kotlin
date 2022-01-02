package org.kiworkshop.learningfpinkotlin

sealed class Either<out L, out R> : Functor<R> {
    abstract override fun <R2> fmap(f: (R) -> R2): Either<L, R2>
}

data class Left<out L>(val value: L) : Either<L, Nothing>() {
    override fun <R2> fmap(f: (Nothing) -> R2): Either<L, R2> = this
}

data class Right<out R>(val value: R) : Either<Nothing, R>() {
    override fun <R2> fmap(f: (R) -> R2): Either<Nothing, R2> = Right(f(value))
}
