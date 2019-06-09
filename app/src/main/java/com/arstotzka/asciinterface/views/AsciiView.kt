package com.arstotzka.asciinterface.views

import android.graphics.Rect
import android.view.MotionEvent

/**
 * Created by Daniel S on 30/05/2017.
 */
open class AsciiView(x: Int, y: Int, var width: Int, var height: Int) {

  var bounds: Rect
  var mtx: Array<CharArray>
  var childs: ArrayList<AsciiView> = ArrayList()
  var parent: AsciiView? = null
  var window: AsciiWindow? = null
  var onClickListener: OnClickListener? = null

  companion object {
    val TRANSPARENT_CHAR = '*'
  }

  init {
    bounds = Rect(x, y, width + x, height + y)
    mtx = Array(width) { CharArray(height) }
  }

  open fun clear() {
    for (x in 0 until width) {
      for (y in 0 until height) {
        setChar(x, y, ' ')
      }
    }
  }

  open fun addChild(child: AsciiView) {
    childs.add(child)
    child.parent = this
    val childMtx = child.mtx
    for (i in 0 until childMtx.size) {
      (0 until childMtx[i].size)
          .filter { childMtx[i][it] != TRANSPARENT_CHAR }
          .filter { i + child.bounds.left >= 0 && it + child.bounds.top >= 0 }
          .filter { i + child.bounds.left < mtx.size && it + child.bounds.top < mtx[0].size }
          .forEach { mtx[i + child.bounds.left][it + child.bounds.top] = childMtx[i][it] }
    }
  }

  open fun rePaint() {
    clear()
    for (child in childs) {
      val childMtx = child.mtx
      for (i in 0 until childMtx.size) {
        (0 until childMtx[i].size)
            .filter { childMtx[i][it] != TRANSPARENT_CHAR }
            .filter { i + child.bounds.left >= 0 && it + child.bounds.top >= 0 }
            .filter { i + child.bounds.left < mtx.size && it + child.bounds.top < mtx[0].size }
            .forEach { mtx[i + child.bounds.left][it + child.bounds.top] = childMtx[i][it] }
      }
    }
    parent?.rePaint()
    window?.refresh()
  }

  open fun setChar(x: Int, y: Int, char: Char) {
    mtx[x][y] = char
  }

  fun onClick(event: MotionEvent?, x: Int, y: Int): ArrayList<AsciiView> {
    val listView = ArrayList<AsciiView>()
    if (bounds.contains(x, y)) {
      listView.add(this)
      for (child in childs)
        listView.addAll(child.onClick(event, x - bounds.left, y - bounds.top))
    }
    return listView
  }

  open fun getX(): Int {
    return bounds.left
  }

  open fun getY(): Int {
    return bounds.top
  }

  open fun setX(x: Int) {
    bounds.left = x
  }

  open fun setY(y: Int) {
    bounds.top = y
  }

  open fun setTextLayout(textLayout: String) {
    var x = 0
    var y = 0
    for (char in textLayout) {
      if (char != '\n') {
        mtx[x][y] = char
        x++
      } else {
        y++
        x = 0
      }
    }
  }

  open fun moveTo(x: Int, y: Int) {
//        if (x != this.bounds?.centerX() || y != this.bounds?.centerY()) {
    val xx = parent!!.bounds.left
    val yy = parent!!.bounds.top
    bounds.offsetTo(x - xx, y - yy)

    parent?.rePaint()
//        }
  }
}