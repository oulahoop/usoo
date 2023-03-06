class Utils {
    companion object {
        val PIXELS_PER_MS = 0.1
        val Y_NOTE_BASE = -100.0

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
    }
}
