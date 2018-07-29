package com.example.games

import com.example.games.model.blocks.MinimumBlock
import com.example.games.model.Field;
import com.example.games.model.blocks.Position
import com.example.games.model.blocks.RectangleBlock
import org.junit.Test

import org.junit.Assert.*

class RectangleBlockTest {
    @Test
    fun block_init_isCorrect() {
        val field = Field(4, 4)
        RectangleBlock(field, 1, 1).fixToField()
        assertEquals("""
            |    |
            | ** |
            | ** |
            |    |
            """.trimIndent(), field.toString())
    }

    class MoveToRightTest {
        @Test
        fun canMove() {
            val field = Field(4, 4)
            val block = RectangleBlock(field, 1, 1)
            assertEquals(true, block.moveToRight())

            block.fixToField()
            assertEquals("""
            |    |
            |  **|
            |  **|
            |    |
            """.trimIndent(), field.toString())
        }

        @Test
        fun cannotMove_exceedWall() {
            val field = Field(4, 4)
            val block = RectangleBlock(field, 2, 1)
            assertEquals(false, block.moveToRight())

            block.fixToField()
            assertEquals("""
            |    |
            |  **|
            |  **|
            |    |
            """.trimIndent(), field.toString())
        }

        @Test
        fun cannotMove_exceedBlock_1() {
            val field = Field(4, 4)
            MinimumBlock(field, 3, 1).fixToField()
            assertEquals("""
            |    |
            |   *|
            |    |
            |    |
            """.trimIndent(), field.toString())

            val block = RectangleBlock(field, 1, 1)
            assertEquals(false, block.moveToRight())

            block.fixToField()
            assertEquals("""
            |    |
            | ***|
            | ** |
            |    |
            """.trimIndent(), field.toString())
        }

        @Test
        fun cannotMove_exceedBlock_2() {
            val field = Field(4, 4)
            MinimumBlock(field, 3, 2).fixToField()
            assertEquals("""
            |    |
            |    |
            |   *|
            |    |
            """.trimIndent(), field.toString())

            val block = RectangleBlock(field, 1, 1)
            assertEquals(false, block.moveToRight())

            block.fixToField()
            assertEquals("""
            |    |
            | ** |
            | ***|
            |    |
            """.trimIndent(), field.toString())
        }
    }

    class MoveToLeftTest {
        @Test
        fun canMove() {
            val field = Field(4, 4)
            val block = RectangleBlock(field, 1, 1)
            assertEquals(true, block.moveToLeft())

            block.fixToField()
            assertEquals("""
            |    |
            |**  |
            |**  |
            |    |
            """.trimIndent(), field.toString())
        }

        @Test
        fun cannotMove_exceedWall() {
            val field = Field(4, 4)
            val block = RectangleBlock(field, 0, 1)
            assertEquals(false, block.moveToLeft())

            block.fixToField()
            assertEquals("""
            |    |
            |**  |
            |**  |
            |    |
            """.trimIndent(), field.toString())
        }

        @Test
        fun cannotMove_exceedBlock_1() {
            val field = Field(4, 4)
            MinimumBlock(field, 0, 1).fixToField()
            assertEquals("""
            |    |
            |*   |
            |    |
            |    |
            """.trimIndent(), field.toString())

            val block = RectangleBlock(field, 1, 1)
            assertEquals(false, block.moveToLeft())

            block.fixToField()
            assertEquals("""
            |    |
            |*** |
            | ** |
            |    |
            """.trimIndent(), field.toString())
        }

        @Test
        fun cannotMove_exceedBlock_2() {
            val field = Field(4, 4)
            MinimumBlock(field, 0, 2).fixToField()
            assertEquals("""
            |    |
            |    |
            |*   |
            |    |
            """.trimIndent(), field.toString())

            val block = RectangleBlock(field, 1, 1)
            assertEquals(false, block.moveToLeft())

            block.fixToField()
            assertEquals("""
            |    |
            | ** |
            |*** |
            |    |
            """.trimIndent(), field.toString())
        }
    }

    class MoveToDownTest {
        @Test
        fun canMove() {
            val field = Field(4, 4)
            val block = RectangleBlock(field, 1, 1)
            assertEquals(true, block.moveToDown())

            block.fixToField()
            assertEquals("""
            |    |
            |    |
            | ** |
            | ** |
            """.trimIndent(), field.toString())
        }

        @Test
        fun cannotMove_exceedGround() {
            val field = Field(4, 4)
            val block = RectangleBlock(field, 1, 2)
            assertEquals(false, block.moveToDown())

            block.fixToField()
            assertEquals("""
            |    |
            |    |
            | ** |
            | ** |
            """.trimIndent(), field.toString())
        }

        @Test
        fun cannotMove_exceedBlock_1() {
            val field = Field(4, 4)
            MinimumBlock(field, 1, 3).fixToField()
            assertEquals("""
            |    |
            |    |
            |    |
            | *  |
            """.trimIndent(), field.toString())

            val block = RectangleBlock(field, 1, 1)
            assertEquals(false, block.moveToDown())

            block.fixToField()
            assertEquals("""
            |    |
            | ** |
            | ** |
            | *  |
            """.trimIndent(), field.toString())
        }

        @Test
        fun cannotMove_exceedBlock_2() {
            val field = Field(4, 4)
            MinimumBlock(field, 2, 3).fixToField()
            assertEquals("""
            |    |
            |    |
            |    |
            |  * |
            """.trimIndent(), field.toString())

            val block = RectangleBlock(field, 1, 1)
            assertEquals(false, block.moveToDown())

            block.fixToField()
            assertEquals("""
            |    |
            | ** |
            | ** |
            |  * |
            """.trimIndent(), field.toString())
        }
    }


}
