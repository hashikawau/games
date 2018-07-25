package com.example.games.model

class Field(private val _width: Int, private val _height: Int) {

    private val _array2d: Array<Array<Boolean>>

    init {
        _array2d = Array<Array<Boolean>>(_height, { Array<Boolean>(_width, { false }) })
    }

    fun fix(block: Block1) {
        _array2d[block.x][block.y] = true
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

class Block1(
        private val _field: Field,
        private var _x: Int = 0,
        private var _y: Int = 0)
{
    val x: Int get () = _x
    val y: Int get () = _y

    fun moveTo(x: Int, y: Int) {
        _x = x
        _y = y
    }



}
