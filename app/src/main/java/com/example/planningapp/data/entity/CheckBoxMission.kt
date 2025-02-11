package com.example.planningapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(
    tableName = "CheckBoxMission",
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
data class CheckBoxMission(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "MissionID") @NotNull var missionId: Int,
    @ColumnInfo(name = "ContentID") @NotNull var contentId: Int,  // This now is both the primary key and a foreign key
    @ColumnInfo(name = "Check") @NotNull var check: Boolean,
    @ColumnInfo(name = "MissionName") @NotNull var missionName: String
)
