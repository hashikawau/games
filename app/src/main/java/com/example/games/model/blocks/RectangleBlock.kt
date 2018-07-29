package com.example.games.model.blocks

import com.example.games.model.Field

class RectangleBlock(private val _field: Field, private var _x: Int = 0, private var _y: Int = 0) : IBlock {
    override fun positions(): Array<Position> {
        return arrayOf(
                Position(_x + 0, _y + 0),
                Position(_x + 0, _y + 1),
                Position(_x + 1, _y + 0),
                Position(_x + 1, _y + 1))
    }

    override fun fixToField(): Unit {
        _field.setCell(_x, _y)
        _field.setCell(_x, _y + 1)
        _field.setCell(_x + 1, _y)
        _field.setCell(_x + 1, _y + 1)
    }

    override fun moveToRight(): Boolean {
        if (RectangleBlock(_field, _x + 1, _y).positions().all { p -> _field.isEmpty(p.x, p.y) }) {
            ++_x
            return true
        } else {
            return false
        }
    }

    override fun moveToLeft(): Boolean {
        if (RectangleBlock(_field, _x - 1, _y).positions().all { p -> _field.isEmpty(p.x, p.y) }) {
            --_x
            return true
        } else {
            return false
        }
    }

    override fun moveToDown(): Boolean {
        if (RectangleBlock(_field, _x, _y + 1).positions().all { p -> _field.isEmpty(p.x, p.y) }) {
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
