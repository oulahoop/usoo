package models.notes

import Utils
import com.soywiz.korim.color.*
import models.*

class TapNote : Note {

    override var time: Long = 0
    override var duration: Long = 0
    override var lane: Bloc = Bloc.A
    override var width: Int = 100
    override var height: Int = 50
    override var color: RGBA = Colors["#000000"]

    //Constructor
    constructor(time: Long, lane: Bloc, color: RGBA) {
        this.time = time
        this.lane = lane
        this.color = when (lane) {
            Bloc.A -> Colors["#ff0000"]
            Bloc.B -> Colors["#00ff00"]
            Bloc.C -> Colors["#0000ff"]
            Bloc.D -> Colors["#ffff00"]
        }
        this.color = color
    }

    constructor()



    override fun toString(): String {
        return "TapNote(time=$time, duration=$duration, lane=$lane, width=$width, height=$height, color=$color)"
    }

    override fun readNote(noteInString: String) {
        val note = noteInString.split(",")

        //DURATION = 0
        time = Utils.convertTimeStringToMs(note[1])
        duration = 0
        lane = when (note[2]) {
            "A" -> Bloc.A
            "B" -> Bloc.B
            "C" -> Bloc.C
            "D" -> Bloc.D
            else -> throw Exception("Bloc not found")
        }
    }
}
