package models.notes

import com.soywiz.korim.color.*
import models.*

class HoldNote : Note {

    override var time: Long = 0
    override var duration: Long = 0
    override var lane: Bloc = Bloc.A
    override var width: Int = 100
    override var height: Int = 50
    override var color: RGBA = Colors["#000000"]

    override fun toString(): String {
        return "HoldNote(time=$time, duration=$duration, lane=$lane, width=$width, height=$height, color=$color)"
    }

    override fun readNote(noteInString: String) {
        println(noteInString)
        val note = noteInString.split(",")
        time = Utils.convertTimeStringToMs(note[1])
        duration = Utils.convertTimeStringToMs(note[2]) - time
        lane = when (note[3]) {
            "A" -> Bloc.A
            "B" -> Bloc.B
            "C" -> Bloc.C
            "D" -> Bloc.D
            else -> throw Exception("Bloc not found")
        }
    }
}
