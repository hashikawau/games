package com.example.games.model.blocks

import com.example.games.model.Field

class HookRightBlock(private val _field: Field, private var _x: Int = 0, private var _y: Int = 0) : IBlock {
    private var _state: IHookRightBlock = VerticalHookRightBlock(_field, _x, _y)

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

private interface IHookRightBlock : IBlock {
    fun rotatedRight(): IHookRightBlock
    fun rotatedLeft(): IHookRightBlock
}

private class VerticalHookRightBlock(private val _field: Field, private var _x: Int, private var _y: Int) : IHookRightBlock {
    override fun positions(): Array<Position> {
        return arrayOf(
                Position(_x + 0, _y + 0),
                Position(_x + 1, _y + 0),
                Position(_x + 1, _y + 1),
                Position(_x + 1, _y + 2))
    }

    override fun fixToField(): Unit {
        positions().forEach { p ->  _field.setCell(p.x, p.y)}
    }

    override fun moveToRight(): Boolean {
        if (VerticalHookRightBlock(_field, _x + 1, _y).positions().all { p -> _field.isEmpty(p.x, p.y) }) {
            ++_x
            return true
        } else {
            return false
        }
    }

    override fun moveToLeft(): Boolean {
        if (VerticalHookRightBlock(_field, _x - 1, _y).positions().all { p -> _field.isEmpty(p.x, p.y) }) {
            --_x
            return true
        } else {
            return false
        }
    }

    override fun moveToDown(): Boolean {
        if (VerticalHookRightBlock(_field, _x, _y + 1).positions().all { p -> _field.isEmpty(p.x, p.y) }) {
            ++_y
            return true
        } else {
            return false
        }
    }

    override fun rotateRight(): Boolean {
        assert(false) {"not implemented"}
        return false
    }

    override fun rotateLeft(): Boolean {
        assert(false) {"not implemented"}
        return false
    }

    override fun rotatedRight(): IHookRightBlock {
        return ReverseHorizontalHookRightBlock(_field, _x, _y)
    }

    override fun rotatedLeft(): IHookRightBlock {
        return HorizontalHookRightBlock(_field, _x, _y + 1)
    }

}

private class ReverseHorizontalHookRightBlock(private val _field: Field, private var _x: Int, private var _y: Int) : IHookRightBlock {
    override fun positions(): Array<Position> {
        return arrayOf(
                Position(_x + 0, _y + 1),
                Position(_x + 1, _y + 1),
                Position(_x + 2, _y + 0),
                Position(_x + 2, _y + 1))
    }

    override fun fixToField(): Unit {
        positions().forEach { p ->  _field.setCell(p.x, p.y)}
    }

    override fun moveToRight(): Boolean {
        if (HorizontalHookRightBlock(_field, _x + 1, _y).positions().all { p -> _field.isEmpty(p.x, p.y) }) {
            ++_x
            return true
        } else {
            return false
        }
    }

    override fun moveToLeft(): Boolean {
        if (HorizontalHookRightBlock(_field, _x - 1, _y).positions().all { p -> _field.isEmpty(p.x, p.y) }) {
            --_x
            return true
        } else {
            return false
        }
    }

    override fun moveToDown(): Boolean {
        if (HorizontalHookRightBlock(_field, _x, _y + 1).positions().all { p -> _field.isEmpty(p.x, p.y) }) {
            ++_y
            return true
        } else {
            return false
        }
    }

    override fun rotateRight(): Boolean {
        assert(false) {"not implemented"}
        return false
    }

    override fun rotateLeft(): Boolean {
        assert(false) {"not implemented"}
        return false
    }

    override fun rotatedRight(): IHookRightBlock {
        return ReverseVerticalHookRightBlock(_field, _x + 1, _y)
    }

    override fun rotatedLeft(): IHookRightBlock {
        return VerticalHookRightBlock(_field, _x + 1, _y)
    }

}

private class ReverseVerticalHookRightBlock(private val _field: Field, private var _x: Int, private var _y: Int) : IHookRightBlock {
    override fun positions(): Array<Position> {
        return arrayOf(
                Position(_x + 0, _y + 0),
                Position(_x + 0, _y + 1),
                Position(_x + 0, _y + 2),
                Position(_x + 1, _y + 2))
    }

    override fun fixToField(): Unit {
        positions().forEach { p ->  _field.setCell(p.x, p.y)}
    }

    override fun moveToRight(): Boolean {
        if (VerticalHookRightBlock(_field, _x + 1, _y).positions().all { p -> _field.isEmpty(p.x, p.y) }) {
            ++_x
            return true
        } else {
            return false
        }
    }

    override fun moveToLeft(): Boolean {
        if (VerticalHookRightBlock(_field, _x - 1, _y).positions().all { p -> _field.isEmpty(p.x, p.y) }) {
            --_x
            return true
        } else {
            return false
        }
    }

    override fun moveToDown(): Boolean {
        if (VerticalHookRightBlock(_field, _x, _y + 1).positions().all { p -> _field.isEmpty(p.x, p.y) }) {
            ++_y
            return true
        } else {
            return false
        }
    }

    override fun rotateRight(): Boolean {
        assert(false) {"not implemented"}
        return false
    }

    override fun rotateLeft(): Boolean {
        assert(false) {"not implemented"}
        return false
    }

    override fun rotatedRight(): IHookRightBlock {
        return HorizontalHookRightBlock(_field, _x - 1, _y + 1)
    }

    override fun rotatedLeft(): IHookRightBlock {
        return ReverseHorizontalHookRightBlock(_field, _x, _y)
    }

}

private class HorizontalHookRightBlock(private val _field: Field, private var _x: Int, private var _y: Int) : IHookRightBlock {
    override fun positions(): Array<Position> {
        return arrayOf(
                Position(_x + 0, _y + 0),
                Position(_x + 0, _y + 1),
                Position(_x + 1, _y + 0),
                Position(_x + 2, _y + 0))
    }

    override fun fixToField(): Unit {
        positions().forEach { p ->  _field.setCell(p.x, p.y)}
    }

    override fun moveToRight(): Boolean {
        if (HorizontalHookRightBlock(_field, _x + 1, _y).positions().all { p -> _field.isEmpty(p.x, p.y) }) {
            ++_x
            return true
        } else {
            return false
        }
    }

    override fun moveToLeft(): Boolean {
        if (HorizontalHookRightBlock(_field, _x - 1, _y).positions().all { p -> _field.isEmpty(p.x, p.y) }) {
            --_x
            return true
        } else {
            return false
        }
    }

    override fun moveToDown(): Boolean {
        if (HorizontalHookRightBlock(_field, _x, _y + 1).positions().all { p -> _field.isEmpty(p.x, p.y) }) {
            ++_y
            return true
        } else {
            return false
        }
    }

    override fun rotateRight(): Boolean {
        assert(false) {"not implemented"}
        return false
    }

    override fun rotateLeft(): Boolean {
        assert(false) {"not implemented"}
        return false
    }

    override fun rotatedRight(): IHookRightBlock {
        return VerticalHookRightBlock(_field, _x, _y - 1)
    }

    override fun rotatedLeft(): IHookRightBlock {
        return ReverseVerticalHookRightBlock(_field, _x + 1, _y - 1)
    }

}
