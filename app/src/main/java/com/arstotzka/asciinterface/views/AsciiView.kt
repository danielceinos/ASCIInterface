package com.arstotzka.asciinterface.views

import android.graphics.Rect

/**
 * Created by Daniel S on 30/05/2017.
 */
open class AsciiView {

    var x: Int
    var y: Int
    var width: Int
    var height: Int
    var bounds: Rect? = null
    var mtx: Array<CharArray>?
    private var childs: ArrayList<AsciiView> = ArrayList()
    var parent: AsciiView? = null
    var onClickListener: OnClickListener? = null
        set

    constructor(x: Int, y: Int, width: Int, height: Int) {
        this.x = x
        this.y = y
        this.width = width
        this.height = height

        bounds = Rect(x, y, x + width, y + height)
        mtx = Array(width) { CharArray(height) }
    }

    fun addChild(child: AsciiView) {
        childs.add(child)
        child.parent = this
        val childMtx = child.mtx
        for (i in 0..childMtx!!.size - 1) {
            (0..childMtx[i].size - 1)
                    .filter { childMtx[i][it] != ' ' }
                    .forEach { mtx!![i + child.x][it + child.y] = childMtx[i][it] }
        }
    }

    open fun refresh() {
        for (child in childs) {
            val childMtx = child.mtx
            for (i in 0..childMtx!!.size - 1) {
                (0..childMtx[i].size - 1)
                        .filter { childMtx[i][it] != ' ' }
                        .forEach { mtx!![i + child.x][it + child.y] = childMtx[i][it] }
            }
        }
    }

    open fun setChar(x: Int, y: Int, char: Char) {
        mtx!![x][y] = char
    }

    fun onClick(x: Int, y: Int) {
        if (bounds?.contains(x, y)!!) {
            val childClicked: AsciiView? = childs.lastOrNull { it.bounds!!.contains(x, y) }
            childClicked?.onClickListener?.onClick(childClicked)
        }
        onClickListener?.onClick(this)
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