package com.example.weatherapp.converter

object DateConvertor {
    private const val REGEX_DATE = "\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])"
    private const val REGEX_TIME = "([01]\\d|2[0-3]):([0-5]\\d|60):([0-5]\\d|60)"
    private const val REGEX_DATE_STRING =
        "^\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])\\s([01]\\d|2[0-3]):([0-5]\\d|60):([0-5]\\d|60)$"
    val days = arrayOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")

    fun findDayOfWeek(day: Int, month: Int, year: Int): Int {
        var d = day
        val m = month
        var y = year
        if (m <= 2) {
            y--
            d += 3
        }
        return 1 + (d + y + y / 4 - y / 100 + y / 400 + (31 * m + 10) / 12) % 7
    }

    fun findTime(dateString: String): String? = Regex(REGEX_TIME).find(dateString)?.value
    fun findDate(dateString: String): String? = Regex(REGEX_DATE).find(dateString)?.value


    fun findDayOfWeekInt(dateString: String?): Int {
        if (dateString == null || !dateString.matches(Regex(REGEX_DATE)))
            return -1
        var date=Regex(REGEX_DATE).find(dateString)?.value
        val year = date?.substring(0, 4)?.toInt()
        val month = date?.substring(5, 7)?.toInt()
        val day = date?.substring(9)?.toInt()
        return findDayOfWeek(day!!, month!!, year!!)
    }

    fun getDayOfWeekString(num: Int): String {
        return if (num < 0 || num > 6) {
            ""
        } else {
            days[num - 1]
        }
    }
}