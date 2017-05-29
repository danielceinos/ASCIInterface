package com.arstotzka.asciinterface

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.SurfaceView

/**
 * Created by Daniel S on 29/05/2017.
 */

class CustomSurfaceView : SurfaceView {
    private var map: Array<IntArray>? = null
    private var sizeW = 0
    private var sizeH = 0
    private val numColumns = 40
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
        p1.color = Color.RED
        val p2 = Paint()
        p2.color = Color.BLUE
        var p = false
        if (map != null && holder.surface.isValid) {
            val canvas = holder.lockCanvas()
            for (i in 0..(map as Array<IntArray>).size - 1) {
                for (j in 0..(map as Array<IntArray>)[i].size - 1) {
                    val rect = Rect(i * sizeW, j * sizeH, i * sizeW + sizeW, j * sizeH + sizeH)
                    if (p)
                        canvas.drawRect(rect, p1)
                    else
                        canvas.drawRect(rect, p2)
                    p = !p

                }
                p = !p
            }
            holder.unlockCanvasAndPost(canvas)
        }
    }


}