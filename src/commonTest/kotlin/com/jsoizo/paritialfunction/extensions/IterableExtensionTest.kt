package com.jsoizo.paritialfunction.extensions

import com.jsoizo.paritialfunction.pf
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class IterableExtensionTest {

    val evenSquarePf = pf<Int, Int>({it % 2 ==0}, {it * it})

    @Test
    fun testCollect() {
        val list = listOf(1,2,3,4)

        val result = list.collect(evenSquarePf)

        assertEquals(result, listOf(4, 16))
    }

    @Test
    fun testCollectFirst() {
        val list = listOf(4,6,8)

        val result = list.collectFirst(evenSquarePf)

        assertTrue(result == 16)
    }

    @Test
    fun testCollectFirstNotMatched() {
        val list = listOf(1,3,5)

        val result = list.collectFirst(evenSquarePf)

        assertNull(result)
    }

}