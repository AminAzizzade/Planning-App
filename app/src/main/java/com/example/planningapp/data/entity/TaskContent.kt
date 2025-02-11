package com.example.planningapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(
    tableName = "TaskContent",
    foreignKeys = [
        ForeignKey(
            entity = TimeLineTask::class,
            parentColumns = ["TaskID"],  // Column in the parent entity
            childColumns = ["TaskID"],     // Column in this entity
            onDelete = ForeignKey.CASCADE  // Optionally cascade deletes
        )
    ],
    indices = [Index("TaskID")] // Improves query performance on foreign key field
)
data class TaskContent(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ContentID") @NotNull var contentId: Int,
    @ColumnInfo(name = "TaskID") @NotNull var taskId: Int,
    @ColumnInfo(name = "Note")   @NotNull var missionNote: String,
    @ColumnInfo(name = "Label")  @NotNull var label: TaskLabel
)
