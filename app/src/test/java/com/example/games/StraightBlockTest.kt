package com.example.games

import com.example.games.model.Field
import com.example.games.model.blocks.SingleBlock
import com.example.games.model.blocks.StraightBlock
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class StraightBlockTest {
    private val _width = 3
    private val _height = 6
    private var _field: Field = Field(_width, _height)

    @Before
    fun before() {
        _field = Field(_width, _height)
    }

    @Test
    fun block_init_isCorrect() {
        StraightBlock(_field, 1, 1).fixToField()
        assertEquals("""
            |   |
            | * |
            | * |
            | * |
            | * |
            |   |
            """.trimIndent(), _field.toString())
    }

    class MoveToRightTest {
        private val _width = 3
        private val _height = 6
        private var _field: Field = Field(_width, _height)

        @Before
        fun before() {
            _field = Field(_width, _height)
        }

        @Test
        fun canMove() {
            SingleBlock(_field, 2, 0).fixToField()
            SingleBlock(_field, 2, 5).fixToField()
            assertEquals("""
            |  *|
            |   |
            |   |
            |   |
            |   |
            |  *|
            """.trimIndent(), _field.toString())

            val block = StraightBlock(_field, 1, 1)
            assertEquals(true, block.moveToRight())

            block.fixToField()
            assertEquals("""
            |  *|
            |  *|
            |  *|
            |  *|
            |  *|
            |  *|
            """.trimIndent(), _field.toString())
        }

        @Test
        fun cannotMove_exceedWall() {
            val block = StraightBlock(_field, 2, 1)
            assertEquals(false, block.moveToRight())

            block.fixToField()
            assertEquals("""
            |   |
            |  *|
            |  *|
            |  *|
            |  *|
            |   |
            """.trimIndent(), _field.toString())
        }

        @Test
        fun cannotMove_exceedBlock_1() {
            SingleBlock(_field, 2, 1).fixToField()
            assertEquals("""
            |   |
            |  *|
            |   |
            |   |
            |   |
            |   |
            """.trimIndent(), _field.toString())

            val block = StraightBlock(_field, 1, 1)
            assertEquals(false, block.moveToRight())

            block.fixToField()
            assertEquals("""
            |   |
            | **|
            | * |
            | * |
            | * |
            |   |
            """.trimIndent(), _field.toString())
        }

        @Test
        fun cannotMove_exceedBlock_2() {
            SingleBlock(_field, 2, 2).fixToField()
            assertEquals("""
            |   |
            |   |
            |  *|
            |   |
            |   |
            |   |
            """.trimIndent(), _field.toString())

            val block = StraightBlock(_field, 1, 1)
            assertEquals(false, block.moveToRight())

            block.fixToField()
            assertEquals("""
            |   |
            | * |
            | **|
            | * |
            | * |
            |   |
            """.trimIndent(), _field.toString())
        }

        @Test
        fun cannotMove_exceedBlock_3() {
            SingleBlock(_field, 2, 3).fixToField()
            assertEquals("""
            |   |
            |   |
            |   |
            |  *|
            |   |
            |   |
            """.trimIndent(), _field.toString())

            val block = StraightBlock(_field, 1, 1)
            assertEquals(false, block.moveToRight())

            block.fixToField()
            assertEquals("""
            |   |
            | * |
            | * |
            | **|
            | * |
            |   |
            """.trimIndent(), _field.toString())
        }

        @Test
        fun cannotMove_exceedBlock_4() {
            SingleBlock(_field, 2, 4).fixToField()
            assertEquals("""
            |   |
            |   |
            |   |
            |   |
            |  *|
            |   |
            """.trimIndent(), _field.toString())

            val block = StraightBlock(_field, 1, 1)
            assertEquals(false, block.moveToRight())

            block.fixToField()
            assertEquals("""
            |   |
            | * |
            | * |
            | * |
            | **|
            |   |
            """.trimIndent(), _field.toString())
        }
    }

    class MoveToLeftTest {
        private val _width = 3
        private val _height = 6
        private var _field: Field = Field(_width, _height)

        @Before
        fun before() {
            _field = Field(_width, _height)
        }

        @Test
        fun canMove() {
            SingleBlock(_field, 0, 0).fixToField()
            SingleBlock(_field, 0, 5).fixToField()
            assertEquals("""
            |*  |
            |   |
            |   |
            |   |
            |   |
            |*  |
            """.trimIndent(), _field.toString())

            val block = StraightBlock(_field, 1, 1)
            assertEquals(true, block.moveToLeft())

            block.fixToField()
            assertEquals("""
            |*  |
            |*  |
            |*  |
            |*  |
            |*  |
            |*  |
            """.trimIndent(), _field.toString())
        }

        @Test
        fun cannotMove_exceedWall() {
            val block = StraightBlock(_field, 0, 1)
            assertEquals(false, block.moveToLeft())

            block.fixToField()
            assertEquals("""
            |   |
            |*  |
            |*  |
            |*  |
            |*  |
            |   |
            """.trimIndent(), _field.toString())
        }

        @Test
        fun cannotMove_exceedBlock_1() {
            SingleBlock(_field, 0, 1).fixToField()
            assertEquals("""
            |   |
            |*  |
            |   |
            |   |
            |   |
            |   |
            """.trimIndent(), _field.toString())

            val block = StraightBlock(_field, 1, 1)
            assertEquals(false, block.moveToLeft())

            block.fixToField()
            assertEquals("""
            |   |
            |** |
            | * |
            | * |
            | * |
            |   |
            """.trimIndent(), _field.toString())
        }

        @Test
        fun cannotMove_exceedBlock_2() {
            SingleBlock(_field, 0, 2).fixToField()
            assertEquals("""
            |   |
            |   |
            |*  |
            |   |
            |   |
            |   |
            """.trimIndent(), _field.toString())

            val block = StraightBlock(_field, 1, 1)
            assertEquals(false, block.moveToLeft())

            block.fixToField()
            assertEquals("""
            |   |
            | * |
            |** |
            | * |
            | * |
            |   |
            """.trimIndent(), _field.toString())
        }

        @Test
        fun cannotMove_exceedBlock_3() {
            SingleBlock(_field, 0, 3).fixToField()
            assertEquals("""
            |   |
            |   |
            |   |
            |*  |
            |   |
            |   |
            """.trimIndent(), _field.toString())

            val block = StraightBlock(_field, 1, 1)
            assertEquals(false, block.moveToLeft())

            block.fixToField()
            assertEquals("""
            |   |
            | * |
            | * |
            |** |
            | * |
            |   |
            """.trimIndent(), _field.toString())
        }

        @Test
        fun cannotMove_exceedBlock_4() {
            SingleBlock(_field, 0, 4).fixToField()
            assertEquals("""
            |   |
            |   |
            |   |
            |   |
            |*  |
            |   |
            """.trimIndent(), _field.toString())

            val block = StraightBlock(_field, 1, 1)
            assertEquals(false, block.moveToLeft())

            block.fixToField()
            assertEquals("""
            |   |
            | * |
            | * |
            | * |
            |** |
            |   |
            """.trimIndent(), _field.toString())
        }
    }

    class MoveToDownTest {
        private val _width = 3
        private val _height = 9
        private var _field: Field = Field(_width, _height)

        @Before
        fun before() {
            _field = Field(_width, _height)
        }

        @Test
        fun canMove() {
            SingleBlock(_field, 0, 5).fixToField()
            SingleBlock(_field, 0, 6).fixToField()
            SingleBlock(_field, 0, 7).fixToField()
            SingleBlock(_field, 0, 8).fixToField()
            SingleBlock(_field, 2, 5).fixToField()
            SingleBlock(_field, 2, 6).fixToField()
            SingleBlock(_field, 2, 7).fixToField()
            SingleBlock(_field, 2, 8).fixToField()
            assertEquals("""
            |   |
            |   |
            |   |
            |   |
            |   |
            |* *|
            |* *|
            |* *|
            |* *|
            """.trimIndent(), _field.toString())

            val block = StraightBlock(_field, 1, 1)
            assertEquals(true, block.moveToDown())
            assertEquals(true, block.moveToDown())
            assertEquals(true, block.moveToDown())
            assertEquals(true, block.moveToDown())

            block.fixToField()
            assertEquals("""
            |   |
            |   |
            |   |
            |   |
            |   |
            |***|
            |***|
            |***|
            |***|
            """.trimIndent(), _field.toString())
        }

        @Test
        fun cannotMove_exceedGround() {
            val block = StraightBlock(_field, 1, 5)
            assertEquals(false, block.moveToDown())

            block.fixToField()
            assertEquals("""
            |   |
            |   |
            |   |
            |   |
            |   |
            | * |
            | * |
            | * |
            | * |
            """.trimIndent(), _field.toString())
        }

        @Test
        fun cannotMove_exceedBlock_1() {
            SingleBlock(_field, 1, 5).fixToField()
            assertEquals("""
            |   |
            |   |
            |   |
            |   |
            |   |
            | * |
            |   |
            |   |
            |   |
            """.trimIndent(), _field.toString())

            val block = StraightBlock(_field, 1, 1)
            assertEquals(false, block.moveToDown())

            block.fixToField()
            assertEquals("""
            |   |
            | * |
            | * |
            | * |
            | * |
            | * |
            |   |
            |   |
            |   |
            """.trimIndent(), _field.toString())
        }
    }
}
