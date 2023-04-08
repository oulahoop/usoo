package models.notes

import com.soywiz.korge.animate.*
import com.soywiz.korge.view.*
import com.soywiz.korim.color.*
import models.*
import vues.*

class HoldNote : Note {

    override var time: Long = 0
    override var duration: Long = 0
    override var lane: Bloc = Bloc.A
    override var width: Int = 100
    override var height: Int = 50
    override var color: RGBA = Colors["#000000"]
    override var rect: RoundRect? = null

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

    override fun afficher(container: SContainer) {
        if(color == Colors["#000000"]) {
            color = when (lane) {
                Bloc.A -> Colors["#ff0000"]
                Bloc.B -> Colors["#00ff00"]
                Bloc.C -> Colors["#0000ff"]
                Bloc.D -> Colors["#ffff00"]
            }
        }
        println("Affichage de $this")
        this.rect = RoundRect(width.toDouble(), height.toDouble(), 10.0, 10.0, color).position(lane.getXFromBloc(), Utils.Y_NOTE_BASE)
        container.addChild(this.rect!!)
    }

    override fun move() {
        if (rect != null) {
            rect!!.y += Utils.SPEED
        }
    }

    override fun disappear() {
        if (rect != null) {
            rect!!.removeFromParent()
            rect = null
        }
    }
}
