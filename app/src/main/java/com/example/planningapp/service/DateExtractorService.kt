package com.example.planningapp.service

object DateExtractorService {

    /**
     * Verilen "YYYY-MM-DD" formatındaki string'den yıl (year) değerini döndürür.
     */
    fun getYear(dateString: String): Int {
        return dateString.split("-")[0].toInt()
    }

    /**
     * Verilen "YYYY-MM-DD" formatındaki string'den ay (month) değerini döndürür.
     */
    fun getMonth(dateString: String): Int {
        return dateString.split("-")[1].toInt()
    }

    /**
     * Verilen "YYYY-MM-DD" formatındaki string'den gün (day) değerini döndürür.
     */
    fun getDay(dateString: String): Int {
        return dateString.split("-")[2].toInt()
    }
}
