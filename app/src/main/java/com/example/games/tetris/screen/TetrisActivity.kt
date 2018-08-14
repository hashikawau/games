package com.example.games.tetris.screen

import android.content.Intent
import android.graphics.Color
import android.graphics.Point
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewTreeObserver
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import com.example.games.R
import com.example.games.model.TetrisField
import com.example.games.model.blocks.CompositeBlock
import java.util.*
import kotlin.math.roundToInt


class TetrisActivity : AppCompatActivity() {
    companion object {
        val TETRIS_ARGUMENTS_SPEED = "tetris_arguments_speed"
        val TETRIS_RESULT_ERASED_LINES = "tetris_result_erased_lines"
        val TETRIS_RESULT_SCORE = "tetris_result_score"

        private val WIDTH = 10
        private val HEIGHT = 18
    }

    private val _tetrisField = TetrisField(WIDTH, HEIGHT, Random(Date().time))

    private var _speed = 0.0 // [0.0 ~ 1.0]

    private var _currentBlock: CompositeBlock? = null
    private var _nextBlock: CompositeBlock? = null
    private var _spaces = Array(0) { Array(0) { View(null) } }

    private var _erasedLines = 0
    private var _score = 0

    private var _lastDownedTimeMillis = 0L
    private var _drawingTimer: Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tetris)

        _speed = intent.getDoubleExtra(TETRIS_ARGUMENTS_SPEED, 0.0)
        _lastDownedTimeMillis = System.currentTimeMillis()

        initLayoutTetrisField()
        initLayoutNextBlock()

        _gestureDetector = GestureDetector(this, _onGestureListener)

        _currentBlock = _currentBlock ?: _nextBlock ?: _tetrisField.newBlock()
        _nextBlock = _tetrisField.newBlock()

        findViewById<TextView>(R.id.textView_value_erased_lines).setText(_erasedLines.toString())
        findViewById<TextView>(R.id.textView_value_score).setText(_score.toString())
    }

    override fun onResume() {
        super.onResume()
        resumeTimer()
    }

    override fun onPause() {
        super.onPause()
        pauseTimer()
    }

    private fun initLayoutTetrisField() {
        _spaces = Array(_tetrisField.height) { Array(_tetrisField.width) { View(this) } }

        val displaySize = Point()
        windowManager.defaultDisplay.getSize(displaySize)

        val tableLayout = findViewById<TableLayout>(R.id.tableLayout)
        for (y in 0 until _tetrisField.height) {
            val tableRow = TableRow(this)
            tableRow.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT)
            for (x in 0 until _tetrisField.width) {
                _spaces!![y][x].layoutParams = TableRow.LayoutParams()
                tableRow.addView(_spaces!![y][x])
            }
            tableLayout.addView(tableRow, TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT))
        }

        val layout = findViewById<View>(R.id.layout_contents_game);
        layout.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                layout.viewTreeObserver.removeOnGlobalLayoutListener(this)
                val width = layout.measuredWidth;
                val height = layout.measuredHeight;
                val viewSize = Math.min(width / _tetrisField.width, height / _tetrisField.height)
                for (y in 0 until _tetrisField.height)
                    for (x in 0 until _tetrisField.width)
                        _spaces[y][x].layoutParams = TableRow.LayoutParams(viewSize, viewSize);
            }
        })
    }

    private val _nextBlockNumX = 4
    private val _nextBlockNumY = 4
