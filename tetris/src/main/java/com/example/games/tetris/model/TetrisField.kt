package com.example.games.tetris.model

import com.example.games.tetris.model.blocks.*
import java.util.*

class TetrisField(
        val width: Int,
        val height: Int,
        private val _random: Random = Random(0)
) {
    enum class Space() {
        EMPTY(),
        RECTANGLE(),
        STRAIGHT(),
        GAP_LEFT(),
        GAP_RIGHT(),
        HOOK_LEFT(),
        HOOK_RIGHT(),
        HOOK_CENTER()
    }

    private val _array2d: Array<Array<Space>> = Array(height) { Array(width) { Space.EMPTY } }

    fun space(i: Int, j: Int): Space {
        return _array2d[i][j]
    }

    fun isGameOver(): Boolean {
        return _array2d[0].any { space -> space != TetrisField.Space.EMPTY }
    }

    private fun isInRange(i: Int, j: Int): Boolean {
        if (i < 0 || i >= height) {
            return false
        }
        if (j < 0 || j >= width) {
            return false
        }
        return true
    }

    fun setCell(i: Int, j: Int, space: Space) {
        if (isInRange(i, j))
            _array2d[i][j] = space
    }

    fun isEmpty(i: Int, j: Int): Boolean {
        return if (isInRange(i, j))
            _array2d[i][j] == Space.EMPTY
        else
            false
    }

    private val _blocks = arrayOf(
            { makeRectangleBlock(this, width / 2 - 2, -1) },
            { makeStraightBlock(this, width / 2 - 2, 0) },
            { makeGapLeftBlock(this, width / 2 - 2, 0) },
            { makeGapRightBlock(this, width / 2 - 2, 0) },
            { makeHookLeftBlock(this, width / 2 - 2, 0) },
            { makeHookRightBlock(this, width / 2 - 2, 0) },
            { makeHookCenterBlock(this, width / 2 - 2, 0) }
    )

    fun newBlock(): CompositeBlock {
        return this._blocks[_random.nextInt(this._blocks.size)]()
    }

    fun erasedLines(): List<Int> {
        return _array2d
                .mapIndexed({ index, row -> Pair(index, row.all { space -> space != Space.EMPTY }) })
                .filter { tup -> tup.second }
                .map { tup -> tup.first }
    }

    fun erase() {
        val anyStoneRows: List<Array<Space>> = _array2d.filter { row -> row.any { space -> space == Space.EMPTY } }
        rearrangeNoStoneRows(anyStoneRows)
        rearrangeAnyStoneRows(anyStoneRows)
    }

    private fun rearrangeNoStoneRows(anyStoneRows: List<Array<Space>>) {
        val offset = height - anyStoneRows.size
        for (i in 0 until (offset)) {
            _array2d[i] = Array(width) { Space.EMPTY }
        }
    }

    private fun rearrangeAnyStoneRows(anyStoneRows: List<Array<Space>>) {
        val offset = height - anyStoneRows.size
        for (i in offset until (height)) {
            _array2d[i] = anyStoneRows[i - offset]
        }
    }

    override fun toString(): String {
        return _array2d.joinToString("\n") { row ->
            "%s".format(outputString(row))
        }
    }

    companion object {
        private fun outputString(row: Array<Space>): String {
            return row.joinToString("", "|", "|") { space ->
                if (space != Space.EMPTY) "*" else " "
            }
        }
    }
}
