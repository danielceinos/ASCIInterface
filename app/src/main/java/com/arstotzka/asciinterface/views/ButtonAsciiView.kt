package com.arstotzka.asciinterface.views

/**
 * Created by Daniel S on 01/06/2017.
 */

class ButtonAsciiView(text: String, x: Int, y: Int, width: Int, height: Int) : AsciiView(x, y, width, height) {

  var textView: TextAsciiView

  constructor(text: String, width: Int, height: Int) : this(text, 0, 0, width, height)

  init {
    clear()
    textView = TextAsciiView(text, width / 2 - text.length / 2, height / 2)
    addChild(textView)
    parent?.addChild(this)
  }

  override fun clear() {
    super.clear()

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
  }

  fun changeText() {
    textView.changeText("pulsado")
  }
}