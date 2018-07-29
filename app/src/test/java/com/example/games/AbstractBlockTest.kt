package com.example.games

import com.example.games.model.Field
import com.example.games.model.blocks.RectangleBlock
import com.example.games.model.blocks.SingleBlock
import org.junit.Assert.assertEquals
import org.junit.Test

class AbstractBlockTest {
    private val _width = 4
    private val _height = 4
    private var _field: Field = Field(_width, _height)

    @Test
    fun block_init_isCorrect() {
        RectangleBlock(_field, 1, 1).fixToField()
        assertEquals("""
            |    |
            | ** |
            | ** |
            |    |
            """.trimIndent(), _field.toString())
    }

    class MoveToRightTest {
        private val _width = 5
        private val _height = 4
        private var _field: Field = Field(_width, _height)

        @Test
        fun canMove() {
            SingleBlock(_field, 3, 0).fixToField()
            SingleBlock(_field, 3, 3).fixToField()
            SingleBlock(_field, 4, 0).fixToField()
            SingleBlock(_field, 4, 3).fixToField()
            assertEquals("""
            |   **|
            |     |
            |     |
            |   **|
            """.trimIndent(), _field.toString())

            val block = RectangleBlock(_field, 1, 1)
            assertEquals(true, block.moveToRight())
            assertEquals(true, block.moveToRight())

            block.fixToField()
            assertEquals("""
            |   **|
            |   **|
            |   **|
            |   **|
            """.trimIndent(), _field.toString())
        }

        @Test
        fun cannotMove_exceedWall() {
            val block = RectangleBlock(_field, 3, 1)
            assertEquals(false, block.moveToRight())

            block.fixToField()
            assertEquals("""
            |     |
            |   **|
            |   **|
            |     |
            """.trimIndent(), _field.toString())
        }

        @Test
        fun cannotMove_exceedBlock_1() {
            SingleBlock(_field, 3, 1).fixToField()
            assertEquals("""
            |     |
            |   * |
            |     |
            |     |
            """.trimIndent(), _field.toString())

            val block = RectangleBlock(_field, 1, 1)
            assertEquals(false, block.moveToRight())

            block.fixToField()
            assertEquals("""
            |     |
            | *** |
            | **  |
            |     |
            """.trimIndent(), _field.toString())
        }

        @Test
        fun cannotMove_exceedBlock_2() {
            SingleBlock(_field, 3, 2).fixToField()
            assertEquals("""
            |     |
            |     |
            |   * |
            |     |
            """.trimIndent(), _field.toString())

            val block = RectangleBlock(_field, 1, 1)
            assertEquals(false, block.moveToRight())

            block.fixToField()
            assertEquals("""
            |     |
            | **  |
            | *** |
            |     |
            """.trimIndent(), _field.toString())
        }
    }

    class MoveToLeftTest {
        private val _width = 5
        private val _height = 4
        private var _field: Field = Field(_width, _height)

        @Test
        fun canMove() {
            SingleBlock(_field, 0, 0).fixToField()
            SingleBlock(_field, 0, 3).fixToField()
            SingleBlock(_field, 1, 0).fixToField()
            SingleBlock(_field, 1, 3).fixToField()
            assertEquals("""
            |**   |
            |     |
            |     |
            |**   |
            """.trimIndent(), _field.toString())

            val block = RectangleBlock(_field, 2, 1)
            assertEquals(true, block.moveToLeft())
            assertEquals(true, block.moveToLeft())

            block.fixToField()
            assertEquals("""
            |**   |
            |**   |
            |**   |
            |**   |
            """.trimIndent(), _field.toString())
        }

        @Test
        fun cannotMove_exceedWall() {
            val block = RectangleBlock(_field, 0, 1)
            assertEquals(false, block.moveToLeft())

            block.fixToField()
            assertEquals("""
            |     |
            |**   |
            |**   |
            |     |
            """.trimIndent(), _field.toString())
        }

        @Test
        fun cannotMove_exceedBlock_1() {
            SingleBlock(_field, 0, 1).fixToField()
            assertEquals("""
            |     |
            |*    |
            |     |
            |     |
            """.trimIndent(), _field.toString())

            val block = RectangleBlock(_field, 1, 1)
            assertEquals(false, block.moveToLeft())

            block.fixToField()
            assertEquals("""
            |     |
            |***  |
            | **  |
            |     |
            """.trimIndent(), _field.toString())
        }

        @Test
        fun cannotMove_exceedBlock_2() {
            SingleBlock(_field, 0, 2).fixToField()
            assertEquals("""
            |     |
            |     |
            |*    |
            |     |
            """.trimIndent(), _field.toString())

            val block = RectangleBlock(_field, 1, 1)
            assertEquals(false, block.moveToLeft())

            block.fixToField()
            assertEquals("""
            |     |
            | **  |
            |***  |
            |     |
            """.trimIndent(), _field.toString())
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
            SingleBlock(field, 1, 3).fixToField()
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
            SingleBlock(field, 2, 3).fixToField()
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
