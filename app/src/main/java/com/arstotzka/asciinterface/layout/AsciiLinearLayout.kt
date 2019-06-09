package com.arstotzka.asciinterface.layout

import com.arstotzka.asciinterface.views.AsciiView

/**
 * Created by Daniel S on 10/06/2017.
 */
class AsciiLinearLayout(x: Int, y: Int, with: Int, height: Int) : AsciiView(x, y, with, height) {

  override fun addChild(child: AsciiView) {
    if (children.size > 0)
      child.setY(children.last().getY() + children.last().height )
    else
      child.setY(0)

    super.addChild(child)
  }

}