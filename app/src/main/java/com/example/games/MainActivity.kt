package com.example.games

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.games.tetris.screen.TetrisActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button_tetris).setOnClickListener { view ->
            startActivity(Intent(this, TetrisActivity::class.java))
        }
    }

}
