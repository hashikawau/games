package com.example.games

import com.example.games.model.Block1
import com.example.games.model.Field;
import org.junit.Test

import org.junit.Assert.*

class TetrisTest {
    @Test
    fun field_size_isCorrect() {
        val field = Field(4, 6)
        assertEquals(field.toString(), """
            |    |
            |    |
            |    |
            |    |
            |    |
            |    |
        """.trimIndent())
    }

    @Test
    fun field_block_isCorrect() {
        val field = Field(4, 6)
        val block_1 = Block1(field)
        block_1.moveTo(5, 1)
        field.fix(block_1)
        assertEquals(field.toString(), """
            |    |
            |    |
            |    |
            |    |
            |    |
            | *  |
        """.trimIndent())
    }
}
