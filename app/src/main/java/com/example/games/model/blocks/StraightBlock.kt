package com.example.games.model.blocks

import com.example.games.model.Field

class StraightBlock(private val _field: Field, private var _x: Int = 0, private var _y: Int = 0) : IBlock {
    private var _state: IStraightBlock = VerticalStraightBlock(_field, _x, _y)

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

private interface IStraightBlock : IBlock {
    fun rotatedRight(): IStraightBlock
    fun rotatedLeft(): IStraightBlock
}

private class VerticalStraightBlock(private val _field: Field, private var _x: Int, private var _y: Int) : IStraightBlock {
    override fun positions(): Array<Position> {
        return arrayOf(
                Position(_x, _y),
                Position(_x, _y + 1),
                Position(_x, _y + 2),
                Position(_x, _y + 3))
    }

    override fun fixToField(): Unit {
        _field.setCell(_x, _y)
        _field.setCell(_x, _y + 1)
        _field.setCell(_x, _y + 2)
        _field.setCell(_x, _y + 3)
    }

    override fun moveToRight(): Boolean {
        if (_field.isEmpty(_x + 1, _y)
                and _field.isEmpty(_x + 1, _y + 1)
                and _field.isEmpty(_x + 1, _y + 2)
                and _field.isEmpty(_x + 1, _y + 3)) {
            ++_x
            return true
        } else {
            return false
        }
    }

    override fun moveToLeft(): Boolean {
        if (_field.isEmpty(_x - 1, _y)
                and _field.isEmpty(_x - 1, _y + 1)
                and _field.isEmpty(_x - 1, _y + 2)
                and _field.isEmpty(_x - 1, _y + 3)) {
            --_x
            return true
        } else {
            return false
        }
    }

    override fun moveToDown(): Boolean {
        if (_field.isEmpty(_x, _y + 4)) {
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

    override fun rotatedRight(): IStraightBlock {
        return HorizontalStraightBlock(_field, _x, _y + 1)
    }

    override fun rotatedLeft(): IStraightBlock {
        return HorizontalStraightBlock(_field, _x, _y + 1)
    }

}

private class HorizontalStraightBlock(private val _field: Field, private var _x: Int, private var _y: Int) : IStraightBlock {
    override fun positions(): Array<Position> {
        return arrayOf(
                Position(_x - 1, _y),
                Position(_x + 0, _y),
                Position(_x + 1, _y),
                Position(_x + 2, _y))
    }

    override fun fixToField(): Unit {
        _field.setCell(_x - 1, _y)
        _field.setCell(_x + 0, _y)
        _field.setCell(_x + 1, _y)
        _field.setCell(_x + 2, _y)
    }

    override fun moveToRight(): Boolean {
        if (_field.isEmpty(_x + 3, _y)) {
            ++_x
            return true
        } else {
            return false
        }
    }

    override fun moveToLeft(): Boolean {
        if (_field.isEmpty(_x - 2, _y)) {
            --_x
            return true
        } else {
            return false
        }
    }

    override fun moveToDown(): Boolean {
        if (_field.isEmpty(_x - 1, _y + 1)
                and _field.isEmpty(_x + 0, _y + 1)
                and _field.isEmpty(_x + 1, _y + 1)
                and _field.isEmpty(_x + 2, _y + 1)) {
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

    override fun rotatedRight(): IStraightBlock {
        return VerticalStraightBlock(_field, _x, _y - 1)
    }

    override fun rotatedLeft(): IStraightBlock {
        return VerticalStraightBlock(_field, _x, _y - 1)
    }

}
