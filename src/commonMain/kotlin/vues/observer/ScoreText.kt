package vues.observer

import com.soywiz.korge.view.*

class ScoreText(val scoreView: QView) : GameObserver {
    override fun onScoreChanged(score: Int, combo: Int, life: Int, lastNotePoint: Int) {
        scoreView.setText(score.toString())
    }
}
