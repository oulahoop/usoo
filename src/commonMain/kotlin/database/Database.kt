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

        fun getInstance(): Database {
            if (instance == null) {
                instance = Database()
            }
            return instance!!
        }
    }

    private fun connectToDatabase() {
        val path = Paths.get("").toAbsolutePath().toString() + "/data/database.db"

        connection = DriverManager.getConnection("jdbc:sqlite:$path")
    }

    private fun createTable() {
        val statement = connection!!.createStatement()
        //Map
        statement.execute("CREATE TABLE IF NOT EXISTS map (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT UNIQUE, author TEXT, difficulty TEXT)")
        //Score
        statement.execute("CREATE TABLE IF NOT EXISTS score (id INTEGER PRIMARY KEY AUTOINCREMENT, id_map INTEGER, score INTEGER, max_combo INTEGER, FOREIGN KEY(id_map) REFERENCES map(id))")
    }

}
