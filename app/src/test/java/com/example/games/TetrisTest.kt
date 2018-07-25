package com.example.games

import com.example.games.model.Block1
import com.example.games.model.Field;
import org.junit.Test

import org.junit.Assert.*

class TetrisTest {
    @Test
    fun field_init_isCorrect() {
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
    fun field_fix_isCorrect() {
        val field = Field(4, 6)
        val block = Block1(field)
        block.moveTo(1, 5)
        block.fixToField()
        assertEquals(field.toString(), """
            |    |
            |    |
            |    |
            |    |
            |    |
            | *  |
        """.trimIndent())
    }

    @Test
    fun block_moveToRightEnd_isCorrect() {
        val field = Field(4, 6)
        val block = Block1(field)
        assertEquals(true, block.moveToRight())
        assertEquals(1, block.x)
        assertEquals(0, block.y)
        assertEquals(true, block.moveToRight())
        assertEquals(2, block.x)
        assertEquals(0, block.y)
        assertEquals(true, block.moveToRight())
        assertEquals(3, block.x)
        assertEquals(0, block.y)
        assertEquals(false, block.moveToRight())
        assertEquals(3, block.x)
        assertEquals(0, block.y)
    }

    @Test
    fun block_moveToRightBlock_isCorrect() {
        val field = with(Field(4, 6)) {
            val block = Block1(this)
            while (block.moveToRight()) {}
            block.fixToField()
            this
        }
        val block = Block1(field)
        assertEquals(true, block.moveToRight())
        assertEquals(1, block.x)
        assertEquals(0, block.y)
        assertEquals(true, block.moveToRight())
        assertEquals(2, block.x)
        assertEquals(0, block.y)
        assertEquals(false, block.moveToRight())
        assertEquals(2, block.x)
        assertEquals(0, block.y)
    }

}
