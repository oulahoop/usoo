package vues.observer

import com.soywiz.korge.view.*
import com.soywiz.korim.color.*

class PrecisionNoteText(val precisionNote: QView): GameObserver {
    override fun onScoreChanged(score: Int, combo: Int, life: Int, lastNotePoint: Int) {
        var text = lastNotePoint.toString()
        var color = "#FFFFFF"
        if (lastNotePoint == 0) {
            text = "X"
            color = "#FF0000"
        }

        //if 50 ==> orange
        if (lastNotePoint == 50) {
            color = "#FFA500"
        }

        //if 100 ==> green
        if (lastNotePoint == 100) {
            color = "#00FF00"
        }

        //if 300 ==> cyan
        if (lastNotePoint == 300) {
            color = "#00FFFF"
        }

        precisionNote.setText(text)
        precisionNote.colorMul = Colors[color]
    }

}
