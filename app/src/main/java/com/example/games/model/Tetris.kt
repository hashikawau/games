package com.example.games.model

import com.example.games.model.blocks.*
import java.util.*

class Field(
        private val _width: Int,
        private val _height: Int,
        private val _random: Random = Random(0)
) {

    private val _array2d: Array<Array<Boolean>> = Array(_height) { Array(_width) { false } }

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

    fun setCell(x: Int, y: Int) {
        if (isInRange(x, y))
            _array2d[y][x] = true
    }

    fun isEmpty(x: Int, y: Int): Boolean {
        return if (isInRange(x, y))
            !_array2d[y][x]
        else
            false
    }

    private val _blocks = arrayOf(
            { makeRectangleBlock(this, _width / 2 - 1, 0) },
            { makeStraightBlock(this, _width / 2 - 1, 0) },
            { makeGapLeftBlock(this, _width / 2 - 1, 0) },
            { makeGapRightBlock(this, _width / 2 - 1, 0) },
            { makeHookLeftBlock(this, _width / 2 - 1, 0) },
            { makeHookRightBlock(this, _width / 2 - 1, 0) },
            { makeHookCenterBlock(this, _width / 2 - 1, 0) }
    )

    fun newBlock(): IBlock {
        return this._blocks[_random.nextInt(this._blocks.size)]()
    }

    fun erase() {
        val remains: List<Array<Boolean>> = _array2d.filter { row -> row.any { b -> !b } }
        val offset = _height - remains.size
        for (y in 0 until (offset)) {
            _array2d[y] = Array(_width) { false }
        }
        for (y in offset until (_height)) {
            _array2d[y] = remains[y - offset]
        }
    }

    override fun toString(): String {
        return _array2d.joinToString("\n") { row ->
            "%s".format(outputString(row))
        }
    }

    companion object {
        private fun outputString(row: Array<Boolean>): String {
            return row.joinToString("", "|", "|") { b ->
                if (b) "*" else " "
            }
        }
    }
}
