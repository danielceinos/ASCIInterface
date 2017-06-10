package com.arstotzka.asciinterface.views

/**
 * Created by Daniel S on 10/06/2017.
 */
class TextAsciiView(text: String, x: Int, y: Int) : AsciiView(x, y, text.length, 1) {

  var text: String = text

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

  open fun changeText(text: String) {
    this.text = text
    clear()
    parent?.rePaint()
    window?.refresh()
  }
}