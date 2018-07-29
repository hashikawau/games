package com.example.games.model.blocks

import com.example.games.model.Field

abstract class AbstractBlock(
        protected val _field: Field,
        protected var _x: Int,
        protected var _y: Int
) : IBlock {
    abstract override fun positions(): Array<Position>
    abstract override fun rotateRight(): Boolean
    abstract override fun rotateLeft(): Boolean

    override fun fixToField() {
        positions().forEach { p -> _field.setCell(p.x, p.y) }
    }

    override fun moveToRight(): Boolean {
        if (positions().map { p -> Position(p.x + 1, p.y) }.all { p -> _field.isEmpty(p.x, p.y) }) {
            ++_x
            return true
        } else {
            return false
        }
    }

    override fun moveToLeft(): Boolean {
        if (positions().map { p -> Position(p.x - 1, p.y) }.all { p -> _field.isEmpty(p.x, p.y) }) {
            --_x
            return true
        } else {
            return false
        }
    }

    override fun moveToDown(): Boolean {
        if (positions().map { p -> Position(p.x, p.y + 1) }.all { p -> _field.isEmpty(p.x, p.y) }) {
            ++_y
            return true
        } else {
            return false
        }
    }
}
