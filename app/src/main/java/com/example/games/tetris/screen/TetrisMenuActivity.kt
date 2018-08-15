package com.example.games.tetris.screen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import com.example.games.R


class TetrisMenuActivity : AppCompatActivity() {

    private val TETRIS_ACTIVITY = 100

    private val SPEED_TABLE = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tetris_menu)

        val spinner = findViewById<Spinner>(R.id.spinner_value_speed)
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item)
        for (speed in SPEED_TABLE)
            adapter.add(speed.toString())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        findViewById<Button>(R.id.button_start).setOnClickListener { view ->
            val selectedNumber = SPEED_TABLE[spinner.selectedItemPosition.toInt()].toDouble()

            val data = Intent(this, TetrisActivity::class.java)
            data.putExtra(TetrisActivity.TETRIS_ARGUMENTS_DROP_SPEED, selectedNumber / SPEED_TABLE.size)
            startActivityForResult(data, TETRIS_ACTIVITY)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            TETRIS_ACTIVITY -> {
                if (resultCode === Activity.RESULT_OK) {
                    val erasedLines = data?.getIntExtra(TetrisActivity.TETRIS_RESULT_ERASED_LINES, 0)
                    val score = data?.getIntExtra(TetrisActivity.TETRIS_RESULT_SCORE, 0)
                    findViewById<TextView>(R.id.textView_value_erased_lines).setText(erasedLines?.toString())
                    findViewById<TextView>(R.id.textView_value_score).setText(score?.toString())
                }
            }
            else -> {
            }
        }
    }

}
