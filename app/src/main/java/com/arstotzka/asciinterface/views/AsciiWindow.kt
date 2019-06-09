package com.arstotzka.asciinterface.views

import android.graphics.Rect
import android.view.MotionEvent
import com.arstotzka.asciinterface.CustomSurfaceView

/**
 * Created by Daniel S on 09/06/2017.
 */
class AsciiWindow(width: Int, height: Int, private val surfaceView: CustomSurfaceView) {

    private val bounds: Rect = Rect(0, 0, width, height)

    var view: AsciiView? = null
        set(value) {
            value?.window = this
            field = value
            field?.rePaint()
        }

    fun onClick(event: MotionEvent?, x: Int, y: Int) {
        view?.onClick(event, x, y)
    }

    fun refresh() {
        view?.let {
            surfaceView.paint(it.mtx)
        }
    }
}