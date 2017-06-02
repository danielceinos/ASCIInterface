package com.arstotzka.asciinterface

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.SurfaceView
import android.view.View
import android.view.View.OnClickListener

/**
 * Created by Daniel S on 29/05/2017.
 */

class CustomSurfaceView : SurfaceView, OnClickListener {

    private var sizeW = 0
    private var sizeH = 0
    private val numColumns = 40
    private val numRows = 29
    var map: Array<CharArray>? = Array(numColumns) { CharArray(numRows) }

    constructor(context: Context) : super(context)
    constructor(context: Context, attr: AttributeSet) : super(context, attr)
    constructor(context: Context, attr: AttributeSet, defStyleAttr: Int) : super(context, attr, defStyleAttr)

    fun paint() {
        val p1 = Paint()
        p1.color = Color.GREEN
        p1.textSize = 40F
        p1.textAlign = Paint.Align.CENTER
        p1.typeface = Typeface.MONOSPACE

        if (map != null && holder.surface.isValid) {
            val canvas = holder.lockCanvas()
            canvas.drawColor(Color.BLACK)
            for (i in 0..(map as Array<CharArray>).size - 1) {
                for (j in 0..(map as Array<CharArray>)[i].size - 1) {
                    canvas.drawText((map as Array<CharArray>)[i][j].toString(), (i * sizeW+sizeW/2).toFloat(), (j * sizeH + sizeH).toFloat(), p1)
                }
            }
            holder.unlockCanvasAndPost(canvas)
        }
    }

    fun setChatAtPos(char: Char, x: Int, y: Int) {
        map!![x][y] = char
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        sizeH = h / numRows
        sizeW = w / numColumns
    }

    override fun onClick(v: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}