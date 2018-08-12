package com.example.games.tetris.screen

import android.graphics.Color
import android.graphics.Point
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewTreeObserver
import android.widget.TableLayout
import android.widget.TableRow
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
    private var _spaces = Array(0) { Array(0) { View(null) } }
    private var _blockSize = 0
    private val HEIGHT_OF_TITLE_BAR = 120

    //
    private val _drawingTimer = Timer(true)
    private val _downingTimer = Timer(true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        supportActionBar?.hide();

        setContentView(R.layout.activity_tetris)

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

        // タイマーの初期化処理
        _drawingTimer.schedule(
                object : TimerTask() {
                    override fun run() {
                        update()
                    }
                }, 0, 100)

        //
        _downingTimer.schedule(
                object : TimerTask() {
                    override fun run() {
                        down()
                    }
                }, _timeSpan, _timeSpan)

        _gestureDetector = GestureDetector(this, _onGestureListener)
    }

    private
    var _gestureDetector: GestureDetector? = null

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (_gestureDetector!!.onTouchEvent(event)) {
            return true;
        }
        return super.onTouchEvent(event);
    }

    private
    val FLICK_THRESHOLD_DISTANCE = _blockSize / 2

    // タッチイベントのリスナー
    private
    val _onGestureListener = object : GestureDetector.SimpleOnGestureListener() {
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

    private fun update() {
        _currentBlock = _currentBlock ?: _tetrisField.newBlock()

        runOnUiThread {
            for (y in 0 until _numY) {
                for (x in 0 until _numX) {
                    when (_tetrisField.array2d[y][x]) {
                        Field.Space.EMPTY -> _spaces!![y][x].setBackgroundColor(Color.WHITE)
                        Field.Space.RECTANGLE -> _spaces!![y][x].setBackgroundColor(Color.DKGRAY)
                        Field.Space.STRAIGHT -> _spaces!![y][x].setBackgroundColor(Color.RED)
                        Field.Space.GAP_LEFT -> _spaces!![y][x].setBackgroundColor(Color.MAGENTA)
                        Field.Space.GAP_RIGHT -> _spaces!![y][x].setBackgroundColor(Color.YELLOW)
                        Field.Space.HOOK_LEFT -> _spaces!![y][x].setBackgroundColor(Color.GREEN)
                        Field.Space.HOOK_RIGHT -> _spaces!![y][x].setBackgroundColor(Color.BLUE)
                        Field.Space.HOOK_CENTER -> _spaces!![y][x].setBackgroundColor(Color.CYAN)
                    }
                }
            }

            for (p in _currentBlock?.positions() ?: arrayOf()) {
                when (_currentBlock?.space) {
                    Field.Space.RECTANGLE -> _spaces!![p.y][p.x].setBackgroundColor(Color.DKGRAY)
                    Field.Space.STRAIGHT -> _spaces!![p.y][p.x].setBackgroundColor(Color.RED)
                    Field.Space.GAP_LEFT -> _spaces!![p.y][p.x].setBackgroundColor(Color.MAGENTA)
                    Field.Space.GAP_RIGHT -> _spaces!![p.y][p.x].setBackgroundColor(Color.YELLOW)
                    Field.Space.HOOK_LEFT -> _spaces!![p.y][p.x].setBackgroundColor(Color.GREEN)
                    Field.Space.HOOK_RIGHT -> _spaces!![p.y][p.x].setBackgroundColor(Color.BLUE)
                    Field.Space.HOOK_CENTER -> _spaces!![p.y][p.x].setBackgroundColor(Color.CYAN)
                }
            }
        }
    }

    private fun down() {
        if (_currentBlock?.moveToDown()?.not() ?: false) {
            _currentBlock?.fixToField()
            _tetrisField.erase()
            _currentBlock = null

            if (_tetrisField.array2d[0].any { space -> space != Field.Space.EMPTY }) {
                showGameOver()
            }
        }
    }

    private fun downToGround() {
        while (_currentBlock?.moveToDown() ?: false) {
        }
        down()
    }

    private fun showGameOver() {
        _drawingTimer.cancel()
        _downingTimer.cancel()

        runOnUiThread {
            for (y in 0 until _numY) {
                for (x in 0 until _numX) {
                    _spaces!![y][x].setBackgroundColor(Color.GRAY)
                }
            }
        }
    }

}

