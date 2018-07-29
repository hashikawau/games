package com.example.games.model.blocks

import com.example.games.model.Field

class RectangleBlock(
        field: Field,
        x: Int = 0,
        y: Int = 0
) : AbstractBlock(field, x, y) {
    override fun positions(): Array<Position> {
        return arrayOf(
                Position(_x + 0, _y + 0),
                Position(_x + 0, _y + 1),
                Position(_x + 1, _y + 0),
                Position(_x + 1, _y + 1))
    }

    override fun rotateRight(): Boolean {
        return true
    }

    override fun rotateLeft(): Boolean {
        return true
    }
}
