package com.example.games.tetris.screen

import android.content.Intent
import android.graphics.Color
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewTreeObserver
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import com.example.games.tetris.R
import com.example.games.tetris.model.TetrisField
import com.example.games.tetris.model.blocks.CompositeBlock
import java.util.*
import kotlin.math.roundToInt


class TetrisActivity : AppCompatActivity() {
    companion object {
        val TETRIS_ARGUMENTS_DROP_SPEED = "tetris_arguments_drop_speed"
        val TETRIS_ARGUMENTS_GUIDE_TYPE = "tetris_arguments_guide_type"
        val TETRIS_RESULT_ERASED_LINES = "tetris_result_erased_lines"
        val TETRIS_RESULT_SCORE = "tetris_result_score"

        private val TETRIS_FIELD_WIDTH = 10
        private val TETRIS_FIELD_HEIGHT = 18
        private val NEXT_BLOCK_WIDTH = 4
        private val NEXT_BLOCK_HEIGHT = 4
    }

    private val _tetrisField = TetrisField(TETRIS_FIELD_WIDTH, TETRIS_FIELD_HEIGHT, Random(Date().time))
    private var _currentBlock: CompositeBlock? = null
    private var _nextBlock: CompositeBlock? = null

    private var _dropSpeed = 0.0 // [0.0 ~ 1.0]
    private var _guideType = GuideType.NONE

    enum class GuideType(val value: Int) {
        NONE(0),
        GRID(1),
        PHANTOM(2)
    }

    private var _tetrisFieldSpaces = Array(0) { Array(0) { View(null) } }
    private var _nextBlockSpace = Array(0) { Array(0) { View(null) } }

    private var _erasedLines = 0
    private var _score = 0

    private var _lastDownedTimeMillis = 0L
    private var _drawingTimer: Timer? = null


    private var slideSound: MediaPlayer? = null
    private var transformSound: MediaPlayer? = null
    private var falldownSound: MediaPlayer? = null
    private var eraseSound: MediaPlayer? = null
    private var gameoverSound: MediaPlayer? = null

    private fun toGuideType(value: Int): GuideType {
        return when (value) {
            GuideType.GRID.value -> GuideType.GRID
            GuideType.PHANTOM.value -> GuideType.PHANTOM
            else -> GuideType.NONE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tetris)

        _dropSpeed = intent.getDoubleExtra(TETRIS_ARGUMENTS_DROP_SPEED, 0.0)
        _guideType = toGuideType(intent.getIntExtra(TETRIS_ARGUMENTS_GUIDE_TYPE, GuideType.NONE.value))

        _currentBlock = _currentBlock ?: _nextBlock ?: _tetrisField.newBlock()
        _nextBlock = _tetrisField.newBlock()
        _lastDownedTimeMillis = System.currentTimeMillis()

        initTetrisField()
        initNextBlock()

        _gestureDetector = GestureDetector(this, _onGestureListener)

//        findViewById<TextView>(R.id.textView_value_drop_speed).setText((_dropSpeed * 10).toInt().toString())
        findViewById<TextView>(R.id.textView_value_drop_speed).setText("%d [msec/drop]".format(toTimeSpan(_dropSpeed)))
        findViewById<TextView>(R.id.textView_value_erased_lines).setText(_erasedLines.toString())
        findViewById<TextView>(R.id.textView_value_score).setText(_score.toString())

        slideSound = MediaPlayer.create(applicationContext, R.raw.slide)
        transformSound = MediaPlayer.create(applicationContext, R.raw.transform)
        falldownSound = MediaPlayer.create(applicationContext, R.raw.falldown)
        eraseSound = MediaPlayer.create(applicationContext, R.raw.erase)
        gameoverSound = MediaPlayer.create(applicationContext, R.raw.gameover)
    }

    override fun onResume() {
        super.onResume()
        resumeTimer()
        setVolumeControlStream(AudioManager.STREAM_MUSIC)
    }

    override fun onPause() {
        super.onPause()
        pauseTimer()
    }

    private fun initTetrisField() {
        _tetrisFieldSpaces = Array(TETRIS_FIELD_HEIGHT) { Array(TETRIS_FIELD_WIDTH) { View(this) } }
        initTableLayout(_tetrisFieldSpaces, R.id.tableLayout, R.id.layout_contents_game)
    }

    private fun initNextBlock() {
        _nextBlockSpace = Array(NEXT_BLOCK_HEIGHT) { Array(NEXT_BLOCK_WIDTH) { View(this) } }
        initTableLayout(_nextBlockSpace, R.id.tableLayout_next_block, R.id.tableLayout_next_block)
    }

    private fun initTableLayout(spaces: Array<Array<View>>, tableLayoutId: Int, layoutContents: Int) {
        val height = spaces.size
        val width = spaces[0].size

        val tableLayout = findViewById<TableLayout>(tableLayoutId)
        val layoutParams = TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT)
        for (y in 0 until height) {
            val tableRow = TableRow(this)
            tableRow.setBackgroundColor(Color.LTGRAY)
            for (x in 0 until width)
                tableRow.addView(spaces[y][x])
            tableLayout.addView(tableRow, layoutParams)
        }

