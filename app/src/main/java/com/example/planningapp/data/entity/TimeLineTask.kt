package com.example.planningapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "TimeLineTask")
data class TimeLineTask (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "TaskID") @NotNull var taskID: Int,

    @ColumnInfo(name = "Day") @NotNull var day: Int,

    @ColumnInfo(name = "Month") @NotNull var month: Int,

    @ColumnInfo(name = "Year") @NotNull var year: Int,

    @ColumnInfo(name = "Name") @NotNull var taskName: String,

    @ColumnInfo(name = "StartTime") @NotNull var startTime: Int,

    @ColumnInfo(name = "EndTime")   @NotNull var endTime: Int
)
