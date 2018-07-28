package com.example.games.model

class Field(private val _width: Int, private val _height: Int) {

    private val _array2d: Array<Array<Boolean>> = Array(_height) { Array(_width) { false } }

    val array2d
        get() = _array2d

    private fun isInRange(x: Int, y: Int): Boolean {
        if (x < 0 || x >= _width) {
            return false
        }
        if (y < 0 || y >= _height) {
            return false
        }
        return true
    }

    fun setCell(x: Int, y: Int) {
        if (isInRange(x, y))
            _array2d[y][x] = true
    }

    fun isEmpty(x: Int, y: Int): Boolean {
        if (isInRange(x, y))
            return ! _array2d[y][x]
        else
            return false
    }

    fun erase() {
        val remains: List<Array<Boolean>> = _array2d.filter { row -> row.any { b -> ! b } }
        val offset = _height - remains.size
        for (y in 0 until(offset)) {
            _array2d[y] = Array(_width) { false }
        }
        for (y in offset until(_height)) {
            _array2d[y] = remains[y - offset]
        }
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

    fun fixToField() {
        _field.setCell(_x, _y)
    }

    fun moveToRight(): Boolean {
        if (_field.isEmpty(_x + 1, _y)) {
            ++_x
            return true
        } else {
            return false
        }
    }

    fun moveToLeft(): Boolean {
        if (_field.isEmpty(_x - 1, _y)) {
            --_x
            return true
        } else {
            return false
        }
    }

    fun moveToDown(): Boolean {
        if (_field.isEmpty(_x, _y + 1)) {
            ++_y
            return true
        } else {
            return false
        }
    }


}
