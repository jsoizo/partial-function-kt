package com.jsoizo.paritialfunction.extensions

import com.jsoizo.paritialfunction.PartialFunction
import com.jsoizo.paritialfunction.partialFunction

fun <T, U> Result<T>.collect(pf: PartialFunction<T, U>): Result<U> =
    this.fold(
        { value ->
            pf(value)?.let { Result.success(it) }
                ?: Result.failure(NoSuchElementException("Predicate does not hold for $value"))
        }, {
            Result.failure(it)
        }
    )

fun <T> Result<T>.recover(pf: PartialFunction<Throwable, T>): Result<T> =
    recoverWith(pf.andThen(partialFunction({ true }, { Result.success(it) })))

fun <T> Result<T>.recoverWith(pf: PartialFunction<Throwable, Result<T>>) =
    exceptionOrNull()?.let(pf) ?: this