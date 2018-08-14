package com.example.games.tetris.screen

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.example.games.R

class TetrisMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tetris_menu)

        findViewById<Button>(R.id.button_start).setOnClickListener { view ->
            startActivity(Intent(this, TetrisActivity::class.java))
        }
    }

}
