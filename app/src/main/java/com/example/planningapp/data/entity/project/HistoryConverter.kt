package com.example.planningapp.data.entity.project

import androidx.room.TypeConverter

class HistoryConverter
{
    @TypeConverter
    fun fromHistoryLabel(value: HistoryLabel): String {
        return value.name  // Convert enum to its name (e.g., "WORK")
    }

    @TypeConverter
    fun toHistoryLabel(value: String): HistoryLabel {
        return HistoryLabel.valueOf(value)  // Convert string back to enum
    }
}