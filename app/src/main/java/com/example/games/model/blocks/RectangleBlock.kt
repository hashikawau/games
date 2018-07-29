package com.example.games.model.blocks

import com.example.games.model.Field

class RectangleBlock(
        field: Field,
        x: Int = 0,
        y: Int = 0
) : AbstractBlock(field, x, y) {

    override fun shapes(): Array<Array<Position>> {
        return arrayOf(
                arrayOf(Position(0, 0),
                        Position(0, 1),
                        Position(1, 0),
                        Position(1, 1))
        )
    }
}
