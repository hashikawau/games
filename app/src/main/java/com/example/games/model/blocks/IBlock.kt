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

class Position(val x: Int, val y: Int)