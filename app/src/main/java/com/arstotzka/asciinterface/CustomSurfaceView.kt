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
import android.view.SurfaceView
import android.view.View
import android.view.View.OnClickListener
import com.arstotzka.asciinterface.views.AsciiView
import com.arstotzka.asciinterface.views.Button

/**
 * Created by Daniel S on 29/05/2017.
 */

class CustomSurfaceView : SurfaceView, View.OnTouchListener, com.arstotzka.asciinterface.views.OnClickListener {

    private var sizeW = 0
    private var sizeH = 0
    private val numColumns = 40
    private val numRows = 29
    private var view: AsciiView? = null
    var map: Array<CharArray>? = Array(numColumns) { CharArray(numRows) }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attr: AttributeSet) : super(context, attr) {
        init()
    }

    constructor(context: Context, attr: AttributeSet, defStyleAttr: Int) : super(context, attr, defStyleAttr) {
        init()
    }

    var b2: AsciiView? = null
    fun init() {
        view = Button("boton padre", 0, 0, numColumns, numRows)
//        (view as AsciiView).onClickListener = this
//        (view as AsciiView).addChild(Button(":3", 1, 1, 15, 7))
//        val b1 = Button("holi ", 3, 4, 11, 5)
//        b1.onClickListener = this
//        (view as AsciiView).addChild(b1)

        b2 = Button("pulsa", 9, 15, 11, 5)
        (b2 as Button).onClickListener = this
        (view as AsciiView).addChild(b2 as Button)

        map = (view as AsciiView).mtx

        setOnTouchListener(this)
    }

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
                (0..(map as Array<CharArray>)[i].size - 1)
                        .filter { (map as Array<CharArray>)[i][it] != '*' }
                        .forEach { canvas.drawText((map as Array<CharArray>)[i][it].toString(), (i * sizeW + sizeW / 2).toFloat(), (it * sizeH + sizeH).toFloat(), p1) }
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

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (event?.action == ACTION_UP || event?.action == ACTION_MOVE) {
            val x = event.x * numColumns / this.width
            val y = event.y * numRows / this.height
            view!!.onClick(event, x.toInt(), y.toInt())
        }



        return true
    }

    override fun onClick(event: MotionEvent?, view: AsciiView?) {
        (view as Button).changeText()

        if (event?.action == ACTION_MOVE) {
            val x = event.x * numColumns / this.width
            val y = event.y * numRows / this.height

            (view as Button).moveTo(x.toInt(), y.toInt())
        }
    }
}