//    private val _nextBlockSize = 1
    private var _nextBlockSpace = Array(0) { Array(0) { View(null) } }

    private fun initLayoutNextBlock() {
        _nextBlockSpace = Array(_nextBlockNumY) { Array(_nextBlockNumX) { View(this) } }

        val tableLayout = findViewById<TableLayout>(R.id.tableLayout_next_block)
        for (y in 0 until _nextBlockNumY) {
            val tableRow = TableRow(this)
            tableRow.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT)
            for (x in 0 until _nextBlockNumX) {
                _nextBlockSpace[y][x].layoutParams = TableRow.LayoutParams()
                tableRow.addView(_nextBlockSpace[y][x])
            }
            tableLayout.addView(tableRow, TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT))
        }

        val layout = findViewById<View>(R.id.tableLayout_next_block);
        layout.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                layout.viewTreeObserver.removeOnGlobalLayoutListener(this)
                val width = layout.measuredWidth;
                val height = layout.measuredHeight;
                val viewSize = Math.min(width / _nextBlockNumX, height / _nextBlockNumY)
                for (y in 0 until _nextBlockNumY)
                    for (x in 0 until _nextBlockNumX)
                        _nextBlockSpace[y][x].layoutParams = TableRow.LayoutParams(viewSize, viewSize);
            }
        })
    }

    private fun resumeTimer() {
        // タイマーの初期化処理
        _drawingTimer = Timer()
        _drawingTimer!!.schedule(
                object : TimerTask() {
                    override fun run() {
                        if (_currentBlock == null && canErase().not() && isGameOver().not()) {
                            _currentBlock = _nextBlock ?: _tetrisField.newBlock()
                            _nextBlock = _tetrisField.newBlock()
                        }

                        if (System.currentTimeMillis() - _lastDownedTimeMillis > toTimeSpan(_speed))
                            downBlock()

                        drawBlocks()

                        if (canErase()) {
                            eraseLines()
                        }

                        if (isGameOver()) {
                            showGameOver()
                            Thread.sleep(500)

                            val data = Intent()
                            data.putExtra(TETRIS_RESULT_ERASED_LINES, _erasedLines)
                            data.putExtra(TETRIS_RESULT_SCORE, _score)
                            setResult(RESULT_OK, data);
                            finish()
                        }
                    }
                }, 0, 50)
    }

    private fun toTimeSpan(speed: Double): Long {
        val max = 1500
        val min = 500
        return (max - (max - min) * speed).toLong()
    }

    private fun pauseTimer() {
        _drawingTimer!!.cancel()
    }

    private var _gestureDetector: GestureDetector? = null

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (_gestureDetector!!.onTouchEvent(event)) {
            return true;
        }
        return super.onTouchEvent(event);
    }

    private val FLICK_THRESHOLD_DISTANCE = 30

    // タッチイベントのリスナー
    private val _onGestureListener = object : GestureDetector.SimpleOnGestureListener() {
        // フリックイベント
        override fun onFling(event1: MotionEvent, event2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            if (_currentBlock == null)
                return false

            if (Math.abs(event1.x - event2.x) < FLICK_THRESHOLD_DISTANCE && Math.abs(event1.y - event2.y) < FLICK_THRESHOLD_DISTANCE)
                return false

            if (didFlickToLeft(event1, event2, velocityX, velocityY)) {
                _currentBlock!!.moveToLeft();
                return true;
            }

            if (didFlickToRight(event1, event2, velocityX, velocityY)) {
                _currentBlock!!.moveToRight();
                return true;
            }

            if (didFlickToDown(event1, event2, velocityX, velocityY)) {
                downToGround()
                return true
            }

            return false
        }

        private fun didFlickToLeft(event1: MotionEvent, event2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            return isFlickHorizontal(velocityX, velocityY) && velocityX < 0
        }

        private fun didFlickToRight(event1: MotionEvent, event2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            return isFlickHorizontal(velocityX, velocityY) && velocityX > 0
        }

        private fun didFlickToDown(event1: MotionEvent, event2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            return isFlickVertical(velocityX, velocityY) && velocityY > 0
        }

        private fun isFlickHorizontal(velocityX: Float, velocityY: Float): Boolean {
            return Math.abs(velocityX) > Math.abs(velocityY)
        }

        private fun isFlickVertical(velocityX: Float, velocityY: Float): Boolean {
            return isFlickHorizontal(velocityX, velocityY).not()
        }

        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            _currentBlock?.rotateRight()
//            _currentBlock?.rotateLeft()
            return true
        }
    }

    private val SLEEP_TIME_FOR_ERASE = 250L

    private fun drawBlocks() {
        runOnUiThread {
            for (y in 0 until _tetrisField.height)
                for (x in 0 until _tetrisField.width)
                    _spaces[y][x].setBackgroundColor(colorOf(_tetrisField.space(y,x)))

            for (p in _currentBlock?.positions() ?: arrayOf())
                _spaces[p.y][p.x].setBackgroundColor(colorOf(_currentBlock?.space))

            for (y in 0 until _nextBlockNumY)
                for (x in 0 until _nextBlockNumX)
                    _nextBlockSpace[y][x].setBackgroundColor(Color.WHITE)
            for (p in _nextBlock?.shape() ?: arrayOf())
                _nextBlockSpace[p.y][p.x].setBackgroundColor(colorOf(_nextBlock?.space))
        }
    }

    private val SCORE_TABLE = intArrayOf(0, 10, 100, 1000, 10000)
    private fun calculateScore(erasedLines: Int): Int {
        return (SCORE_TABLE[erasedLines] * (0.5 + 0.5 * _speed)).roundToInt()
    }

    private fun colorOf(blockType: TetrisField.Space?): Int {
        return when (blockType) {
            TetrisField.Space.RECTANGLE -> Color.DKGRAY
            TetrisField.Space.STRAIGHT -> Color.RED
            TetrisField.Space.GAP_LEFT -> Color.YELLOW
            TetrisField.Space.GAP_RIGHT -> Color.MAGENTA
            TetrisField.Space.HOOK_LEFT -> Color.BLUE
            TetrisField.Space.HOOK_RIGHT -> Color.GREEN
            TetrisField.Space.HOOK_CENTER -> Color.CYAN
            else -> Color.WHITE
        }
    }

    private fun downBlock() {
        _lastDownedTimeMillis = System.currentTimeMillis()

        if (_currentBlock?.moveToDown() ?: true)
            return

        _currentBlock?.fixToField()
        _currentBlock = null
    }

    private fun downToGround() {
        while (_currentBlock?.moveToDown() ?: false) {
        }
        downBlock()
    }

    private fun canErase(): Boolean {
        return _tetrisField.erasedLines().size > 0
    }

    private fun eraseLines() {
        val erased = _tetrisField.erasedLines()

        Thread.sleep(SLEEP_TIME_FOR_ERASE)

        runOnUiThread {
            for (y in 0 until _tetrisField.height) {
                if (erased.contains(y)) {
                    for (x in 0 until _tetrisField.width)
                        _spaces[y][x].setBackgroundColor(Color.WHITE)
                } else {
                    for (x in 0 until _tetrisField.width)
                        _spaces[y][x].setBackgroundColor(colorOf(_tetrisField.space(y, x)))
                }
            }
        }

        Thread.sleep(SLEEP_TIME_FOR_ERASE)

        _erasedLines += erased.size
        _score += calculateScore(erased.size)
        runOnUiThread {
            findViewById<TextView>(R.id.textView_value_erased_lines).setText(_erasedLines.toString())
            findViewById<TextView>(R.id.textView_value_score).setText(_score.toString())
        }

        _tetrisField.erase()
        _lastDownedTimeMillis = System.currentTimeMillis()
    }

    private fun isGameOver(): Boolean {
        return _tetrisField.isGameOver()
    }

    private fun showGameOver() {
        for (y in _tetrisField.height - 1 downTo 0) {
            Thread.sleep(25)
            runOnUiThread {
                for (x in 0 until _tetrisField.width)
                    _spaces!![y][x].setBackgroundColor(Color.GRAY)
            }
        }

        _drawingTimer!!.cancel()
    }

}

