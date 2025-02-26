package com.example.planningapp.data.entity.project

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(
    tableName = "ProjectTask",
    foreignKeys = [
        ForeignKey(
            entity = Project::class,
            parentColumns = ["ProjectID"],  // Column in the parent entity
            childColumns = ["ProjectID"],     // Column in this entity
            onDelete = ForeignKey.CASCADE  // Optionally cascade deletes
        )
    ],
    indices = [Index("ProjectID")] // Improves query performance on foreign key field
)
data class ProjectTask(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ProjectTaskID") @NotNull var projectTaskId: Int,
    @ColumnInfo(name = "ProjectID") @NotNull var projectId: Int,
    @ColumnInfo(name = "TaskName") @NotNull var taskName: String
    )
