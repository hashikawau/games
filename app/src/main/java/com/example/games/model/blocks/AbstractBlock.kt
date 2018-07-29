package com.example.games.model.blocks

import com.example.games.model.Field

abstract class AbstractBlock(
        protected val _field: Field,
        protected var _x: Int,
        protected var _y: Int
) : IBlock {
    protected var _current = 0

    open fun shapes(): Array<Array<Position>> {
        return arrayOf()
    }

    private fun shape(): Array<Position> {
        return shapes()[_current]
    }

    override fun positions(): Array<Position> {
        return shape().map { p -> Position(_x + p.x, _y + p.y) }.toTypedArray()
    }

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

    override fun rotateRight(): Boolean {
        val next = (_current + 1) % shapes().size
        return if (shapes()[next].all { p -> _field.isEmpty(p.x, p.y) }) {
            _current = next
            true
        } else {
            false
        }
    }

    override fun rotateLeft(): Boolean {
        val next = (_current + shapes().size - 1) % shapes().size
        return if (shapes()[next].all { p -> _field.isEmpty(p.x, p.y) }) {
            _current = next
            true
        } else {
            false
        }
    }
}
