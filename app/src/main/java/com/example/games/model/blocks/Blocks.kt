package com.example.games.model.blocks

import com.example.games.model.Field

interface IBlock {
    fun positions(): Array<Position>
    fun fixToField()
    fun moveToRight(): Boolean
    fun moveToLeft(): Boolean
    fun moveToDown(): Boolean
    fun rotateRight(): Boolean
    fun rotateLeft(): Boolean
}

class Position(val x: Int, val y: Int)


