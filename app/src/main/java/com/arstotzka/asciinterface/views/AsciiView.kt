package com.arstotzka.asciinterface.views

/**
 * Created by Daniel S on 30/05/2017.
 */
class AsciiView {

    private var x: Int
    private var y: Int
    private var mtx: Array<CharArray>?

    constructor(x: Int, y: Int) {
        this.x = x
        this.y = y

        mtx = Array(x) { CharArray(y) }
    }

    
}