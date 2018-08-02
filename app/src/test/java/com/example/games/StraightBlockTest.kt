package com.example.games

import com.example.games.model.Field
import com.example.games.model.blocks.makeSingleBlock
import com.example.games.model.blocks.makeStraightBlock
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class StraightBlockTest {
    class VerticalStraightBlockTest {
        private val _width = 3
        private val _height = 6
        private var _field: Field = Field(_width, _height)

        @Before
        fun before() {
            _field = Field(_width, _height)
        }

        @Test
        fun block_init_isCorrect() {
            makeStraightBlock(_field, 1, 1).fixToField()
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
                makeSingleBlock(_field, 2, 0).fixToField()
                makeSingleBlock(_field, 2, 5).fixToField()
                assertEquals("""
            |  *|
            |   |
            |   |
            |   |
            |   |
            |  *|
            """.trimIndent(), _field.toString())

                val block = makeStraightBlock(_field, 1, 1)
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
                val block = makeStraightBlock(_field, 2, 1)
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
                makeSingleBlock(_field, 2, 1).fixToField()
                assertEquals("""
            |   |
            |  *|
            |   |
            |   |
            |   |
            |   |
            """.trimIndent(), _field.toString())

                val block = makeStraightBlock(_field, 1, 1)
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
                makeSingleBlock(_field, 2, 2).fixToField()
                assertEquals("""
            |   |
            |   |
            |  *|
            |   |
            |   |
            |   |
            """.trimIndent(), _field.toString())

                val block = makeStraightBlock(_field, 1, 1)
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
                makeSingleBlock(_field, 2, 3).fixToField()
                assertEquals("""
            |   |
            |   |
            |   |
            |  *|
            |   |
            |   |
            """.trimIndent(), _field.toString())

                val block = makeStraightBlock(_field, 1, 1)
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
                makeSingleBlock(_field, 2, 4).fixToField()
                assertEquals("""
            |   |
            |   |
            |   |
            |   |
            |  *|
            |   |
            """.trimIndent(), _field.toString())

                val block = makeStraightBlock(_field, 1, 1)
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
                makeSingleBlock(_field, 0, 0).fixToField()
                makeSingleBlock(_field, 0, 5).fixToField()
                assertEquals("""
            |*  |
            |   |
            |   |
            |   |
            |   |
            |*  |
            """.trimIndent(), _field.toString())

                val block = makeStraightBlock(_field, 1, 1)
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
                val block = makeStraightBlock(_field, 0, 1)
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
                makeSingleBlock(_field, 0, 1).fixToField()
                assertEquals("""
            |   |
            |*  |
            |   |
            |   |
            |   |
            |   |
            """.trimIndent(), _field.toString())

                val block = makeStraightBlock(_field, 1, 1)
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
                makeSingleBlock(_field, 0, 2).fixToField()
                assertEquals("""
            |   |
            |   |
            |*  |
            |   |
            |   |
            |   |
            """.trimIndent(), _field.toString())

                val block = makeStraightBlock(_field, 1, 1)
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
                makeSingleBlock(_field, 0, 3).fixToField()
                assertEquals("""
            |   |
            |   |
            |   |
            |*  |
            |   |
            |   |
            """.trimIndent(), _field.toString())

                val block = makeStraightBlock(_field, 1, 1)
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
                makeSingleBlock(_field, 0, 4).fixToField()
                assertEquals("""
            |   |
            |   |
            |   |
            |   |
            |*  |
            |   |
            """.trimIndent(), _field.toString())

                val block = makeStraightBlock(_field, 1, 1)
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
                makeSingleBlock(_field, 0, 5).fixToField()
                makeSingleBlock(_field, 0, 6).fixToField()
                makeSingleBlock(_field, 0, 7).fixToField()
                makeSingleBlock(_field, 0, 8).fixToField()
                makeSingleBlock(_field, 2, 5).fixToField()
                makeSingleBlock(_field, 2, 6).fixToField()
                makeSingleBlock(_field, 2, 7).fixToField()
                makeSingleBlock(_field, 2, 8).fixToField()
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

                val block = makeStraightBlock(_field, 1, 1)
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
                val block = makeStraightBlock(_field, 1, 5)
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
                makeSingleBlock(_field, 1, 5).fixToField()
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

                val block = makeStraightBlock(_field, 1, 1)
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

        class RotateRightTest {
            private val _width = 4
            private val _height = 4
            private var _field: Field = Field(_width, _height)

            @Before
            fun before() {
                _field = Field(_width, _height)
            }

            @Test
            fun canRotate() {
                makeSingleBlock(_field, 0, 0).fixToField()
                makeSingleBlock(_field, 0, 2).fixToField()
                makeSingleBlock(_field, 0, 3).fixToField()
                makeSingleBlock(_field, 2, 0).fixToField()
                makeSingleBlock(_field, 2, 2).fixToField()
                makeSingleBlock(_field, 2, 3).fixToField()
                makeSingleBlock(_field, 3, 0).fixToField()
                makeSingleBlock(_field, 3, 2).fixToField()
                makeSingleBlock(_field, 3, 3).fixToField()
                assertEquals("""
            |* **|
            |    |
            |* **|
            |* **|
            """.trimIndent(), _field.toString())

                val block = makeStraightBlock(_field, 1, 0)
                assertEquals(true, block.rotateRight())

                block.fixToField()
                assertEquals("""
            |* **|
            |****|
            |* **|
            |* **|
            """.trimIndent(), _field.toString())
            }

            @Test
            fun cannotMove_exceedWall_1() {
                val block = makeStraightBlock(_field, 2, 0)
                assertEquals(false, block.rotateRight())

                block.fixToField()
                assertEquals("""
            |  * |
            |  * |
            |  * |
            |  * |
            """.trimIndent(), _field.toString())
            }

            @Test
            fun cannotMove_exceedWall_2() {
                val block = makeStraightBlock(_field, 0, 0)
                assertEquals(false, block.rotateRight())

                block.fixToField()
                assertEquals("""
            |*   |
            |*   |
            |*   |
            |*   |
            """.trimIndent(), _field.toString())
            }

            @Test
            fun cannotRotate_exceedBlock_1() {
                makeSingleBlock(_field, 0, 1).fixToField()
                assertEquals("""
            |    |
            |*   |
            |    |
            |    |
            """.trimIndent(), _field.toString())

                val block = makeStraightBlock(_field, 1, 0)
                assertEquals(false, block.rotateRight())

                block.fixToField()
                assertEquals("""
            | *  |
            |**  |
            | *  |
            | *  |
            """.trimIndent(), _field.toString())
            }

            @Test
            fun cannotRotate_exceedBlock_2() {
                makeSingleBlock(_field, 2, 1).fixToField()
                assertEquals("""
            |    |
            |  * |
            |    |
            |    |
            """.trimIndent(), _field.toString())

                val block = makeStraightBlock(_field, 1, 0)
                assertEquals(false, block.rotateRight())

                block.fixToField()
                assertEquals("""
            | *  |
            | ** |
            | *  |
            | *  |
            """.trimIndent(), _field.toString())
            }

            @Test
            fun cannotRotate_exceedBlock_3() {
                makeSingleBlock(_field, 3, 1).fixToField()
                assertEquals("""
            |    |
            |   *|
            |    |
            |    |
            """.trimIndent(), _field.toString())

                val block = makeStraightBlock(_field, 1, 0)
                assertEquals(false, block.rotateRight())

                block.fixToField()
                assertEquals("""
            | *  |
            | * *|
            | *  |
            | *  |
            """.trimIndent(), _field.toString())
            }
        }

        class RotateLeftTest {
            private val _width = 4
            private val _height = 4
            private var _field: Field = Field(_width, _height)

            @Before
            fun before() {
                _field = Field(_width, _height)
            }

            @Test
            fun canRotate() {
                makeSingleBlock(_field, 0, 0).fixToField()
                makeSingleBlock(_field, 0, 2).fixToField()
                makeSingleBlock(_field, 0, 3).fixToField()
                makeSingleBlock(_field, 2, 0).fixToField()
                makeSingleBlock(_field, 2, 2).fixToField()
                makeSingleBlock(_field, 2, 3).fixToField()
                makeSingleBlock(_field, 3, 0).fixToField()
                makeSingleBlock(_field, 3, 2).fixToField()
                makeSingleBlock(_field, 3, 3).fixToField()
                assertEquals("""
            |* **|
            |    |
            |* **|
            |* **|
            """.trimIndent(), _field.toString())

                val block = makeStraightBlock(_field, 1, 0)
                assertEquals(true, block.rotateLeft())

                block.fixToField()
                assertEquals("""
            |* **|
            |****|
            |* **|
            |* **|
            """.trimIndent(), _field.toString())
            }

            @Test
            fun cannotMove_exceedWall_1() {
                val block = makeStraightBlock(_field, 2, 0)
                assertEquals(false, block.rotateLeft())

                block.fixToField()
                assertEquals("""
            |  * |
            |  * |
            |  * |
            |  * |
            """.trimIndent(), _field.toString())
            }

            @Test
            fun cannotMove_exceedWall_2() {
                val block = makeStraightBlock(_field, 0, 0)
                assertEquals(false, block.rotateLeft())

                block.fixToField()
                assertEquals("""
            |*   |
            |*   |
            |*   |
            |*   |
            """.trimIndent(), _field.toString())
            }

            @Test
            fun cannotRotate_exceedBlock_1() {
                makeSingleBlock(_field, 0, 1).fixToField()
                assertEquals("""
            |    |
            |*   |
            |    |
            |    |
            """.trimIndent(), _field.toString())

                val block = makeStraightBlock(_field, 1, 0)
                assertEquals(false, block.rotateLeft())

                block.fixToField()
                assertEquals("""
            | *  |
            |**  |
            | *  |
            | *  |
            """.trimIndent(), _field.toString())
            }

            @Test
            fun cannotRotate_exceedBlock_2() {
                makeSingleBlock(_field, 2, 1).fixToField()
                assertEquals("""
            |    |
            |  * |
            |    |
            |    |
            """.trimIndent(), _field.toString())

                val block = makeStraightBlock(_field, 1, 0)
                assertEquals(false, block.rotateLeft())

                block.fixToField()
                assertEquals("""
            | *  |
            | ** |
            | *  |
            | *  |
            """.trimIndent(), _field.toString())
            }

            @Test
            fun cannotRotate_exceedBlock_3() {
                makeSingleBlock(_field, 3, 1).fixToField()
                assertEquals("""
            |    |
            |   *|
            |    |
            |    |
            """.trimIndent(), _field.toString())

                val block = makeStraightBlock(_field, 1, 0)
                assertEquals(false, block.rotateLeft())

                block.fixToField()
                assertEquals("""
            | *  |
            | * *|
            | *  |
            | *  |
            """.trimIndent(), _field.toString())
            }
        }
    }

    class HorizontalStraightBlockTest {
        private val _width = 3
        private val _height = 6
        private var _field = Field(_width, _height)
        private var _block = makeStraightBlock(_field)

        @Before
        fun before() {
            _field = Field(_width, _height)
            _block = makeStraightBlock(_field)
        }

        @Test
        fun block_init_isCorrect() {
            val block = makeStraightBlock(_field, 1, 1)
            block.rotateRight()
            block.fixToField()
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
                makeSingleBlock(_field, 2, 0).fixToField()
                makeSingleBlock(_field, 2, 5).fixToField()
                assertEquals("""
            |  *|
            |   |
            |   |
            |   |
            |   |
            |  *|
            """.trimIndent(), _field.toString())

                val block = makeStraightBlock(_field, 1, 1)
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
                val block = makeStraightBlock(_field, 2, 1)
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
                makeSingleBlock(_field, 2, 1).fixToField()
                assertEquals("""
            |   |
            |  *|
            |   |
            |   |
            |   |
            |   |
            """.trimIndent(), _field.toString())

                val block = makeStraightBlock(_field, 1, 1)
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
                makeSingleBlock(_field, 2, 2).fixToField()
                assertEquals("""
            |   |
            |   |
            |  *|
            |   |
            |   |
            |   |
            """.trimIndent(), _field.toString())

                val block = makeStraightBlock(_field, 1, 1)
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
                makeSingleBlock(_field, 2, 3).fixToField()
                assertEquals("""
            |   |
            |   |
            |   |
            |  *|
            |   |
            |   |
            """.trimIndent(), _field.toString())

                val block = makeStraightBlock(_field, 1, 1)
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
                makeSingleBlock(_field, 2, 4).fixToField()
                assertEquals("""
            |   |
            |   |
            |   |
            |   |
            |  *|
            |   |
            """.trimIndent(), _field.toString())

                val block = makeStraightBlock(_field, 1, 1)
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
                makeSingleBlock(_field, 0, 0).fixToField()
                makeSingleBlock(_field, 0, 5).fixToField()
                assertEquals("""
            |*  |
            |   |
            |   |
            |   |
            |   |
            |*  |
            """.trimIndent(), _field.toString())

                val block = makeStraightBlock(_field, 1, 1)
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
                val block = makeStraightBlock(_field, 0, 1)
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
                makeSingleBlock(_field, 0, 1).fixToField()
                assertEquals("""
            |   |
            |*  |
            |   |
            |   |
            |   |
            |   |
            """.trimIndent(), _field.toString())

                val block = makeStraightBlock(_field, 1, 1)
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
                makeSingleBlock(_field, 0, 2).fixToField()
                assertEquals("""
            |   |
            |   |
            |*  |
            |   |
            |   |
            |   |
            """.trimIndent(), _field.toString())

                val block = makeStraightBlock(_field, 1, 1)
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
                makeSingleBlock(_field, 0, 3).fixToField()
                assertEquals("""
            |   |
            |   |
            |   |
            |*  |
            |   |
            |   |
            """.trimIndent(), _field.toString())

                val block = makeStraightBlock(_field, 1, 1)
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
                makeSingleBlock(_field, 0, 4).fixToField()
                assertEquals("""
            |   |
            |   |
            |   |
            |   |
            |*  |
            |   |
            """.trimIndent(), _field.toString())

                val block = makeStraightBlock(_field, 1, 1)
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
                makeSingleBlock(_field, 0, 5).fixToField()
                makeSingleBlock(_field, 0, 6).fixToField()
                makeSingleBlock(_field, 0, 7).fixToField()
                makeSingleBlock(_field, 0, 8).fixToField()
                makeSingleBlock(_field, 2, 5).fixToField()
                makeSingleBlock(_field, 2, 6).fixToField()
                makeSingleBlock(_field, 2, 7).fixToField()
                makeSingleBlock(_field, 2, 8).fixToField()
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

                val block = makeStraightBlock(_field, 1, 1)
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
                val block = makeStraightBlock(_field, 1, 5)
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
                makeSingleBlock(_field, 1, 5).fixToField()
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

                val block = makeStraightBlock(_field, 1, 1)
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

        class RotateRightTest {
            private val _width = 4
            private val _height = 4
            private var _field: Field = Field(_width, _height)

            @Before
            fun before() {
                _field = Field(_width, _height)
            }

            @Test
            fun canRotate() {
                makeSingleBlock(_field, 0, 0).fixToField()
                makeSingleBlock(_field, 0, 2).fixToField()
                makeSingleBlock(_field, 0, 3).fixToField()
                makeSingleBlock(_field, 2, 0).fixToField()
                makeSingleBlock(_field, 2, 2).fixToField()
                makeSingleBlock(_field, 2, 3).fixToField()
                makeSingleBlock(_field, 3, 0).fixToField()
                makeSingleBlock(_field, 3, 2).fixToField()
                makeSingleBlock(_field, 3, 3).fixToField()
                assertEquals("""
            |* **|
            |    |
            |* **|
            |* **|
            """.trimIndent(), _field.toString())

                val block = makeStraightBlock(_field, 1, 0)
                assertEquals(true, block.rotateRight())

                block.fixToField()
                assertEquals("""
            |* **|
            |****|
            |* **|
            |* **|
            """.trimIndent(), _field.toString())
            }

            @Test
            fun cannotMove_exceedWall_1() {
                val block = makeStraightBlock(_field, 2, 0)
                assertEquals(false, block.rotateRight())

                block.fixToField()
                assertEquals("""
            |  * |
            |  * |
            |  * |
            |  * |
            """.trimIndent(), _field.toString())
            }

            @Test
            fun cannotMove_exceedWall_2() {
                val block = makeStraightBlock(_field, 0, 0)
                assertEquals(false, block.rotateRight())

                block.fixToField()
                assertEquals("""
            |*   |
            |*   |
            |*   |
            |*   |
            """.trimIndent(), _field.toString())
            }

            @Test
            fun cannotRotate_exceedBlock_1() {
                makeSingleBlock(_field, 0, 1).fixToField()
                assertEquals("""
            |    |
            |*   |
            |    |
            |    |
            """.trimIndent(), _field.toString())

                val block = makeStraightBlock(_field, 1, 0)
                assertEquals(false, block.rotateRight())

                block.fixToField()
                assertEquals("""
            | *  |
            |**  |
            | *  |
            | *  |
            """.trimIndent(), _field.toString())
            }

            @Test
            fun cannotRotate_exceedBlock_2() {
                makeSingleBlock(_field, 2, 1).fixToField()
                assertEquals("""
            |    |
            |  * |
            |    |
            |    |
            """.trimIndent(), _field.toString())

                val block = makeStraightBlock(_field, 1, 0)
                assertEquals(false, block.rotateRight())

                block.fixToField()
                assertEquals("""
            | *  |
            | ** |
            | *  |
            | *  |
            """.trimIndent(), _field.toString())
            }

            @Test
            fun cannotRotate_exceedBlock_3() {
                makeSingleBlock(_field, 3, 1).fixToField()
                assertEquals("""
            |    |
            |   *|
            |    |
            |    |
            """.trimIndent(), _field.toString())

                val block = makeStraightBlock(_field, 1, 0)
                assertEquals(false, block.rotateRight())

                block.fixToField()
                assertEquals("""
            | *  |
            | * *|
            | *  |
            | *  |
            """.trimIndent(), _field.toString())
            }
        }

        class RotateLeftTest {
            private val _width = 4
            private val _height = 4
            private var _field: Field = Field(_width, _height)

            @Before
            fun before() {
                _field = Field(_width, _height)
            }

            @Test
            fun canRotate() {
                makeSingleBlock(_field, 0, 0).fixToField()
                makeSingleBlock(_field, 0, 2).fixToField()
                makeSingleBlock(_field, 0, 3).fixToField()
                makeSingleBlock(_field, 2, 0).fixToField()
                makeSingleBlock(_field, 2, 2).fixToField()
                makeSingleBlock(_field, 2, 3).fixToField()
                makeSingleBlock(_field, 3, 0).fixToField()
                makeSingleBlock(_field, 3, 2).fixToField()
                makeSingleBlock(_field, 3, 3).fixToField()
                assertEquals("""
            |* **|
            |    |
            |* **|
            |* **|
            """.trimIndent(), _field.toString())

                val block = makeStraightBlock(_field, 1, 0)
                assertEquals(true, block.rotateLeft())

                block.fixToField()
                assertEquals("""
            |* **|
            |****|
            |* **|
            |* **|
            """.trimIndent(), _field.toString())
            }

            @Test
            fun cannotMove_exceedWall_1() {
                val block = makeStraightBlock(_field, 2, 0)
                assertEquals(false, block.rotateLeft())

                block.fixToField()
                assertEquals("""
            |  * |
            |  * |
            |  * |
            |  * |
            """.trimIndent(), _field.toString())
            }

            @Test
            fun cannotMove_exceedWall_2() {
                val block = makeStraightBlock(_field, 0, 0)
                assertEquals(false, block.rotateLeft())

                block.fixToField()
                assertEquals("""
            |*   |
            |*   |
            |*   |
            |*   |
            """.trimIndent(), _field.toString())
            }

            @Test
            fun cannotRotate_exceedBlock_1() {
                makeSingleBlock(_field, 0, 1).fixToField()
                assertEquals("""
            |    |
            |*   |
            |    |
            |    |
            """.trimIndent(), _field.toString())

                val block = makeStraightBlock(_field, 1, 0)
                assertEquals(false, block.rotateLeft())

                block.fixToField()
                assertEquals("""
            | *  |
            |**  |
            | *  |
            | *  |
            """.trimIndent(), _field.toString())
            }

            @Test
            fun cannotRotate_exceedBlock_2() {
                makeSingleBlock(_field, 2, 1).fixToField()
                assertEquals("""
            |    |
            |  * |
            |    |
            |    |
            """.trimIndent(), _field.toString())

                val block = makeStraightBlock(_field, 1, 0)
                assertEquals(false, block.rotateLeft())

                block.fixToField()
                assertEquals("""
            | *  |
            | ** |
            | *  |
            | *  |
            """.trimIndent(), _field.toString())
            }

            @Test
            fun cannotRotate_exceedBlock_3() {
                makeSingleBlock(_field, 3, 1).fixToField()
                assertEquals("""
            |    |
            |   *|
            |    |
            |    |
            """.trimIndent(), _field.toString())

                val block = makeStraightBlock(_field, 1, 0)
                assertEquals(false, block.rotateLeft())

                block.fixToField()
                assertEquals("""
            | *  |
            | * *|
            | *  |
            | *  |
            """.trimIndent(), _field.toString())
            }
        }
    }
}
