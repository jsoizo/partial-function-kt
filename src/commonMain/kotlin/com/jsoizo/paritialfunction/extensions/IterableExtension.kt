package com.jsoizo.paritialfunction.extensions

import com.jsoizo.paritialfunction.PartialFunction

fun <A, B> Iterable<A>.collect(pf: PartialFunction<A, B>): List<B> =
    this.mapNotNull { pf(it) }

fun <A, B> Iterable<A>.collectFirst(pf: PartialFunction<A, B>): B? =
    this.firstNotNullOfOrNull { pf(it) }
