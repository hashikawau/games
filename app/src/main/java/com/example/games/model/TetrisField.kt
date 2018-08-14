package com.example.games.model

import com.example.games.model.blocks.*
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

    val array2d: Array<Array<Space>> = Array(height) { Array(width) { Space.EMPTY } }

    private fun isInRange(j: Int, i: Int): Boolean {
        if (j < 0 || j >= width) {
            return false
        }
        if (i < 0 || i >= height) {
            return false
        }
        return true
    }

    fun setCell(j: Int, i: Int, space: Space) {
        if (isInRange(j, i))
            array2d[i][j] = space
    }

    fun isEmpty(j: Int, i: Int): Boolean {
        return if (isInRange(j, i))
            array2d[i][j] == Space.EMPTY
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
        return array2d
                .mapIndexed({ index, row -> Pair(index, row.all { space -> space != Space.EMPTY }) })
                .filter { tup -> tup.second }
                .map { tup -> tup.first }
    }

    fun erase() {
        val anyStoneRows: List<Array<Space>> = array2d.filter { row -> row.any { space -> space == Space.EMPTY } }
        rearrangeNoStoneRows(anyStoneRows)
        rearrangeAnyStoneRows(anyStoneRows)
    }

    private fun rearrangeNoStoneRows(anyStoneRows: List<Array<Space>>) {
        val offset = height - anyStoneRows.size
        for (i in 0 until (offset)) {
            array2d[i] = Array(width) { Space.EMPTY }
        }
    }

    private fun rearrangeAnyStoneRows(anyStoneRows: List<Array<Space>>) {
        val offset = height - anyStoneRows.size
        for (i in offset until (height)) {
            array2d[i] = anyStoneRows[i - offset]
        }
    }

    override fun toString(): String {
        return array2d.joinToString("\n") { row ->
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
