package com.example.games

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import com.example.games.model.Field
import com.example.games.model.blocks.IBlock
import java.util.*

class TetrisActivity : AppCompatActivity() {

    private val _numX = 10
    private val _numY = 16
    private val _timeSpan = 1000L
    private val _tetrisField = Field(_numX, _numY)
    private var _currentBlock: IBlock? = null
    private var _buttons = Array(0) { Array(0) { Button(null) } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tetris)

        _buttons = Array(_numY) { Array(_numX) { Button(this) } }

        val tableLayout = findViewById<TableLayout>(R.id.tableLayout)
        val sizeButton = 60
        for (y in 0 until _numY) {
            val tableRow = TableRow(this)
            tableRow.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT)
            for (x in 0 until _numX) {
                _buttons!![y][x].layoutParams = TableRow.LayoutParams(sizeButton, sizeButton)
                tableRow.addView(_buttons!![y][x])
            }
            tableLayout.addView(tableRow, TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT))
        }

        // タイマーの初期化処理
        val drawingTimer = Timer(true)
        drawingTimer.schedule(object : TimerTask() {
            override fun run() {
                update()
            }
        }, 0, 100)

        //
        val downingTimer = Timer(true)
        downingTimer.schedule(object : TimerTask() {
            override fun run() {
                down()
            }
        }, _timeSpan, _timeSpan)

        //
        findViewById<Button>(R.id.button_move_left).setOnClickListener { view ->
            _currentBlock?.moveToLeft()
        }
        findViewById<Button>(R.id.button_move_right).setOnClickListener { view ->
            _currentBlock?.moveToRight()
        }
        findViewById<Button>(R.id.button_move_down).setOnClickListener { view ->
            down()
        }
    }

    private fun update() {
        _currentBlock = _currentBlock ?: _tetrisField.newBlock()

        runOnUiThread {
            for (y in 0 until _numY) {
                for (x in 0 until _numX) {
                    if (_tetrisField.array2d[y][x])
                        _buttons!![y][x].setBackgroundColor(Color.GRAY)
                    else
                        _buttons!![y][x].setBackgroundColor(Color.WHITE)
                }
            }

            for (p in _currentBlock?.positions() ?: arrayOf())
                _buttons!![p.y][p.x].setBackgroundColor(Color.BLUE)
        }
    }

    private fun down() {
        if (_currentBlock?.moveToDown()?.not() ?: false) {
            _currentBlock?.fixToField()
            _tetrisField.erase()
            _currentBlock = null
        }
    }

}

