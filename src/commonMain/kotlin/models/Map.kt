package models

import com.soywiz.korau.sound.*
import com.soywiz.korge.view.*
import com.soywiz.korio.file.std.*
import jdk.jshell.execution.Util
import vues.*

//Class with the data of a game of piano tiles
class Map {
    var musicPath: String = ""
    var name: String = ""
    var author: String = ""
    var difficulty: String = ""
    var notes: MutableList<Note> = mutableListOf()
    var notesPrinted = mutableListOf<Note>()
    var currentMapTime = -2000L

    //Constructor
    constructor(name: String, author: String, difficulty: String, notes: MutableList<Note>) {
        this.name = name
        this.author = author
        this.difficulty = difficulty
        this.notes = notes
    }

    //Constructor
    constructor(name: String, author: String, difficulty: String) {
        this.name = name
        this.author = author
        this.difficulty = difficulty
    }

    //Constructor
    constructor()

    //Adds a note to the map
    fun addNote(note: Note) {
        notes.add(note)
        notes.sortBy { it.time }
    }

    //Removes a note from the map
    fun removeNote(note: Note) {
        notes.remove(note)
    }

    //Create id of the map
    fun getId(): String {
        return name + author + difficulty
    }

    //Returns the string representation of the map
    override fun toString(): String {
        return "Map(name='$name', author='$author', difficulty='$difficulty', notes=$notes)"
    }


}
