package com.example.planningapp.data.entity.project

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.planningapp.data.entity.TaskContent
import org.jetbrains.annotations.NotNull

@Entity(
    tableName = "ProjectHistory",
    foreignKeys = [
        ForeignKey(
            entity = TaskContent::class,
            parentColumns = ["ContentID"],  // Column in the parent entity
            childColumns = ["ContentID"],     // Column in this entity
            onDelete = ForeignKey.CASCADE  // Optionally cascade deletes
        )
    ],
    indices = [Index("ContentID")] // Improves query performance on foreign key field
)
data class ProjectHistory(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ProjectHistoryID") @NotNull var projectHistoryId: Int,
    @ColumnInfo(name = "ProjectID") @NotNull var projectId: Int,
    @ColumnInfo(name = "ProjectHistoryName") @NotNull var projectHistoryName: String,
    @ColumnInfo(name = "HistoryLabel") @NotNull var historyLabel: HistoryLabel,
)
