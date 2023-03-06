package controllers

import models.*
import models.notes.*

class ReadNote {

    companion object {
        lateinit var note: Note

        fun setNoteTypeReader(type: String) {
            when (type) {
                "TAP" -> note = TapNote()
                "HOLD" -> note = HoldNote()
            }
        }

        fun readNote(noteInString: String) {
            note.readNote(noteInString)
        }
    }
}
