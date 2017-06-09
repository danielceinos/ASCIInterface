package com.arstotzka.asciinterface.views

import android.graphics.Rect
import android.view.MotionEvent
import com.arstotzka.asciinterface.CustomSurfaceView
import java.lang.Thread.sleep

/**
 * Created by Daniel S on 09/06/2017.
 */
class AsciiWindow {

    val width: Int
    val height: Int
    val surfaceView: CustomSurfaceView
    val bounds: Rect
    var view: AsciiView

    constructor(width: Int, height: Int, surfaceView: CustomSurfaceView, view: AsciiView) {
        this.width = width
        this.height = height
        this.surfaceView = surfaceView
        this.view = view
        view.window = this

        bounds = Rect(0, 0, width, height)
        view.rePaint()
    }

    fun onClick(event: MotionEvent?, x: Int, y: Int) {
        view.onClick(event, x, y)
    }

    fun refresh() {
        surfaceView.paint(view.mtx!!)
    }
}