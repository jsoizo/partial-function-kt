package com.jsoizo.paritialfunction.extensions

import com.jsoizo.paritialfunction.pf
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ResultExtensionTest {

    val evenSquarePf = pf<Int, Int>({it % 2 ==0}, {it * it})

    @Test
    fun collectTest() {
        val rValue = Result.success(10)

        val result = rValue.collect(evenSquarePf)

        assertEquals(result, Result.success(100))
    }

    @Test
    fun collectTestFailure() {
        val rValue = Result.failure<Int>(Exception())

        val result = rValue.collect(evenSquarePf)

        assertEquals(result, rValue)
    }

    @Test
    fun collectTestNotMatched() {
        val rValue = Result.success(99)

        val result = rValue.collect(evenSquarePf)

        assertTrue(result.exceptionOrNull() is NoSuchElementException)
    }

    val recoverPf = pf<Throwable, Int>({ it is Exception }, { 100 })

    @Test
    fun recoverTest() {
        val rValue = Result.failure<Int>(Exception())

        val result = rValue.recover(recoverPf)

        assertEquals(result, Result.success(100))
    }

    @Test
    fun recoverTestSuccess() {
        val rValue = Result.success(10)

        val result = rValue.recover(recoverPf)

        assertEquals(result, Result.success(10))
    }

    @Test
    fun recoverTestNotMatched() {
        val rValue = Result.failure<Int>(Error())

        val result = rValue.recover(recoverPf)

        assertEquals(result, rValue)
    }

    val recoverWithPf = pf<Throwable, Result<Int>>({ it is Exception }, { Result.success( 100) })

    @Test
    fun recoverWithTest() {
        val rValue = Result.failure<Int>(Exception())

        val result = rValue.recoverWith(recoverWithPf)

        assertEquals(result, Result.success(100))
    }

    @Test
    fun recoverWithTestSuccess() {
        val rValue = Result.success(10)

        val result = rValue.recoverWith(recoverWithPf)

        assertEquals(result, rValue)
    }

    @Test
    fun recoverWithTestNotMatched() {
        val rValue = Result.failure<Int>(Error())

        val result = rValue.recoverWith(recoverWithPf)

        assertEquals(result, rValue)
    }
}