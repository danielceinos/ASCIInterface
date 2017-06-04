package com.arstotzka.asciinterface.views

import android.graphics.Rect
import android.util.Log

/**
 * Created by Daniel S on 30/05/2017.
 */
open class AsciiView {

    var width: Int
    var height: Int
    var bounds: Rect? = null
    var mtx: Array<CharArray>?
    private var childs: ArrayList<AsciiView> = ArrayList()
    var parent: AsciiView? = null
    var onClickListener: OnClickListener? = null
    val TRANSPARENT_CHAR = '*'

    constructor(x: Int, y: Int, width: Int, height: Int) {
        this.width = width
        this.height = height

        bounds = Rect(x, y, width + x, height + y)
        mtx = Array(width) { CharArray(height) }
    }

    open fun paint() {
        for (x in 0..width - 1) {
            for (y in 0..height - 1) {
                setChar(x, y, ' ')
            }
        }
    }

    fun addChild(child: AsciiView) {
        childs.add(child)
        child.parent = this
        val childMtx = child.mtx
        for (i in 0..childMtx!!.size - 1) {
            (0..childMtx[i].size - 1)
                    .filter { childMtx[i][it] != TRANSPARENT_CHAR }
                    .forEach { mtx!![i + child.bounds!!.left][it + child.bounds!!.top] = childMtx[i][it] }
        }
    }

    open fun refresh() {
        paint()
        for (child in childs) {
            val childMtx = child.mtx
            for (i in 0..childMtx!!.size - 1) {
                (0..childMtx[i].size - 1)
                        .filter { childMtx[i][it] != TRANSPARENT_CHAR }
                        .filter {i + child.bounds!!.left >= 0 && it + child.bounds!!.top >= 0  }
                        .filter {i + child.bounds!!.left < mtx!!.size && it + child.bounds!!.top < mtx!![0].size  }
                        .forEach { mtx!![i + child.bounds!!.left][it + child.bounds!!.top] = childMtx[i][it] }
            }
        }
    }

    open fun setChar(x: Int, y: Int, char: Char) {
        mtx!![x][y] = char
    }

    fun onClick(x: Int, y: Int) {

        if (bounds?.contains(x, y)!!) {
            val childClicked: AsciiView? = childs.lastOrNull { it.onClickListener != null && it.bounds!!.contains(x, y) }
            childClicked?.onClickListener?.onClick(childClicked)
            if (childClicked == null)
                onClickListener?.onClick(this)
        }

    }

    open fun setTextLayout(textLayout: String) {
        var x = 0
        var y = 0
        for (char in textLayout) {
            if (char != '\n') {
                mtx!![x][y] = char
                x++
            } else {
                y++
                x = 0
            }
        }
    }
}