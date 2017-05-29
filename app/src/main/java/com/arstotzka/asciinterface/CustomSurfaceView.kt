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
    private var map: Array<IntArray>? = null
    private var sizeW = 0
    private var sizeH = 0
    private val numColumns = 30
    private val numRows = 26

    constructor(context: Context) : super(context)
    constructor(context: Context, attr: AttributeSet) : super(context, attr)
    constructor(context: Context, attr: AttributeSet, defStyleAttr: Int) : super(context, attr, defStyleAttr)

    fun paint() {
        if (map == null && width > 0) {
            map = Array(numColumns) { IntArray(numRows) }
            sizeW = width / numColumns
            sizeH = height / numRows
        }

        val p1 = Paint()
        p1.color = Color.GREEN
        p1.textSize = 50F
        p1.textAlign = Paint.Align.CENTER
        p1.typeface = Typeface.MONOSPACE
        val fm = p1.getFontMetrics()
        sizeH = (fm.descent - fm.ascent).toInt()
        sizeW = p1.measureText("█").toInt()
        val p2 = Paint()
        p2.color = Color.BLACK
        val p3 = Paint()
        p3.color = Color.DKGRAY
        var textBounds = Rect()
        var p = false

        if (map != null && holder.surface.isValid) {
            val canvas = holder.lockCanvas()
            canvas.drawColor(Color.BLACK)
            for (i in 0..(map as Array<IntArray>).size - 1) {
                for (j in 0..(map as Array<IntArray>)[i].size - 1) {

                    var text = ""
                    if (i > j)
                        text = "═"
                    if (j > i)
                        text = "║"
                    if (i == j )
                        text = "╔"
                    if (j == 0 || j == (map as Array<IntArray>)[i].size - 1)
                        text = "═"
                    if (i == 0 || i == (map as Array<IntArray>).size - 1)
                        text = "║"
                    if (i == 0 && j == 0)
                        text = "╔"
                    if (i == 0 && j == (map as Array<IntArray>)[i].size - 1)
                        text = "╚"
                    if (i == (map as Array<IntArray>).size - 1 && j == 0)
                        text = "╗"
                    if (i == (map as Array<IntArray>).size - 1 && j == (map as Array<IntArray>)[i].size - 1)
                        text = "╝"

                    p = !p

                    p1.getTextBounds("█", 0, 1, textBounds)
                    canvas.drawText(text, (i * sizeW + sizeW / 2).toFloat(), (j * sizeH + textBounds.height() / 2).toFloat(), p1)
                }
                p = !p
            }
            holder.unlockCanvasAndPost(canvas)
        }
    }


}