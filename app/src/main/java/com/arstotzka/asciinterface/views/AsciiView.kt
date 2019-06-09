package com.arstotzka.asciinterface.views

import android.graphics.Color
import android.graphics.Rect
import android.view.MotionEvent

/**
 * Created by Daniel S on 30/05/2017.
 */
open class AsciiView(x: Int, y: Int, var width: Int, var height: Int) {

    var window: AsciiWindow? = null
    var bounds: Rect = Rect(x, y, width + x, height + y)
    var mtx: Array<CharArray> = Array(width) { CharArray(height) }
    var colorMtx: Array<IntArray> = Array(width) { IntArray(height) }
    var children: ArrayList<AsciiView> = ArrayList()
    var parent: AsciiView? = null
    var clickable: Boolean = false
    var onAsciiViewClickListener: (x: Int, y: Int) -> Unit = { x, y -> }
    open var color: Int = Color.GREEN
        set(value) {
            field = value
            rePaint()
        }

    companion object {
        const val TRANSPARENT_CHAR = '*'
    }

    open fun clear() {
        for (x in 0 until width) {
            for (y in 0 until height) {
                setChar(x, y, ' ')
            }
        }
    }

    open fun addChild(child: AsciiView) {
        children.add(child)
        child.parent = this
        val childMtx = child.mtx
        val childColorMtx = child.colorMtx

        for (i in 0 until childMtx.size) {
            (0 until childMtx[i].size)
                .filter { childMtx[i][it] != TRANSPARENT_CHAR }
                .filter { i + child.bounds.left >= 0 && it + child.bounds.top >= 0 }
                .filter { i + child.bounds.left < mtx.size && it + child.bounds.top < mtx[0].size }
                .forEach {
                    mtx[i + child.bounds.left][it + child.bounds.top] = childMtx[i][it]
                    colorMtx[i + child.bounds.left][it + child.bounds.top] = childColorMtx[i][it]
                }
        }
    }

    open fun rePaint() {
        clear()
        for (child in children) {
            val childMtx = child.mtx
            val childColorMtx = child.colorMtx

            for (i in 0 until childMtx.size) {
                (0 until childMtx[i].size)
                    .filter { childMtx[i][it] != TRANSPARENT_CHAR }
                    .filter { i + child.bounds.left >= 0 && it + child.bounds.top >= 0 }
                    .filter { i + child.bounds.left < mtx.size && it + child.bounds.top < mtx[0].size }
                    .forEach {
                        mtx[i + child.bounds.left][it + child.bounds.top] = childMtx[i][it]
                        colorMtx[i + child.bounds.left][it + child.bounds.top] = childColorMtx[i][it]
                    }
            }
        }
        parent?.rePaint()
        window?.refresh()
    }

    open fun setChar(x: Int, y: Int, char: Char) {
        if (mtx.size > x && mtx[0].size > y) {
            mtx[x][y] = char
            colorMtx[x][y] = color
        }
    }

    open fun onClick(event: MotionEvent?, x: Int, y: Int): Boolean {
        if (bounds.contains(x, y)) {
            for (child in children.reversed()) {
                val clickHandled = child.onClick(event, x - bounds.left, y - bounds.top)
                if (clickHandled) return true
            }
            if (clickable)
                onAsciiViewClickListener.invoke(x, y)
            return clickable
        }
        return false
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

    private fun print(): String {
        var stringMtx = ""
        for (j in 0 until mtx[0].size) {
            for (i in 0 until mtx.size) {
                stringMtx += mtx[i][j]
            }
            stringMtx += "\n"
        }
        return stringMtx
    }
}