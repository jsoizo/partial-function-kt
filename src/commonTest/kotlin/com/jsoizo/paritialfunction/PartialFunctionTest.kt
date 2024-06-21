package com.jsoizo.paritialfunction

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class PartialFunctionTest {

    @Test
    fun invokeReturnsValueWhenDefinedAtReturnsTrue() {
        val pf = pf<String, String>(
            { true }, { "foo" }
        )

        val result = pf.invoke("test")

        assertTrue(result == "foo")
    }

    @Test
    fun invokeReturnsNullWhenDefinedAtReturnsFalse() {
        val pf = pf<String, String>(
            { false }, { "foo" }
        )

        val result = pf.invoke("test")

        assertNull(result)
    }

    @Test
    fun andThenTest() {
        val pfEvenSquare = pf<Int, Int>(
            { it % 2 == 0 }, { it * it }
        )
        val pfPlusOne = pf<Int, Int>(
            { it > 0 }, { it + 1 }
        )
        val pf = pfEvenSquare.andThen(pfPlusOne)

        // When pfEvenSquare and pfPlusOne are applied
        val result1 = pf.invoke(2)
        assertTrue(result1 == 5)

        // When pfEvenSquare not applied
        val result2 = pf.invoke(3)
        assertNull(result2)

        // When pfPlusOne not applied
        val result3 = pf.invoke(0)
        assertNull(result3)
    }

    @Test
    fun andThenTest2() {
        val pfEvenSquare = pf<Int, Int>(
            { it % 2 == 0 }, { it * it }
        )
        val plusOne = { i: Int -> i + 1 }
        val pf = pfEvenSquare.andThen(plusOne)

        // When pfEvenSquare and pfPlusOne are applied
        val result1 = pf.invoke(2)
        assertTrue(result1 == 5)

        // When pfEvenSquare not applied
        val result2 = pf.invoke(3)
        assertNull(result2)
    }

    @Test
    fun orElseTest() {
        val pfEvenSquare = pf<Int, Int>(
            { it % 2 == 0 }, { it * it }
        )
        val pfPlusOne = pf<Int, Int>(
            { it > 0 }, { it + 1 }
        )

        val pf = pfEvenSquare.orElse(pfPlusOne)

        // When pfEvenSquare applied
        val result1 = pf.invoke(2)
        assertTrue(result1 == 4)

        // When pfEvenSquare not applied but pfPlusOne applied
        val result2 = pf.invoke(1)
        assertTrue(result2 == 2)

        // When pfEvenSquare and pfPlusOne not applied
        val result3 = pf.invoke(-3)
        assertNull(result3)
    }

    @Test
    fun runWithTest() {
        val messages = mutableMapOf<Int, String>()

        val pf = pf<Int, Int>(
            { it % 2 == 0 }, { it * it }
        )
        val pushMessage: (Int) -> Unit = { i ->
            messages[i] = "Value: $i"
        }

        val result1 = pf.runWith(pushMessage)(2)
        assertTrue(result1)
        assertTrue(messages[4] == "Value: 4")

        val result2 = pf.runWith(pushMessage)(1)
        assertFalse(result2)
        assertNull(messages[1])
    }

}