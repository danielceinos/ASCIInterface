package com.arstotzka.asciinterface

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_MOVE
import android.view.MotionEvent.ACTION_UP
import android.view.SurfaceView
import android.view.View
import com.arstotzka.asciinterface.views.AsciiView
import com.arstotzka.asciinterface.views.AsciiWindow
import com.arstotzka.asciinterface.views.Button
import android.view.SurfaceHolder
import com.arstotzka.asciinterface.views.OnClickListener


/**
 * Created by Daniel S on 29/05/2017.
 */

class CustomSurfaceView : SurfaceView, View.OnTouchListener, OnClickListener {


    private var sizeW = 0
    private var sizeH = 0
    private val numColumns = 40
    private val numRows = 29
    private var window: AsciiWindow? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attr: AttributeSet) : super(context, attr) {
        init()
    }

    constructor(context: Context, attr: AttributeSet, defStyleAttr: Int) : super(context, attr, defStyleAttr) {
        init()
    }

    fun init() {
        holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceDestroyed(holder: SurfaceHolder) {
                //stop render thread here
            }

            override fun surfaceCreated(holder: SurfaceHolder) {
                window?.refresh()
            }

            override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}

        })
        window = AsciiWindow(numColumns, numRows, this, Button("padre", 0, 0, numColumns, numRows))
        window?.view?.onClickListener = this
        setOnTouchListener(this)
    }

    fun paint(map: Array<CharArray>) {
        val p1 = Paint()
        p1.color = Color.GREEN
        p1.textSize = 40F
        p1.textAlign = Paint.Align.CENTER
        p1.typeface = Typeface.MONOSPACE

        if (holder.surface.isValid) {
            val canvas = holder.lockCanvas()
            canvas.drawColor(Color.BLACK)
            for (i in 0..map.size - 1) {
                (0..map [i].size - 1)
                        .filter { map[i][it] != '*' }
                        .forEach { canvas.drawText(map[i][it].toString(), (i * sizeW + sizeW / 2).toFloat(), (it * sizeH + sizeH).toFloat(), p1) }
            }
            holder.unlockCanvasAndPost(canvas)
        }
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
            window!!.onClick(event, x.toInt(), y.toInt())
        }
        return true
    }

    override fun onClick(event: MotionEvent?, view: AsciiView?) {
        (view as Button).changeText()
    }
}