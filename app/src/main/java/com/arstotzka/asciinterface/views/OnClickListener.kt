package com.arstotzka.asciinterface.views

import android.view.MotionEvent

/**
 * Created by Daniel S on 04/06/2017.
 */
interface OnClickListener {

  fun onClickAsciiView(event: MotionEvent?, view: AsciiView?): Boolean

}