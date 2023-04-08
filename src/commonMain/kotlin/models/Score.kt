package models

import database.*

data class Score(val mapId: String, val score: Int, val maxCombo: Int) {

    //function save to db
    fun save() {
        val dao = ScoreDAO()
        dao.saveScore(this)
    }
}
