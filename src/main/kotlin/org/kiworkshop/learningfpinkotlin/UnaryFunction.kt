package org.kiworkshop.learningfpinkotlin

data class UnaryFunction<in T, out R>(val g: (T) -> R) : Functor<R> {
    override fun <B> fmap(f: (R) -> B): Functor<B> = UnaryFunction { x: T -> f(g(x)) }

    fun invoke(input: T): R = g(input)
}
