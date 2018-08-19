package com.example.games

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.example.games.breakblocks.screen.BreakBlocksActivity
import com.example.games.tetris.screen.TetrisMenuActivity

class MainActivity : AppCompatActivity() {

    val tetris = "android.intent.action.games.TETRIS"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button_tetris).setOnClickListener { view ->
//            startActivity(Intent(this, TetrisMenuActivity::class.java))
            val data = Intent(tetris)
            startActivity(data)
        }
//        findViewById<Button>(R.id.button_breakblocks).setOnClickListener { view ->
//            startActivity(Intent(this, BreakBlocksActivity::class.java))
//        }
    }

}
