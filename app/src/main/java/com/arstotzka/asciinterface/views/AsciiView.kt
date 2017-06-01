package com.arstotzka.asciinterface.views

import android.util.Log

/**
 * Created by Daniel S on 30/05/2017.
 */
open class AsciiView {

    var x: Int
    var y: Int
    var width: Int
    var height: Int
    var mtx: Array<CharArray>?
    private var child: AsciiView? = null
    var parent: AsciiView?

    constructor(x: Int, y: Int, width: Int, height: Int, parent: AsciiView?) {
        this.x = x
        this.y = y
        this.parent = parent
        this.width = width
        this.height = height

        mtx = Array(width) { CharArray(height) }

        parent?.setChild(this)
    }

    fun setChild(child: AsciiView) {
        this.child = child

        var childMtx = child.mtx
        for (i in 0..childMtx!!.size - 1) {
            (0..childMtx[i].size - 1)
                    .filter { childMtx[i][it] != ' ' }
                    .forEach { mtx!![i + child.x][it + child.y] = childMtx[i][it] }
        }

        if (parent != null)
            parent!!.setChild(this)
    }

    open fun setChar(x: Int, y: Int, char: Char) {
        mtx!![x][y] = char
    }

    open fun setTextLayout(textLayout: String) {
        var x = 0
        var y = 0
        for (char in textLayout) {
            Log.e("TAG", char.toString() + " - " + x + " / " + y)
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