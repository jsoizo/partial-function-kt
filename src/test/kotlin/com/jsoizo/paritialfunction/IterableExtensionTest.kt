package com.jsoizo.paritialfunction

import com.jsoizo.paritialfunction.extensions.collect
import com.jsoizo.paritialfunction.extensions.collectFirst
import kotlin.test.Test
import kotlin.test.assertNull

class IterableExtensionTest {

    val evenSquarePf = pf<Int, Int>({it % 2 ==0}, {it * it})

    @Test
    fun testCollect() {
        val list = listOf(1,2,3,4)

        val result = list.collect(evenSquarePf)

        assert(result == listOf(4, 16))
    }

    @Test
    fun testCollectFirst() {
        val list = listOf(4,6,8)

        val result = list.collectFirst(evenSquarePf)

        assert(result == 16)
    }

    @Test
    fun testCollectFirstNotMatched() {
        val list = listOf(1,3,5)

        val result = list.collectFirst(evenSquarePf)

        assertNull(result)
    }

}