package com.example.planningapp.service

import android.util.Log
import com.example.planningapp.data.entity.TimeLineTask

object IdentifyEmptyHoursService {

    /**
     * Gün boyunca verilen görevler arasında hangi saat dilimlerinin boş olduğunu tespit eder.
     * Her boş saat dilimi için, timeline öğesi olarak 0 değeri eklenir.
     *
     * Zaman birimi dakika cinsindendir (0 → 00:00, 60 → 01:00, …, 1440 → 24:00).
     *
     * @param tasks Görevlerin kronolojik sıralı listesi.
     * @return Boş saat dilimlerini temsil eden, her biri 0 değeri içeren bir liste.
     */
    fun identifyEmptyHours(tasks: List<TimeLineTask>): List<Int> {
        // Eğer görev listesi boşsa, boş liste döndür.
        if (tasks.isEmpty()) return emptyList()

        val emptyHours = mutableListOf<Int>()
        var taskIndex = 0

        // Günün 0'dan 1440'a kadar olan saat başlarını kontrol ediyoruz (dakika cinsinden).
        for (hour in 0..1440 step 60) {
            Log.e(
                "IdentifyEmptyHoursService",
                "hour: $hour\n startTime: ${tasks[taskIndex].startTime}\n endTime: ${tasks[taskIndex].endTime}"
            )

            when {
                // Eğer mevcut görevin başlangıç zamanı kontrol edilen saatten sonra ise,
                // o saat boş demektir → timeline öğesi olarak 0 eklenir.
                tasks[taskIndex].startTime > hour -> {
                    emptyHours.add(hour)
                }
                // Eğer kontrol edilen saat, mevcut görevin çalışma süresi içerisindeyse,
                // bu saat dolu kabul edilir; hiçbir ekleme yapılmaz.
                tasks[taskIndex].endTime > hour -> {
                    // Saat dolu; ekleme yapmıyoruz.
                }
                // Eğer mevcut görevin bitiş zamanı kontrol edilen saatten küçükse,
                // sonraki göreve geçmemiz gerekir.
                tasks[taskIndex].endTime < hour -> {
                    if (taskIndex + 1 < tasks.size) {
                        emptyHours.add(-1)
                        taskIndex++
                    } else {
                        break // Daha fazla görev yoksa döngüden çık.
                    }
                    // Yeni görevde, kontrol edilen saat henüz başlamamışsa, gerekirse
                    // bir sonraki göreve geçiyoruz.
                    while (taskIndex < tasks.size && tasks[taskIndex].startTime < hour) {
                        if (tasks[taskIndex].endTime >= hour) {
                            break
                        } else {
                            if (taskIndex + 1 < tasks.size) {
                                emptyHours.add(-1)
                                taskIndex++
                            } else {
                                break
                            }
                        }
                    }
                }
                // Eğer mevcut görevin bitiş zamanı tam olarak kontrol edilen saate eşitse,
                // sonraki göreve geçiyoruz.
                tasks[taskIndex].endTime == hour -> {
                    if (taskIndex + 1 < tasks.size) {
                        emptyHours.add(-1)
                        taskIndex++
                    } else {
                        break
                    }
                }
            }
        }

        // Günün sonunda, son görev bitiş saatinden sonraki boş saat dilimlerini de ekle:
        val lastTaskEndTime = (((tasks.last().endTime) / 60) + 1) * 60
        emptyHours.add(-1)
        for (hour in lastTaskEndTime..1440 step 60) {
            emptyHours.add(hour)
        }

        // Bulunan boş saat dilimlerini (0 değeri) log'la.
        emptyHours.forEach { emptyValue ->
            Log.e("IdentifyEmptyHoursService", "empty timeline element (0): $emptyValue")
        }

        return emptyHours
    }
}
