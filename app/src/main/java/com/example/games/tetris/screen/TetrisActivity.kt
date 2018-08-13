package com.example.games.tetris.screen

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
import com.example.games.model.Field
import com.example.games.model.blocks.CompositeBlock
import java.util.*


class TetrisActivity : AppCompatActivity() {

    private val _numX = 10
    private val _numY = 18
    private val _timeSpan = 1000L
    private val _tetrisField = Field(_numX, _numY, Random(Date().time))
    private var _currentBlock: CompositeBlock? = null
    private var _nextBlock: CompositeBlock? = null
    private var _spaces = Array(0) { Array(0) { View(null) } }
    private var _blockSize = 0

    private var _score = 0
    private var _erasedLines = 0

    //
    private var _drawingTimer: Timer? = null
    private var _downingTimer: Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        supportActionBar?.hide();

        setContentView(R.layout.activity_tetris)

        initLayoutTetrisField()
        initLayoutNextBlock()

        _gestureDetector = GestureDetector(this, _onGestureListener)


        _currentBlock = _currentBlock ?: _nextBlock ?: _tetrisField.newBlock()
        _nextBlock = _tetrisField.newBlock()


        findViewById<TextView>(R.id.textView_value_score).setText(_score.toString())
        findViewById<TextView>(R.id.textView_value_erased_lines).setText(_erasedLines.toString())
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
        _spaces = Array(_numY) { Array(_numX) { View(this) } }

        val displaySize = Point()
        windowManager.defaultDisplay.getSize(displaySize)
        _blockSize = 1;//Math.min(displaySize.x / _numX, (displaySize.y - HEIGHT_OF_TITLE_BAR) / _numY)

        val tableLayout = findViewById<TableLayout>(R.id.tableLayout)
        for (y in 0 until _numY) {
            val tableRow = TableRow(this)
            tableRow.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT)
            for (x in 0 until _numX) {
                _spaces!![y][x].layoutParams = TableRow.LayoutParams(_blockSize, _blockSize)
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
                _blockSize = Math.min(width / _numX, height / _numY)
                for (y in 0 until _numY)
                    for (x in 0 until _numX)
                        _spaces[y][x].layoutParams = TableRow.LayoutParams(_blockSize, _blockSize);
            }
        })
    }

    private val _nextBlockNumX = 4
    private val _nextBlockNumY = 4
    private val _nextBlockSize = 1
    private var _nextBlockSpace = Array(0) { Array(0) { View(null) } }

    private fun initLayoutNextBlock() {
        _nextBlockSpace = Array(_nextBlockNumY) { Array(_nextBlockNumX) { View(this) } }

        val tableLayout = findViewById<TableLayout>(R.id.tableLayout_next_block)
        for (y in 0 until _nextBlockNumY) {
            val tableRow = TableRow(this)
            tableRow.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT)
            for (x in 0 until _nextBlockNumX) {
                _nextBlockSpace[y][x].layoutParams = TableRow.LayoutParams(_nextBlockSize, _nextBlockSize)
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
                val blockSize = Math.min(width / _nextBlockNumX, height / _nextBlockNumY)
                for (y in 0 until _nextBlockNumY)
                    for (x in 0 until _nextBlockNumX)
                        _nextBlockSpace[y][x].layoutParams = TableRow.LayoutParams(blockSize, blockSize);
            }
        })
    }

    private fun resumeTimer() {
        // タイマーの初期化処理
        _drawingTimer = Timer()
        _drawingTimer!!.schedule(
                object : TimerTask() {
                    override fun run() {
                        update()
                    }
                }, 0, 100)

        //
        _downingTimer = Timer()
        _downingTimer!!.schedule(
                object : TimerTask() {
                    override fun run() {
                        down()
                    }
                }, _timeSpan, _timeSpan)
    }

    private fun pauseTimer() {
        _drawingTimer!!.cancel()
        _downingTimer!!.cancel()
    }

    private var _gestureDetector: GestureDetector? = null

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (_gestureDetector!!.onTouchEvent(event)) {
            return true;
        }
        return super.onTouchEvent(event);
    }

    private val FLICK_THRESHOLD_DISTANCE = _blockSize

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

    private val SLEEP_TIME_FOR_ERASE = 500L

    private fun update() {
        runOnUiThread {
            for (y in 0 until _numY)
                for (x in 0 until _numX)
                    _spaces[y][x].setBackgroundColor(colorOf(_tetrisField.array2d[y][x]))

            for (p in _currentBlock?.positions() ?: arrayOf())
                _spaces[p.y][p.x].setBackgroundColor(colorOf(_currentBlock?.space))

            for (y in 0 until _nextBlockNumY)
                for (x in 0 until _nextBlockNumX)
                    _nextBlockSpace[y][x].setBackgroundColor(Color.WHITE)
            for (p in _nextBlock?.shape() ?: arrayOf())
                _nextBlockSpace[p.y][p.x].setBackgroundColor(colorOf(_nextBlock?.space))
        }

        val erased = _tetrisField.erasedLines()
        if (erased.size > 0) {
            Thread.sleep(SLEEP_TIME_FOR_ERASE)

            _score += calculateScore(erased.size)
            _erasedLines += erased.size
            runOnUiThread {
                findViewById<TextView>(R.id.textView_value_score).setText(_score.toString())
                findViewById<TextView>(R.id.textView_value_erased_lines).setText(_erasedLines.toString())
            }

            _tetrisField.erase()
        }

        if (_currentBlock == null) {
            _currentBlock = _currentBlock ?: _nextBlock ?: _tetrisField.newBlock()
            _nextBlock = _tetrisField.newBlock()

            if (_tetrisField.array2d[0].any { space -> space != Field.Space.EMPTY }) {
                showGameOver()
            }
        }
    }

    private val SCORE_TABLE = intArrayOf(1, 10, 100, 1000, 10000)
    private fun calculateScore(erasedLines: Int): Int {
        return SCORE_TABLE[erasedLines]
    }

    private fun colorOf(blockType: Field.Space?): Int {
        return when (blockType) {
            Field.Space.RECTANGLE -> Color.DKGRAY
            Field.Space.STRAIGHT -> Color.RED
            Field.Space.GAP_LEFT -> Color.MAGENTA
            Field.Space.GAP_RIGHT -> Color.YELLOW
            Field.Space.HOOK_LEFT -> Color.GREEN
            Field.Space.HOOK_RIGHT -> Color.BLUE
            Field.Space.HOOK_CENTER -> Color.CYAN
            else -> Color.WHITE
        }
    }

    private fun down() {
        if (_currentBlock?.moveToDown()?.not() ?: false) {
            _currentBlock?.fixToField()

            _currentBlock = null
        }
    }

    private fun downToGround() {
        while (_currentBlock?.moveToDown() ?: false) {
        }
        down()
    }

    private fun showGameOver() {
        runOnUiThread {
            for (y in 0 until _numY)
                for (x in 0 until _numX)
                    _spaces!![y][x].setBackgroundColor(Color.GRAY)
        }

        _drawingTimer!!.cancel()
        _downingTimer!!.cancel()
    }

}

