package com.example.planningapp.service

object TimeConverterService {

    /**
     * "HH:MM" formatındaki saat string'ini Integer (dakika cinsinden) formata çevirir.
     * Örneğin: "14:30" → 870 (14 saat * 60 + 30 dakika)
     */
    fun convert(timeString: String): Int {
        return try {
            val (hour, minute) = timeString.split(":").map { it.toInt() }
            hour * 60 + minute
        } catch (e: Exception) {
            e.printStackTrace()
            0 // Geçersiz format durumunda varsayılan değer
        }
    }

    /**
     * Dakika cinsinden verilen Integer değeri "HH:MM" formatındaki String'e çevirir.
     * Örneğin: 870 → "14:30"
     */
    fun convert(timeInMinutes: Int): String {
        val hours = timeInMinutes / 60
        val minutes = timeInMinutes % 60
        return String.format("%02d:%02d", hours, minutes)
    }
}
