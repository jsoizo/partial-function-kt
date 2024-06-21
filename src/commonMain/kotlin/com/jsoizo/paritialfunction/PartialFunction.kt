package com.jsoizo.paritialfunction

interface PartialFunction<A, B> : (A) -> B? {
    fun isDefinedAt(a: A): Boolean
    fun apply(a: A): B
    override fun invoke(a: A): B? = if (isDefinedAt(a)) apply(a) else null

    infix fun orElse(other: PartialFunction<A, B>): PartialFunction<A, B> = object : PartialFunction<A, B> {
        override fun isDefinedAt(a: A): Boolean = this@PartialFunction.isDefinedAt(a) || other.isDefinedAt(a)
        override fun apply(a: A): B =
            if (this@PartialFunction.isDefinedAt(a)) this@PartialFunction.apply(a)
            else other.apply(a)
    }

    infix fun <C> andThen(other: PartialFunction<B, C>): PartialFunction<A, C> = object : PartialFunction<A, C> {
        override fun isDefinedAt(a: A): Boolean =
            this@PartialFunction.isDefinedAt(a) && other.isDefinedAt(this@PartialFunction.apply(a))

        override fun apply(a: A): C = other.apply(this@PartialFunction.apply(a))
    }

    infix fun <C> andThen(other: (B) -> C): PartialFunction<A, C> = object : PartialFunction<A, C> {
        override fun isDefinedAt(a: A): Boolean = this@PartialFunction.isDefinedAt(a)
        override fun apply(a: A): C = other(this@PartialFunction.apply(a))
    }

    fun <C> runWith(f: (B) -> C): (A) -> Boolean = { a ->
        if (isDefinedAt(a)) {
            f(apply(a))
            true
        } else false
    }

    fun lift(): (A) -> B? = this
}

typealias PF<A, B> = PartialFunction<A, B>

fun <A, B> partialFunction(isDefinedAt: (A) -> Boolean, apply: (A) -> B): PF<A, B> =
    object : PartialFunction<A, B> {
        override fun isDefinedAt(a: A): Boolean = isDefinedAt(a)
        override fun apply(a: A) = apply(a)
    }

fun <A, B> pf(isDefinedAt: (A) -> Boolean, apply: (A) -> B): PF<A, B> =
    partialFunction(isDefinedAt, apply)
