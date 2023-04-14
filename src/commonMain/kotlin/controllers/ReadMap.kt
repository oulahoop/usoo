package controllers

import models.Map
import java.io.*

class ReadMap {
    //Reads a map from a file and returns it
    //Static
    companion object {
        fun readMap(path: String): Map {
            try {
                val map = Map()
                val file = File(path)
                val reader = BufferedReader(FileReader(file))
                var line = reader.readLine()
                while (line != null && line != "") {
                    when {
                        line.startsWith("name:", true) -> map.name = line.substring(5)
                        line.startsWith("author:", true) -> map.author = line.substring(7)
                        line.startsWith("difficulty:", true) -> map.difficulty = line.substring(11)
                        line.startsWith("music:", true) -> map.musicPath = "maps/" + line.substring(6)
                        else -> {
                            val note = line.split(",")
                            val type = note[0]
                            ReadNote.setNoteTypeReader(type)
                            ReadNote.readNote(line)
                            map.addNote(ReadNote.note)
                        }
                    }
                    line = reader.readLine()
                }
                return map
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return Map()
        }

        fun readAll(): List<Map> {
            val maps = mutableListOf<Map>()
            val folder = File("src/commonMain/resources/maps")
            val files = folder.listFiles()
            for (file in files!!) {
                if(file.name.endsWith(".txt")) {
                    maps.add(readMap(file.path))
                }
            }
            return maps
        }
    }
}
