package vues.observer

import com.soywiz.korge.view.*

class ComboText(val comboView: QView) : GameObserver {
    override fun onScoreChanged(score: Int, combo: Int, life: Int) {
        comboView.setText(combo.toString())
    }
}
