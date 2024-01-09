package com.jsoizo.paritialfunction.extensions


import com.jsoizo.paritialfunction.pf
import kotlin.test.Test
import kotlin.test.assertNull

class NullableExtensionTest {

    val evenSquarePf = pf<Int, Int>({it % 2 ==0}, {it * it})

    @Test
    fun collectTest() {
        val nullableValue = 10

        val result = nullableValue.collect(evenSquarePf)

        assert(result == 100)
    }

    @Test
    fun collectTestNull() {
        val nullableValue: Int? = null

        val result = nullableValue.collect(evenSquarePf)

        assertNull(result)
    }

    @Test
    fun collectTestNotMatched() {
        val nullableValue: Int? = 9

        val result = nullableValue.collect(evenSquarePf)

        assertNull(result)
    }
}