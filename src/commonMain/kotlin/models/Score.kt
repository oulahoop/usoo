package models

import database.*

data class Score(val mapId: String, val score: Int, val maxCombo: Int, val x300: Int, val x100: Int, val x50: Int, val miss: Int) {

    //function save to db
    fun save() {
        val dao = ScoreDAO()
        dao.saveScore(this)
    }
}
