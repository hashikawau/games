package com.example.games.model.blocks

import com.example.games.model.Field

class SingleBlock(
        field: Field,
        x: Int = 0,
        y: Int = 0
) : AbstractBlock(field, x, y) {
    val x: Int get () = _x
    val y: Int get () = _y

    fun moveTo(x: Int, y: Int) {
        _x = x
        _y = y
    }

    override fun positions(): Array<Position> {
        return arrayOf(Position(_x, _y))
    }

    override fun rotateRight(): Boolean {
        return true
    }

    override fun rotateLeft(): Boolean {
        return true
    }
}
