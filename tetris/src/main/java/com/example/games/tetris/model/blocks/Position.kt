package com.example.games.tetris.model.blocks


class Position(val x: Int, val y: Int) {
    override fun toString(): String {
        return "P(%d,%d)".format(x, y)
    }
}