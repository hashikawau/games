package com.example.games.model.blocks

import com.example.games.model.Field

class StraightBlock(
        field: Field,
        x: Int = 0,
        y: Int = 0
) : AbstractBlock(field, x, y) {

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
        val canRotate = rotated.positions().all { p -> _field.isEmpty(p.x, p.y) }
        if (canRotate) {
            _state = rotated
        }
        return canRotate
    }

    override fun rotateLeft(): Boolean {
        val rotated = _state.rotatedLeft()
        val canRotate = rotated.positions().all { p -> _field.isEmpty(p.x, p.y) }
        if (canRotate) {
            _state = rotated
        }
        return canRotate
    }
}

private abstract class IStraightBlock(
        field: Field,
        x: Int,
        y: Int
) : AbstractBlock(field, x, y) {
    abstract fun rotatedRight(): IStraightBlock
    abstract fun rotatedLeft(): IStraightBlock
}

private class VerticalStraightBlock(
        field: Field,
        x: Int,
        y: Int
) : IStraightBlock(field, x, y) {
    override fun positions(): Array<Position> {
        return arrayOf(
                Position(_x, _y),
                Position(_x, _y + 1),
                Position(_x, _y + 2),
                Position(_x, _y + 3))
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

private class HorizontalStraightBlock(
        field: Field,
        x: Int,
        y: Int
) : IStraightBlock(field, x, y) {
    override fun positions(): Array<Position> {
        return arrayOf(
                Position(_x - 1, _y),
                Position(_x + 0, _y),
                Position(_x + 1, _y),
                Position(_x + 2, _y))
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
