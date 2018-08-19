//package com.example.games
//
//import com.example.games.tetris.model.blocks.makeSingleBlock
//import com.example.games.tetris.model.TetrisField;
//import org.junit.Test
//
//import org.junit.Assert.*
//
//class TetrisModelTest {
//    @Test
//    fun field_init_isCorrect() {
//        val field = TetrisField(4, 6)
//        assertEquals("""
//            |    |
//            |    |
//            |    |
//            |    |
//            |    |
//            |    |
//            """.trimIndent(), field.toString())
//    }
//
//    @Test
//    fun field_fix_isCorrect() {
//        val field = TetrisField(4, 6)
//        val block = makeSingleBlock(field)
//        block.moveTo(1, 5)
//        block.fixToField()
//        assertEquals("""
//            |    |
//            |    |
//            |    |
//            |    |
//            |    |
//            | *  |
//            """.trimIndent(), field.toString())
//    }
//
//    @Test
//    fun block_moveToRightEnd_isCorrect() {
//        val field = TetrisField(4, 1)
//        val block = makeSingleBlock(field)
//        assertEquals(true, block.moveToRight())
//        assertEquals(1, block.x)
//        assertEquals(0, block.y)
//        assertEquals(true, block.moveToRight())
//        assertEquals(2, block.x)
//        assertEquals(0, block.y)
//        assertEquals(true, block.moveToRight())
//        assertEquals(3, block.x)
//        assertEquals(0, block.y)
//        assertEquals(false, block.moveToRight())
//        assertEquals(3, block.x)
//        assertEquals(0, block.y)
//        block.fixToField()
//        assertEquals("""
//            |   *|
//            """.trimIndent(), field.toString())
//    }
//
//    @Test
//    fun block_moveToRightBlock_isCorrect() {
//        val field = with(TetrisField(4, 1)) {
//            val block = makeSingleBlock(this)
//            while (block.moveToRight()) {
//            }
//            block.fixToField()
//            this
//        }
//        val block = makeSingleBlock(field)
//        assertEquals(true, block.moveToRight())
//        assertEquals(1, block.x)
//        assertEquals(0, block.y)
//        assertEquals(true, block.moveToRight())
//        assertEquals(2, block.x)
//        assertEquals(0, block.y)
//        assertEquals(false, block.moveToRight())
//        assertEquals(2, block.x)
//        assertEquals(0, block.y)
//        block.fixToField()
//        assertEquals("""
//            |  **|
//            """.trimIndent(), field.toString())
//    }
//
//    @Test
//    fun block_moveToLeftEnd_isCorrect() {
//        val field = TetrisField(4, 1)
//        val block = with(makeSingleBlock(field)) {
//            while (this.moveToRight()) {
//            }
//            this
//        }
//        assertEquals(true, block.moveToLeft())
//        assertEquals(2, block.x)
//        assertEquals(0, block.y)
//        assertEquals(true, block.moveToLeft())
//        assertEquals(1, block.x)
//        assertEquals(0, block.y)
//        assertEquals(true, block.moveToLeft())
//        assertEquals(0, block.x)
//        assertEquals(0, block.y)
//        assertEquals(false, block.moveToLeft())
//        assertEquals(0, block.x)
//        assertEquals(0, block.y)
//        block.fixToField()
//        assertEquals("""
//            |*   |
//            """.trimIndent(), field.toString())
//    }
//
//    @Test
//    fun block_moveToLeftBlock_isCorrect() {
//        val field = with(TetrisField(4, 1)) {
//            val block = makeSingleBlock(this)
//            block.fixToField()
//            this
//        }
//        val block = with(makeSingleBlock(field)) {
//            while (this.moveToRight()) {
//            }
//            this
//        }
//        assertEquals(true, block.moveToLeft())
//        assertEquals(2, block.x)
//        assertEquals(0, block.y)
//        assertEquals(true, block.moveToLeft())
//        assertEquals(1, block.x)
//        assertEquals(0, block.y)
//        assertEquals(false, block.moveToLeft())
//        assertEquals(1, block.x)
//        assertEquals(0, block.y)
//        block.fixToField()
//        assertEquals("""
//            |**  |
//            """.trimIndent(), field.toString())
//    }
//
//    @Test
//    fun block_moveToDownEnd_isCorrect() {
//        val field = TetrisField(1, 6)
//        val block = makeSingleBlock(field)
//        assertEquals(true, block.moveToDown())
//        assertEquals(0, block.x)
//        assertEquals(1, block.y)
//        assertEquals(true, block.moveToDown())
//        assertEquals(0, block.x)
//        assertEquals(2, block.y)
//        assertEquals(true, block.moveToDown())
//        assertEquals(0, block.x)
//        assertEquals(3, block.y)
//        assertEquals(true, block.moveToDown())
//        assertEquals(0, block.x)
//        assertEquals(4, block.y)
//        assertEquals(true, block.moveToDown())
//        assertEquals(0, block.x)
//        assertEquals(5, block.y)
//        assertEquals(false, block.moveToDown())
//        assertEquals(0, block.x)
//        assertEquals(5, block.y)
//        block.fixToField()
//        assertEquals("""
//            | |
//            | |
//            | |
//            | |
//            | |
//            |*|
//            """.trimIndent(), field.toString())
//    }
//
//    @Test
//    fun block_moveToDownBlock_isCorrect() {
//        val field = with(TetrisField(1, 6)) {
//            val block = makeSingleBlock(this)
//            while (block.moveToDown()) {}
//            block.fixToField()
//            this
//        }
//        val block = makeSingleBlock(field)
//        assertEquals(true, block.moveToDown())
//        assertEquals(0, block.x)
//        assertEquals(1, block.y)
//        assertEquals(true, block.moveToDown())
//        assertEquals(0, block.x)
//        assertEquals(2, block.y)
//        assertEquals(true, block.moveToDown())
//        assertEquals(0, block.x)
//        assertEquals(3, block.y)
//        assertEquals(true, block.moveToDown())
//        assertEquals(0, block.x)
//        assertEquals(4, block.y)
//        assertEquals(false, block.moveToDown())
//        assertEquals(0, block.x)
//        assertEquals(4, block.y)
//        block.fixToField()
//        assertEquals("""
//            | |
//            | |
//            | |
//            | |
//            |*|
//            |*|
//            """.trimIndent(), field.toString())
//    }
//
//    @Test
//    fun field_erase_isCorrect() {
//        val field = with(TetrisField(4, 6)) {
//            with(makeSingleBlock(this)) { moveTo(0, 5); fixToField() }
//            with(makeSingleBlock(this)) { moveTo(1, 5); fixToField() }
//            with(makeSingleBlock(this)) { moveTo(2, 5); fixToField() }
//            with(makeSingleBlock(this)) { moveTo(3, 5); fixToField() }
//            with(makeSingleBlock(this)) { moveTo(0, 4); fixToField() }
//            with(makeSingleBlock(this)) { moveTo(1, 4); fixToField() }
//            with(makeSingleBlock(this)) { moveTo(3, 4); fixToField() }
//            with(makeSingleBlock(this)) { moveTo(0, 3); fixToField() }
//            with(makeSingleBlock(this)) { moveTo(1, 3); fixToField() }
//            with(makeSingleBlock(this)) { moveTo(2, 3); fixToField() }
//            with(makeSingleBlock(this)) { moveTo(3, 3); fixToField() }
//            with(makeSingleBlock(this)) { moveTo(0, 0); fixToField() }
//            with(makeSingleBlock(this)) { moveTo(2, 0); fixToField() }
//            this
//        }
//        assertEquals("""
//            |* * |
//            |    |
//            |    |
//            |****|
//            |** *|
//            |****|
//            """.trimIndent(), field.toString())
//        field.erase()
//        assertEquals("""
//            |    |
//            |    |
//            |* * |
//            |    |
//            |    |
//            |** *|
//            """.trimIndent(), field.toString())
//    }
//
//}
