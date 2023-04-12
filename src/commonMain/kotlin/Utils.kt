import models.*

class Utils {
    companion object {
        var SPEED = 0.0
        val Y_NOTE_BASE = -100.0
        val Y_PERFECT_TIMING = 600.0
        val TIME_BEFORE_SPAWN_NOTE = 2000;

        fun convertTimeStringToMs(timeString: String): Long {
            val parts = timeString.split(":")
            val minutes = parts[0].toInt()
            val seconds = parts[1].toInt()
            val milliseconds = parts[2].toInt()
            return minutes * 60000L + seconds * 1000L + milliseconds
        }

        fun isInInterval(value: Long, min: Long, max: Long): Boolean {
            return value in min..max
        }

        fun initSpeed() {
            // Distance à parcourir au total pour avoir le timing parfait
            val distance = Y_PERFECT_TIMING - Y_NOTE_BASE

            // Temps en seconde pour parcourir la distance
            val timeInSeconds = TIME_BEFORE_SPAWN_NOTE / 1000.0

            // Vitesse en pixel par 10 ms
            SPEED = (distance / timeInSeconds) / 100
        }

        fun getSpeed(): Double {
            return SPEED
        }
    }
}
