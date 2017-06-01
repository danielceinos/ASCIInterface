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
        try {
            while (s.hasNext()) {
                val word = s.next()
                viewTextLayout += word
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
