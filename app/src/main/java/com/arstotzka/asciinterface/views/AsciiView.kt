package com.arstotzka.asciinterface.views

/**
 * Created by Daniel S on 30/05/2017.
 */
class AsciiView {

    private var x: Int
    private var y: Int
    private var mtx: Array<CharArray>?
    private var child: AsciiView? = null

    constructor(x: Int, y: Int) {
        this.x = x
        this.y = y

        mtx = Array(x) { CharArray(y) }
    }

    fun setChild(child: AsciiView) {
        this.child = child

        var childMtx = child.mtx
        for (i in 0..childMtx!!.size - 1) {
            (0..childMtx[i].size - 1)
                    .filter { childMtx[i][it] != ' ' }
                    .forEach { mtx!![i][it] = childMtx[i][it] }
        }
    }
}