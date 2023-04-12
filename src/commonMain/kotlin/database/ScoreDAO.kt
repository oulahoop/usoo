package database

import models.*

class ScoreDAO {
    fun saveScore(score: Score) {
        val db = Database.getInstance();
        val statement = db.prepareStatement("INSERT INTO score (id_map, score, max_combo, x300, x100, x50, miss) VALUES (?, ?, ?, ?, ?, ?, ?)")
        statement.setString(1, score.mapId)
        statement.setInt(2, score.score)
        statement.setInt(3, score.maxCombo)
        statement.setInt(4, score.x300)
        statement.setInt(5, score.x100)
        statement.setInt(6, score.x50)
        statement.setInt(7, score.miss)

        statement.execute()
    }

    fun getTop(score: Score): Int {
        val db = Database.getInstance();
        val statement = db.prepareStatement("SELECT * FROM score WHERE id_map = ? ORDER BY score DESC")
        statement.setString(1, score.mapId)
        val result = statement.executeQuery()
        var top = 1

        while (result.next()) {
            val scoreInDb = result.getInt("score")
            if (score.score == scoreInDb) {
                return top
            }
            top += 1
        }

        return top
    }
}
