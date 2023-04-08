package database

import models.*

class ScoreDAO {
    fun saveScore(score: Score) {
        val db = Database.getInstance();
        val statement = db.prepareStatement("INSERT INTO score (id_map, score, max_combo) VALUES (?, ?, ?)")
        statement.setString(1, score.mapId)
        statement.setInt(2, score.score)
        statement.setInt(3, score.maxCombo)

        statement.execute()
    }
}
