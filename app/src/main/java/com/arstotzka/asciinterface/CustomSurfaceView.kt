package com.arstotzka.asciinterface

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.SurfaceView

/**
 * Created by Daniel S on 29/05/2017.
 */

class CustomSurfaceView : SurfaceView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attr: AttributeSet) : super(context, attr)
    constructor(context: Context, attr: AttributeSet, defStyleAttr: Int) : super(context, attr, defStyleAttr)

    fun paint() {
        if (holder.surface.isValid) {
            val canvas = holder.lockCanvas()
            canvas.drawColor(Color.RED)
            holder.unlockCanvasAndPost(canvas)
        }
    }


}