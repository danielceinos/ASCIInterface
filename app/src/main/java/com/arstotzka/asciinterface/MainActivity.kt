package com.arstotzka.asciinterface

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.arstotzka.asciinterface.views.AsciiView
import java.util.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val surfaceView = findViewById(R.id.surfaceView) as CustomSurfaceView

        val view1 = AsciiView(0, 0, null)
        var viewTextLayout = ""
        val s = Scanner(resources.openRawResource(R.raw.view1))
        s.useDelimiter("")
        var x = 0
        var y = 0
        try {
            while (s.hasNext()) {
                val word = s.next()
                viewTextLayout += word
//                if (word != "\n")
//                    surfaceView.setChatAtPos(word.toCharArray()[0], x, y)
//                x++
//                if (word == "\n") {
//                    y++
//                    x = 0
//                }
            }
        } finally {

            s.close()
        }
        view1.setTextLayout(viewTextLayout)
        surfaceView.map = view1.mtx

        thread {
            while (true) {
                surfaceView.paint()
            }
        }
    }
}
