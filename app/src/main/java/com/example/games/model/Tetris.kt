package com.example.games.model

import android.support.constraint.solver.widgets.Rectangle
import android.util.Log
import com.example.games.model.blocks.*
import java.util.*

class Field(private val _width: Int, private val _height: Int) {

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
        if (isInRange(x, y))
            return !_array2d[y][x]
        else
            return false
    }

    private val _rand = Random(Date().time)
    fun newBlock(): IBlock {
        val type = _rand.nextInt(6)
        return when (type) {
            0 -> RectangleBlock(this, _width / 2 - 1, 0)
            1 -> StraightBlock(this, _width / 2 - 1, 0)
            2 -> GapLeftBlock(this, _width / 2 - 1, 0)
            3 -> GapRightBlock(this, _width / 2 - 1, 0)
            4 -> HookLeftBlock(this, _width / 2 - 1, 0)
            5 -> HookRightBlock(this, _width / 2 - 1, 0)
            else -> MinimumBlock(this, _width / 2 - 1, 0)
        }
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
        return _array2d
                .map { row -> "%s".format(outputString(row)) }
                .joinToString("\n")
    }

    companion object {
        private fun outputString(row: Array<Boolean>): String {
            return row
                    .map { b -> if (b) "*" else " " }
                    .joinToString("", "|", "|")
        }
    }
}
