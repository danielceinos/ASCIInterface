package com.arstotzka.asciinterface.views

/**
 * Created by Daniel S on 01/06/2017.
 */

class Button(text: String, x: Int, y: Int, width: Int, height: Int) : AsciiView(x, y, width, height) {

    var name: String = text

    init {
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
        for (c in text) {
            setChar(indx + width / 2 - text.length / 2, height / 2, c)
            indx++
        }

        parent?.addChild(this)
    }

    fun changeText() {
        val texto = "PULSADO"
        var indx = 0
        for (c in name) {
            setChar(indx + width / 2 - name.length / 2, height / 2, ' ')
            indx++
        }
        indx = 0
        for (c in texto) {
            setChar(indx + width / 2 - texto.length / 2, height / 2, c)
            indx++
        }
        parent?.refresh()
    }
}