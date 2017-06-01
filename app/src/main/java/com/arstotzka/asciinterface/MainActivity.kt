package com.arstotzka.asciinterface

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.arstotzka.asciinterface.views.Button
import java.util.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val surfaceView = findViewById(R.id.surfaceView) as CustomSurfaceView

        var b = Button("boton padre", 0, 0, 40, 29, null)
        Button(":3", 1, 1, 15, 7, b)
        Button("holi ", 3, 4, 11, 5, b)
        Button("pulsa", 9, 15, 11, 5, b)
        surfaceView.map = b.mtx

        thread {
            while (true) {
                surfaceView.paint()
            }
        }
    }
}
