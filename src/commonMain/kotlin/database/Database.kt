package database

import java.nio.file.*
import java.sql.*

class Database {

    private constructor() {
        connectToDatabase()
        createTable()
    }

    companion object {
        private var instance: Database? = null
        private var connection: Connection? = null

        fun getInstance(): Connection {
            if (instance == null) {
                instance = Database()
            }
            return connection!!
        }
    }

    private fun connectToDatabase() {
        val path = Paths.get("").toAbsolutePath().toString() + "/data/database.db"

        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:$path")
    }

    private fun createTable() {
        val statement = connection!!.createStatement()
        //Score
        statement.execute("CREATE TABLE IF NOT EXISTS score (id INTEGER PRIMARY KEY AUTOINCREMENT, id_map INTEGER, score INTEGER, max_combo INTEGER)")
    }

}
