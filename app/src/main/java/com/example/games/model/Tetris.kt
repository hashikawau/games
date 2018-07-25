package com.example.games.model

class Field(val _width: Int, val _height: Int) {

    val _array2d: Array<Array<Boolean>>

    init {
        _array2d = Array<Array<Boolean>>(_height, { Array<Boolean>(_width, { false }) })
    }

    override fun toString(): String {
        return _array2d
                .map { row -> "%s".format(outputString(row)) }
                .joinToString("\n")
    }

    companion object {
        private fun outputString(row: Array<Boolean>): String {
            return row
                    .map {b -> if (b) "*" else " " }
                    .joinToString("", "|", "|")
        }
    }
}