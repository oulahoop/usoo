package vues.observer

interface GameObserver {
    fun onScoreChanged(score: Int, combo: Int, life: Int, lastNotePoint: Int)
}
