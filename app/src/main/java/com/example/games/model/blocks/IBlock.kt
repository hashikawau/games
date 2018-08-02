package com.example.games.model.blocks

interface IBlock {
    fun positions(): Array<Position>
    fun fixToField()
    fun moveToRight(): Boolean
    fun moveToLeft(): Boolean
    fun moveToDown(): Boolean
    fun rotateRight(): Boolean
    fun rotateLeft(): Boolean
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
