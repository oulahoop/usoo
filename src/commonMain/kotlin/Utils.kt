class Utils {
    companion object {
        fun convertTimeStringToMs(timeString: String): Long {
            val parts = timeString.split(":")
            val minutes = parts[0].toInt()
            val seconds = parts[1].toInt()
            val milliseconds = parts[2].toInt()
            return minutes * 60000L + seconds * 1000L + milliseconds
        }
    }
}
