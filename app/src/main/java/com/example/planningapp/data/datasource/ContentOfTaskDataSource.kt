package com.example.planningapp.data.datasource

import com.example.planningapp.data.entity.TaskContent
import com.example.planningapp.data.entity.TaskLabel
import com.example.planningapp.room.TaskContentDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContentOfTaskDataSource(private val dao: TaskContentDAO)
{
    suspend fun testInsert() = withContext(Dispatchers.IO)
    {
        val foreignKey = 14379
        val content = TaskContent(
            contentId = 0,
            taskId = foreignKey,
            missionNote = "test",
            label = TaskLabel.HOME
        )

        dao.insertContent(content)
    }

    suspend fun testRemove() = withContext(Dispatchers.IO)
    {
        val foreignKey = 14379
        val content = TaskContent(
            contentId = 0,
            taskId = foreignKey,
            missionNote = "test",
            label = TaskLabel.HOME
        )

        dao.removeContent(foreignKey)
    }
}