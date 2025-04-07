package com.example.planningapp.data.entity.project

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(
    tableName = "ProjectDescription",
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
data class ProjectDescription (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ProjectDescriptionID") @NotNull var projectDescriptionId: Int,
    @ColumnInfo(name = "ProjectID") @NotNull var projectId: Int,
    @ColumnInfo(name = "Description") @NotNull var description: String,
)