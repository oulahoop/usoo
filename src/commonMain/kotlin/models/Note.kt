package models

import com.soywiz.korge.view.*
import com.soywiz.korim.color.*
import vues.*

//Class of a note in a piano tiles game
interface Note {
    var time: Long
    var duration: Long
    var lane: Bloc

    //width height
    var width: Int
    var height: Int
    var color: RGBA

    //Returns the string representation of the note
    override fun toString(): String

    fun readNote(noteInString: String)
    fun afficher(container: SContainer)
}
