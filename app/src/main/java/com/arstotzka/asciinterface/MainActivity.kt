package com.arstotzka.asciinterface

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlin.concurrent.thread
import java.util.*


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val surfaceView = findViewById(R.id.surfaceView) as CustomSurfaceView

        val s = Scanner(resources.openRawResource(R.raw.texto))
        s.useDelimiter("")
        var x = 0
        var y = 0
        try {
            while (s.hasNext()) {
                val word = s.next()
                if (word != "\n")
                    surfaceView.setChatAtPos(word.toCharArray()[0], x, y)
                x++
                if (word == "\n") {
                    y++
                    x = 0
                }
            }
        } finally {
            s.close()
        }

        thread {
            while (true) {
                surfaceView.paint()
            }
        }
    }
}
