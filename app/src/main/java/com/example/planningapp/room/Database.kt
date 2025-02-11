package com.example.planningapp.room

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.planningapp.data.entity.CheckBoxMission
import com.example.planningapp.data.entity.Converters
import com.example.planningapp.data.entity.TaskContent
import com.example.planningapp.data.entity.TimeLineTask

@Database(entities = [TimeLineTask::class, TaskContent::class, CheckBoxMission::class],
    version = 2,
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ]
)

@TypeConverters(Converters::class)
abstract class Database: RoomDatabase()
{
    abstract fun getTimeLineTaskDAO(): TimeLineTaskDAO
}