        val layout = findViewById<View>(layoutContents);
        layout.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                layout.viewTreeObserver.removeOnGlobalLayoutListener(this)
                val viewSize = Math.min(layout.measuredWidth / width, layout.measuredHeight / height)
                val layoutParams = makeTableRowLayoutParams(_guideType, viewSize)
                for (y in 0 until height)
                    for (x in 0 until width)
                        spaces[y][x].layoutParams = layoutParams
            }
        })
    }

    private fun makeTableRowLayoutParams(guideType: GuideType, viewSize: Int): TableRow.LayoutParams {
        return when (guideType) {
            GuideType.GRID -> makeMarginedLayout(viewSize)
            else -> TableRow.LayoutParams(viewSize, viewSize)
        }
    }

    private fun makeMarginedLayout(viewSize: Int): TableRow.LayoutParams {
        val marginSize = 1
        val layoutParams = TableRow.LayoutParams(viewSize - marginSize * 2, viewSize - marginSize * 2)
        layoutParams.setMargins(marginSize, marginSize, marginSize, marginSize)
        return layoutParams
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

                        if (System.currentTimeMillis() - _lastDownedTimeMillis > toTimeSpan(_dropSpeed))
                            downBlock()

                        drawBlocks()

                        if (canErase()) {
                            eraseLines()
                        }

                        if (isGameOver()) {
                            playSoundEffect(gameoverSound)

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

    private fun toTimeSpan(dropSpeed: Double): Long {
        val max = 2500.0
        val min = 250.0
        return (max - (max - min) * dropSpeed).toLong()
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
                if (_currentBlock!!.moveToLeft())
                    playSoundEffect(slideSound)
                return true;
            }

            if (didFlickToRight(event1, event2, velocityX, velocityY)) {
                if (_currentBlock!!.moveToRight())
                    playSoundEffect(slideSound)
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
            if (_currentBlock?.rotateRight() ?: false)
                playSoundEffect(transformSound)
            return true
        }

        override fun onDoubleTap(e: MotionEvent?): Boolean {
            if (_currentBlock?.rotateRight() ?: false)
                playSoundEffect(transformSound)
            return true
        }
    }

    private val SLEEP_TIME_FOR_ERASE = 250L

    private fun drawBlocks() {
        runOnUiThread {
            for (y in 0 until _tetrisField.height)
                for (x in 0 until _tetrisField.width)
                    _tetrisFieldSpaces[y][x].setBackgroundColor(colorOf(_tetrisField.space(y, x)))

            if (_guideType == GuideType.PHANTOM) {
                for (p in _currentBlock?.phantomPositions() ?: arrayOf())
                    _tetrisFieldSpaces[p.y][p.x].setBackgroundColor(getColor(R.color.colorPhantomBlock))
            }

            for (p in _currentBlock?.positions() ?: arrayOf())
                _tetrisFieldSpaces[p.y][p.x].setBackgroundColor(colorOf(_currentBlock?.space))

            for (y in 0 until NEXT_BLOCK_HEIGHT)
                for (x in 0 until NEXT_BLOCK_WIDTH)
                    _nextBlockSpace[y][x].setBackgroundColor(Color.WHITE)
            for (p in _nextBlock?.shape() ?: arrayOf())
                _nextBlockSpace[p.y][p.x].setBackgroundColor(colorOf(_nextBlock?.space))
        }
    }

    private val SCORE_TABLE = intArrayOf(0, 10, 100, 1000, 10000)
    private fun calculateScore(erasedLines: Int): Int {
        return (SCORE_TABLE[erasedLines] * (0.5 + 0.5 * _dropSpeed)).roundToInt()
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

        playSoundEffect(falldownSound)

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

        playSoundEffect(eraseSound)

        Thread.sleep(SLEEP_TIME_FOR_ERASE)

        runOnUiThread {
            for (y in 0 until _tetrisField.height) {
                if (erased.contains(y)) {
                    for (x in 0 until _tetrisField.width)
                        _tetrisFieldSpaces[y][x].setBackgroundColor(Color.WHITE)
                } else {
                    for (x in 0 until _tetrisField.width)
                        _tetrisFieldSpaces[y][x].setBackgroundColor(colorOf(_tetrisField.space(y, x)))
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
                    _tetrisFieldSpaces!![y][x].setBackgroundColor(Color.GRAY)
            }
        }

        _drawingTimer!!.cancel()
    }

    private fun playSoundEffect(sound: MediaPlayer?) {
        if (sound == null)
            return
        if (sound.isPlaying ?: false) {
            sound.pause()
            sound.seekTo(0)
        }
        sound.start()
    }

}

