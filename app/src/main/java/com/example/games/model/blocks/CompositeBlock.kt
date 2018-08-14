package com.example.games.model.blocks

import com.example.games.model.TetrisField

class CompositeBlock(
        protected val _shapes: Array<Array<Position>>,
        protected val _field: TetrisField,
        public val space: TetrisField.Space,
        protected var _x: Int,
        protected var _y: Int
) {
    protected var _current = 0

    fun shape(): Array<Position> {
        return _shapes[_current]
    }

    fun positions(): Array<Position> {
        return shape().map { p -> Position(_x + p.x, _y + p.y) }.toTypedArray()
    }

    fun canFixToField(): Boolean {
        return positions().all { p -> _field.isEmpty(p.x, p.y) }
    }

    fun fixToField() {
        positions().forEach { p -> _field.setCell(p.x, p.y, space) }
    }

    fun moveToRight(): Boolean {
        if (positions().map { p -> Position(p.x + 1, p.y) }.all { p -> _field.isEmpty(p.x, p.y) }) {
            ++_x
            return true
        } else {
            return false
        }
    }

    fun moveToLeft(): Boolean {
        if (positions().map { p -> Position(p.x - 1, p.y) }.all { p -> _field.isEmpty(p.x, p.y) }) {
            --_x
            return true
        } else {
            return false
        }
    }

    fun moveToDown(): Boolean {
        if (positions().map { p -> Position(p.x, p.y + 1) }.all { p -> _field.isEmpty(p.x, p.y) }) {
            ++_y
            return true
        } else {
            return false
        }
    }

    fun rotateRight(): Boolean {
        val next = (_current + 1) % _shapes.size
        return if (_shapes[next].map { p -> Position(_x + p.x, _y + p.y) }.all { p -> _field.isEmpty(p.x, p.y) }) {
            _current = next
            true
        } else {
            false
        }
    }

    fun rotateLeft(): Boolean {
        val next = (_current + _shapes.size - 1) % _shapes.size
        return if (_shapes[next].map { p -> Position(_x + p.x, _y + p.y) }.all { p -> _field.isEmpty(p.x, p.y) }) {
            _current = next
            true
        } else {
            false
        }
    }
}

class Position(val x: Int, val y: Int) {
    fun rotateRight(): Position {
        return Position(y, -x)
    }
    fun rotateLeft(): Position {
        return Position(-y, x)
    }

    override fun toString(): String {
        return "P(%d,%d)".format(x, y)
    }
}
