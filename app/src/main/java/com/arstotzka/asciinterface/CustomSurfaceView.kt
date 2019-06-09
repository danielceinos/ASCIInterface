package com.arstotzka.asciinterface

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_MOVE
import android.view.MotionEvent.ACTION_UP
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import com.arstotzka.asciinterface.views.AsciiView
import com.arstotzka.asciinterface.views.AsciiWindow
import com.arstotzka.asciinterface.views.ButtonAsciiView

/**
 * Created by Daniel S on 29/05/2017.
 */

class CustomSurfaceView : SurfaceView, View.OnTouchListener {

    private var sizeW = 0
    private var sizeH = 0
    private val numColumns = 40
    private val numRows = 29
    private var window: AsciiWindow

    constructor(context: Context) : super(context)
    constructor(context: Context, attr: AttributeSet) : super(context, attr)
    constructor(context: Context, attr: AttributeSet, defStyleAttr: Int) : super(context, attr, defStyleAttr)

    init {
        window = AsciiWindow(numColumns, numRows, this)

        holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceDestroyed(holder: SurfaceHolder) {
                //stop render thread here
            }

            override fun surfaceCreated(holder: SurfaceHolder) {
                window.refresh()
            }

            override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}

        })

        val father = AsciiView(0, 0, numColumns, numRows)
        window.view = father

        val child = ButtonAsciiView("boton 1", 0, 0, numColumns, numRows)
        val child1 = ButtonAsciiView("boton 2", 2, 2, 10, 5)
        val child2 = ButtonAsciiView("boton 3", 4, 5, 10, 5)
        child.addChild(child1)
        child.addChild(child2)
        window.view?.addChild(child)

        setOnTouchListener(this)
    }

    fun paint(map: Array<CharArray>) {
        Log.i("CustomSurfaceView", "Start paint")
        val startTimestamp = System.currentTimeMillis()

        val p1 = Paint()
        p1.color = Color.GREEN
        p1.textSize = 40F
        p1.textAlign = Paint.Align.CENTER
        p1.typeface = Typeface.MONOSPACE

        if (holder.surface.isValid) {
            val canvas = holder.lockCanvas()
            canvas.drawColor(Color.BLACK)
            for (i in 0 until map.size) {
                (0 until map[i].size)
                    .filter { map[i][it] != '*' }
                    .forEach { canvas.drawText(map[i][it].toString(), (i * sizeW + sizeW / 2).toFloat(), (it * sizeH + sizeH).toFloat(), p1) }
            }
            holder.unlockCanvasAndPost(canvas)
        }

        Log.i("CustomSurfaceView", "Paint finished")
        Log.i("CustomSurfaceView", "Elapsed time = ${System.currentTimeMillis() - startTimestamp}")

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        sizeH = h / numRows
        sizeW = w / numColumns
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (event?.action == ACTION_UP || event?.action == ACTION_MOVE) {
            val x = event.x * numColumns / this.width
            val y = event.y * numRows / this.height
            window.onClick(event, x.toInt(), y.toInt())
        }
        return true
    }
}