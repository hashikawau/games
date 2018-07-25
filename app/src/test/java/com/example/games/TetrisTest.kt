package com.example.games

import com.example.games.model.Field;
import org.junit.Test

import org.junit.Assert.*

class TetrisTest {
    @Test
    fun field_isCorrect() {
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
}
