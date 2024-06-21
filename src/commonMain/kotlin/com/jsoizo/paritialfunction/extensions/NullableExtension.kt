package com.jsoizo.paritialfunction.extensions

import com.jsoizo.paritialfunction.PartialFunction

fun <T, U> T?.collect(pf: PartialFunction<T, U>): U? = this?.let(pf)