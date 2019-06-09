package com.arstotzka.asciinterface.views

/**
 * Created by Daniel S on 10/06/2017.
 */
class TextAsciiView(private var text: String, x: Int, y: Int) : AsciiView(x, y, text.length, 1) {

    init {
        clear()
        parent?.addChild(this)
    }

    override fun clear() {
        super.clear()

        var indx = 0
        for (c in text) {
            setChar(indx, 0, c)
            indx++
        }
    }

    fun changeText(text: String) {
        this.text = text
        rePaint()
    }
}