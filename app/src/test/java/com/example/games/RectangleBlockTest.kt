package com.example.games

import com.example.games.model.TetrisField
import com.example.games.model.blocks.makeRectangleBlock
import com.example.games.model.blocks.makeSingleBlock
import org.junit.Assert.assertEquals
import org.junit.Test

class makeRectangleBlockTest {
    private val _width = 4
    private val _height = 4
    private var _field: TetrisField = TetrisField(_width, _height)

    @Test
    fun block_init_isCorrect() {
        makeRectangleBlock(_field, 1, 1).fixToField()
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
        private var _field: TetrisField = TetrisField(_width, _height)

        @Test
        fun canMove() {
            makeSingleBlock(_field, 3, 0).fixToField()
            makeSingleBlock(_field, 3, 3).fixToField()
            makeSingleBlock(_field, 4, 0).fixToField()
            makeSingleBlock(_field, 4, 3).fixToField()
            assertEquals("""
            |   **|
            |     |
            |     |
            |   **|
            """.trimIndent(), _field.toString())

            val block = makeRectangleBlock(_field, 1, 1)
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
            val block = makeRectangleBlock(_field, 3, 1)
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
            makeSingleBlock(_field, 3, 1).fixToField()
            assertEquals("""
            |     |
            |   * |
            |     |
            |     |
            """.trimIndent(), _field.toString())

            val block = makeRectangleBlock(_field, 1, 1)
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
            makeSingleBlock(_field, 3, 2).fixToField()
            assertEquals("""
            |     |
            |     |
            |   * |
            |     |
            """.trimIndent(), _field.toString())

            val block = makeRectangleBlock(_field, 1, 1)
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
        private var _field: TetrisField = TetrisField(_width, _height)

        @Test
        fun canMove() {
            makeSingleBlock(_field, 0, 0).fixToField()
            makeSingleBlock(_field, 0, 3).fixToField()
            makeSingleBlock(_field, 1, 0).fixToField()
            makeSingleBlock(_field, 1, 3).fixToField()
            assertEquals("""
            |**   |
            |     |
            |     |
            |**   |
            """.trimIndent(), _field.toString())

            val block = makeRectangleBlock(_field, 2, 1)
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
            val block = makeRectangleBlock(_field, 0, 1)
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
            makeSingleBlock(_field, 0, 1).fixToField()
            assertEquals("""
            |     |
            |*    |
            |     |
            |     |
            """.trimIndent(), _field.toString())

            val block = makeRectangleBlock(_field, 1, 1)
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
            makeSingleBlock(_field, 0, 2).fixToField()
            assertEquals("""
            |     |
            |     |
            |*    |
            |     |
            """.trimIndent(), _field.toString())

            val block = makeRectangleBlock(_field, 1, 1)
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
            val field = TetrisField(4, 4)
            val block = makeRectangleBlock(field, 1, 1)
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
            val field = TetrisField(4, 4)
            val block = makeRectangleBlock(field, 1, 2)
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
            val field = TetrisField(4, 4)
            makeSingleBlock(field, 1, 3).fixToField()
            assertEquals("""
            |    |
            |    |
            |    |
            | *  |
            """.trimIndent(), field.toString())

            val block = makeRectangleBlock(field, 1, 1)
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
            val field = TetrisField(4, 4)
            makeSingleBlock(field, 2, 3).fixToField()
            assertEquals("""
            |    |
            |    |
            |    |
            |  * |
            """.trimIndent(), field.toString())

            val block = makeRectangleBlock(field, 1, 1)
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

    class RotateRightTest {
        @Test
        fun canRotate() {
            val field = TetrisField(4, 4)
            val block = makeRectangleBlock(field, 1, 1)
            assertEquals(true, block.rotateRight())

            block.fixToField()
            assertEquals("""
            |    |
            | ** |
            | ** |
            |    |
            """.trimIndent(), field.toString())
        }
    }

    class RotateLeftTest {
        @Test
        fun canRotate() {
            val field = TetrisField(4, 4)
            val block = makeRectangleBlock(field, 1, 1)
            assertEquals(true, block.rotateLeft())

            block.fixToField()
            assertEquals("""
            |    |
            | ** |
            | ** |
            |    |
            """.trimIndent(), field.toString())
        }
    }
}
