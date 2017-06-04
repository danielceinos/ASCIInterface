package com.arstotzka.asciinterface.views

import android.util.Log

/**
 * Created by Daniel S on 01/06/2017.
 */

class Button(text: String, x: Int, y: Int, width: Int, height: Int) : AsciiView(x, y, width, height) {

    var name: String = text

    init {
        paint()
        parent?.addChild(this)
    }

    override fun paint() {
        super.paint()

        setChar(0, 0, '╔')
        setChar(width - 1, 0, '╗')
        setChar(0, height - 1, '╚')
        setChar(width - 1, height - 1, '╝')
        for (i in 1..width - 2) {
            setChar(i, 0, '═')
            setChar(i, height - 1, '═')
        }
        for (i in 1..height - 2) {
            setChar(0, i, '║')
            setChar(width - 1, i, '║')
        }

        var indx = 0
        for (c in name) {
            setChar(indx + width / 2 - name.length / 2, height / 2, c)
            indx++
        }

    }

    fun changeText() {
        val texto = "PULSADO"
        var indx = 0
        for (c in name) {
            setChar(indx + width / 2 - name.length / 2, height / 2, ' ')
            indx++
        }
        name = texto
        indx = 0
        for (c in texto) {
            setChar(indx + width / 2 - texto.length / 2, height / 2, c)
            indx++
        }

        parent?.refresh()
    }

    var t = 0.0
    fun moveOneUp() {
        t += 0.05
        val dx = (0 * Math.sin(t)).toInt()
        val dy = (7 * Math.cos(t)).toInt()
        bounds?.offsetTo(dx+15, dy+10)
        parent?.refresh()
    }
}