package models

import com.soywiz.korge.view.*
import vues.*

//Class with the data of a game of piano tiles
class Map {
    var musicPath: String = ""
    var name: String = ""
    var author: String = ""
    var difficulty: String = ""
    var notes: MutableList<Note> = mutableListOf()

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

    //Returns the string representation of the map
    override fun toString(): String {
        return "Map(name='$name', author='$author', difficulty='$difficulty', notes=$notes)"
    }

    /*
    Start the map music, print the notes and wait for the user to press the correct key
     */
    fun start(container: SContainer) {
        //Print the notes
        var currentTime = -2000L
        val listNotePrinted = mutableListOf<Note>()
        println(notes)
        //Tant que la dernière note n'est pas passé
        while (currentTime < notes.last().time + 2000) {
            //Pour chaque note
            for (note in notes) {
                // Si la note est dans la fenêtre de temps
                if(Utils.isInInterval(note.time - 2000, currentTime-50, currentTime+50) ) {
                    //Si la note n'a pas encore été affichée
                    if(!listNotePrinted.contains(note)) {
                        //Afficher la note
                        note.afficher(container)
                        //Ajouter la note à la liste des notes affichées
                        listNotePrinted.add(note)
                    }
                }
            }

            currentTime += 10
            //Attendre 1ms
            try {
                Thread.sleep(10)
            }catch (e: Exception) {
                println(e)
            }
        }
        //Wait for the user to press the correct key
    }

}
