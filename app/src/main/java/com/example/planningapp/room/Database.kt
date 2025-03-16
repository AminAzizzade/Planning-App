package com.example.planningapp.room

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.planningapp.data.entity.CheckBoxMission
import com.example.planningapp.data.entity.Converters
import com.example.planningapp.data.entity.TaskContent
import com.example.planningapp.data.entity.TimeLineTask
import com.example.planningapp.data.entity.project.HistoryConverter
import com.example.planningapp.data.entity.project.Project
import com.example.planningapp.data.entity.project.ProjectHistory
import com.example.planningapp.data.entity.project.ProjectTask
import com.example.planningapp.data.entity.project.ProjectTaskResource
import com.example.planningapp.room.project.ProjectDAO
import com.example.planningapp.room.project.ProjectHistoryDAO
import com.example.planningapp.room.project.ProjectTaskDAO
import com.example.planningapp.room.project.ProjectTaskResourceDAO

@Database(
    entities = [
        TimeLineTask::class,
        TaskContent::class,
        CheckBoxMission::class,
        Project::class,
        ProjectTask::class,
        ProjectHistory::class,
        ProjectTaskResource::class
               ],
    version = 3,
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
        AutoMigration(from = 2, to = 3)
    ]
)

@TypeConverters(value = [
        Converters::class,
        HistoryConverter::class
    ]
)
abstract class Database() : RoomDatabase() {

    abstract fun getTimeLineTaskDAO(): TimeLineTaskDAO

    abstract fun getTaskContentDAO(): TaskContentDAO

    abstract fun getCheckBoxMissionDAO(): CheckBoxMissionDAO

    abstract fun getProjectDAO(): ProjectDAO

    abstract fun getProjectHistoryDAO(): ProjectHistoryDAO

    abstract fun getProjectTaskDAO(): ProjectTaskDAO

    abstract fun getProjectTaskResourceDAO(): ProjectTaskResourceDAO

}