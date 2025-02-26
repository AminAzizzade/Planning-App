package com.example.planningapp.data.entity.project

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "Project")
data class Project (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ProjectID") @NotNull var projectId: Int,
    @ColumnInfo(name = "ProjectName") @NotNull var projectName: String
)