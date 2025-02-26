package com.example.planningapp.room

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.planningapp.data.entity.CheckBoxMission
import com.example.planningapp.data.entity.Converters
import com.example.planningapp.data.entity.TaskContent
import com.example.planningapp.data.entity.TimeLineTask
import com.example.planningapp.room.project.ProjectDAO
import com.example.planningapp.room.project.ProjectHistoryDAO
import com.example.planningapp.room.project.ProjectTaskDAO
import com.example.planningapp.room.project.ProjectTaskResourceDAO

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

    abstract fun getTaskContentDAO(): TaskContentDAO

    abstract fun getCheckBoxMissionDAO(): CheckBoxMissionDAO

    abstract fun getProjectDAO(): ProjectDAO

    abstract fun getProjectHistoryDAO(): ProjectHistoryDAO

    abstract fun getProjectTaskDAO(): ProjectTaskDAO

    abstract fun getProjectTaskResourceDAO(): ProjectTaskResourceDAO

}