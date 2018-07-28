package com.example.games.model.blocks

import com.example.games.model.Field

class GapRightBlock(private val _field: Field, private var _x: Int = 0, private var _y: Int = 0) : IBlock {
    private var _state: IGapRightBlock = VerticalGapRightBlock(_field, _x, _y)

    override fun positions(): Array<Position> {
        return _state.positions()
    }

    override fun fixToField(): Unit {
        return _state.fixToField()
    }

    override fun moveToRight(): Boolean {
        return _state.moveToRight()
    }

    override fun moveToLeft(): Boolean {
        return _state.moveToLeft()
    }

    override fun moveToDown(): Boolean {
        return _state.moveToDown()
    }

    override fun rotateRight(): Boolean {
        val rotated = _state.rotatedRight()
        val canRotate = rotated.positions().all {p -> _field.isEmpty(p.x, p.y)}
        if (canRotate) {
            _state = rotated
        }
        return canRotate
    }

    override fun rotateLeft(): Boolean {
        val rotated = _state.rotatedLeft()
        val canRotate = rotated.positions().all {p -> _field.isEmpty(p.x, p.y)}
        if (canRotate) {
            _state = rotated
        }
        return canRotate
    }
}

private interface IGapRightBlock : IBlock {
    fun rotatedRight(): IGapRightBlock
    fun rotatedLeft(): IGapRightBlock
}

private class VerticalGapRightBlock(private val _field: Field, private var _x: Int, private var _y: Int) : IGapRightBlock {
    override fun positions(): Array<Position> {
        return arrayOf(
                Position(_x + 0, _y + 0),
                Position(_x + 0, _y + 1),
                Position(_x + 1, _y + 1),
                Position(_x + 1, _y + 2))
    }

    override fun fixToField(): Unit {
        positions().forEach { p ->  _field.setCell(p.x, p.y)}
    }

    override fun moveToRight(): Boolean {
        if (VerticalGapRightBlock(_field, _x + 1, _y).positions().all { p -> _field.isEmpty(p.x, p.y) }) {
            ++_x
            return true
        } else {
            return false
        }
    }

    override fun moveToLeft(): Boolean {
        if (VerticalGapRightBlock(_field, _x - 1, _y).positions().all { p -> _field.isEmpty(p.x, p.y) }) {
            --_x
            return true
        } else {
            return false
        }
    }

    override fun moveToDown(): Boolean {
        if (VerticalGapRightBlock(_field, _x, _y + 1).positions().all { p -> _field.isEmpty(p.x, p.y) }) {
            ++_y
            return true
        } else {
            return false
        }
    }

    override fun rotateRight(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun rotateLeft(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun rotatedRight(): IGapRightBlock {
        return HorizontalGapRightBlock(_field, _x, _y)
    }

    override fun rotatedLeft(): IGapRightBlock {
        return HorizontalGapRightBlock(_field, _x, _y)
    }

}

private class HorizontalGapRightBlock(private val _field: Field, private var _x: Int, private var _y: Int) : IGapRightBlock {
    override fun positions(): Array<Position> {
        return arrayOf(
                Position(_x + 0, _y + 1),
                Position(_x + 1, _y + 0),
                Position(_x + 1, _y + 1),
                Position(_x + 2, _y + 0))
    }

    override fun fixToField(): Unit {
        positions().forEach { p ->  _field.setCell(p.x, p.y)}
    }

    override fun moveToRight(): Boolean {
        if (HorizontalGapRightBlock(_field, _x + 1, _y).positions().all { p -> _field.isEmpty(p.x, p.y) }) {
            ++_x
            return true
        } else {
            return false
        }
    }

    override fun moveToLeft(): Boolean {
        if (HorizontalGapRightBlock(_field, _x - 1, _y).positions().all { p -> _field.isEmpty(p.x, p.y) }) {
            --_x
            return true
        } else {
            return false
        }
    }

    override fun moveToDown(): Boolean {
        if (HorizontalGapRightBlock(_field, _x, _y + 1).positions().all { p -> _field.isEmpty(p.x, p.y) }) {
            ++_y
            return true
        } else {
            return false
        }
    }

    override fun rotateRight(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun rotateLeft(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun rotatedRight(): IGapRightBlock {
        return VerticalGapRightBlock(_field, _x, _y)
    }

    override fun rotatedLeft(): IGapRightBlock {
        return VerticalGapRightBlock(_field, _x, _y)
    }

}