package com.arstotzka.asciinterface

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.SurfaceView

/**
 * Created by Daniel S on 29/05/2017.
 */

class CustomSurfaceView : SurfaceView {

    private var sizeW = 0
    private var sizeH = 0
    private val numColumns = 41
    private val numRows = 29
    private var map: Array<CharArray>? = Array(numColumns) { CharArray(numRows) }

    constructor(context: Context) : super(context)
    constructor(context: Context, attr: AttributeSet) : super(context, attr)
    constructor(context: Context, attr: AttributeSet, defStyleAttr: Int) : super(context, attr, defStyleAttr)

    fun paint() {
        val p1 = Paint()
        p1.color = Color.GREEN
        p1.textSize = 40F
        p1.textAlign = Paint.Align.CENTER
        p1.typeface = Typeface.MONOSPACE
        val fm = p1.getFontMetrics()
        sizeH = (fm.descent - fm.ascent + fm.leading).toInt()
        sizeW = p1.measureText("█").toInt()

        if (map != null && holder.surface.isValid) {
            val canvas = holder.lockCanvas()
            canvas.drawColor(Color.BLACK)
            for (i in 0..(map as Array<CharArray>).size - 1) {
                for (j in 0..(map as Array<CharArray>)[i].size - 1) {
                    canvas.drawText((map as Array<CharArray>)[i][j].toString(), (i * sizeW + sizeW / 2).toFloat(), (j * sizeH + sizeH).toFloat(), p1)
                }
            }
            holder.unlockCanvasAndPost(canvas)
        }
    }

    fun setChatAtPos(char: Char,x: Int, y: Int) {
        map!![x][y] = char
    }

}