package com.example.games.model

import com.example.games.model.blocks.*
import java.util.*

class Field(
        private val _width: Int,
        private val _height: Int,
        private val _random: Random = Random(0)
) {
    enum class Space(val state: Int) {
        EMPTY(0),
        RECTANGLE(1),
        STRAIGHT(2),
        GAP_LEFT(3),
        GAP_RIGHT(4),
        HOOK_LEFT(5),
        HOOK_RIGHT(6),
        HOOK_CENTER(7)
    }

    private val _array2d: Array<Array<Space>> = Array(_height) { Array(_width) { Space.EMPTY } }

    val array2d
        get() = _array2d

    private fun isInRange(x: Int, y: Int): Boolean {
        if (x < 0 || x >= _width) {
            return false
        }
        if (y < 0 || y >= _height) {
            return false
        }
        return true
    }

    fun setCell(x: Int, y: Int, space: Space) {
        if (isInRange(x, y))
            _array2d[y][x] = space
    }

    fun isEmpty(x: Int, y: Int): Boolean {
        return if (isInRange(x, y))
            _array2d[y][x] == Space.EMPTY
        else
            false
    }

    private val _blocks = arrayOf(
            { makeRectangleBlock(this, _width / 2 - 2, -1) },
            { makeStraightBlock(this, _width / 2 - 2, 0) },
            { makeGapLeftBlock(this, _width / 2 - 2, 0) },
            { makeGapRightBlock(this, _width / 2 - 2, 0) },
            { makeHookLeftBlock(this, _width / 2 - 2, 0) },
            { makeHookRightBlock(this, _width / 2 - 2, 0) },
            { makeHookCenterBlock(this, _width / 2 - 2, 0) }
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
        val offset = _height - anyStoneRows.size
        for (y in 0 until (offset)) {
            _array2d[y] = Array(_width) { Space.EMPTY }
        }
    }

    private fun rearrangeAnyStoneRows(anyStoneRows: List<Array<Space>>) {
        val offset = _height - anyStoneRows.size
        for (y in offset until (_height)) {
            _array2d[y] = anyStoneRows[y - offset]
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
