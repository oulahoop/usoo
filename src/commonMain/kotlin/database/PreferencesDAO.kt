package database

import Utils

class PreferencesDAO {

    fun readPreferences() {
        // Récupération de l'instance
        val db = Database.getInstance();

        // Récupération des préférences
        val statement = db.prepareStatement("SELECT * FROM preferences")
        val result = statement.executeQuery()

        // On parcourt les résultats
        while (result.next()) {
            val id = result.getInt("id")
            val touche = result.getString("touche")

            if (id >= 4) {
                continue // erreur
            }

            // On ajoute les préférences dans la liste
            Utils.setTouche(id, touche)
        }
    }

    fun setPreferences(preferences: Array<String>) {
        // Récupération de l'instance
        val db = Database.getInstance();

        // On supprime les préférences actuelles
        val statement = db.prepareStatement("DELETE FROM preferences")
        statement.execute()

        // On ajoute les nouvelles préférences
        for (i in 0..3) {
            val statement = db.prepareStatement("INSERT INTO preferences (id, touche) VALUES (?, ?)")
            statement.setInt(1, i)
            statement.setString(2, preferences[i])

            statement.execute()
        }
    }
}
