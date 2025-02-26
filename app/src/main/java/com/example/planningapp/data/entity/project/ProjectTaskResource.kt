package com.example.planningapp.data.entity.project

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(
    tableName = "ProjectTaskResource",
    foreignKeys = [
        ForeignKey(
            entity = ProjectTask::class,
            parentColumns = ["ProjectTaskID"],  // Column in the parent entity
            childColumns = ["ProjectTaskID"],     // Column in this entity
            onDelete = ForeignKey.CASCADE  // Optionally cascade deletes
        )
    ],
    indices = [Index("ProjectTaskID")] // Improves query performance on foreign key field
)
data class ProjectTaskResource(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ProjectTaskResourceID") @NotNull var projectTaskResourceId: Int,
    @ColumnInfo(name = "ProjectTaskID") @NotNull var projectTaskId: Int,
    @ColumnInfo(name = "ResourceName") @NotNull var resourceName: String,
    @ColumnInfo(name = "ResourceCheck") @NotNull var resourceCheck: Boolean
)
