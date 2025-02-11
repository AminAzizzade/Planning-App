package com.example.planningapp.data.entity

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun fromTaskLabel(value: TaskLabel): String {
        return value.name  // Convert enum to its name (e.g., "WORK")
    }

    @TypeConverter
    fun toTaskLabel(value: String): TaskLabel {
        return TaskLabel.valueOf(value)  // Convert string back to enum
    }
}
