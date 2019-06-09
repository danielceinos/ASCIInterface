package com.arstotzka.asciinterface.views

import android.graphics.Rect
import android.view.MotionEvent
import com.arstotzka.asciinterface.CustomSurfaceView

/**
 * Created by Daniel S on 09/06/2017.
 */
class AsciiWindow(width: Int, height: Int, private val surfaceView: CustomSurfaceView, var view: AsciiView) {

  private val bounds: Rect

  init {
    view.window = this
    bounds = Rect(0, 0, width, height)
    view.rePaint()
  }

  fun onClick(event: MotionEvent?, x: Int, y: Int) {
    val view = view.onClick(event, x, y).lastOrNull { it.onClickListener != null }
    view?.onClickListener?.onClickAsciiView(event, view)
  }

  fun refresh() {
    surfaceView.paint(view.mtx)
  }
}