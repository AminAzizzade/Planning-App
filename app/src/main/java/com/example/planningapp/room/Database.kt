package com.example.planningapp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.planningapp.data.entity.TimeLineTask

@Database(entities = [TimeLineTask::class], version = 1)
abstract class Database: RoomDatabase()
{
    abstract fun getTimeLineTaskDAO(): TimeLineTaskDAO
}