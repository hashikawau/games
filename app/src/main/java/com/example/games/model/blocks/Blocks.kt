package com.example.games.model.blocks

import com.example.games.model.Field

interface IBlock {
    fun positions(): Array<Position>
    fun fixToField(): Unit
    fun moveToRight(): Boolean
    fun moveToLeft(): Boolean
    fun moveToDown(): Boolean
    fun rotateRight(): Boolean
    fun rotateLeft(): Boolean
}

class Position(val x: Int, val y: Int) {}

class MinimumBlock(
        private val _field: Field,
        private var _x: Int = 0,
        private var _y: Int = 0) : IBlock {
    val x: Int get () = _x
    val y: Int get () = _y

    fun moveTo(x: Int, y: Int) {
        _x = x
        _y = y
    }

    override fun positions(): Array<Position> {
        return arrayOf(Position(_x, _y))
    }

    override fun fixToField(): Unit {
        _field.setCell(_x, _y)
    }

    override fun moveToRight(): Boolean {
        if (_field.isEmpty(_x + 1, _y)) {
            ++_x
            return true
        } else {
            return false
        }
    }

    override fun moveToLeft(): Boolean {
        if (_field.isEmpty(_x - 1, _y)) {
            --_x
            return true
        } else {
            return false
        }
    }

    override fun moveToDown(): Boolean {
        if (_field.isEmpty(_x, _y + 1)) {
            ++_y
            return true
        } else {
            return false
        }
    }

    override fun rotateRight(): Boolean {
        return true
    }

    override fun rotateLeft(): Boolean {
        return true
    }
}

