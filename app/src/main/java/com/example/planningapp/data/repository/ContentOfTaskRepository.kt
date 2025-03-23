package com.example.planningapp.data.repository

import com.example.planningapp.data.datasource.ContentOfTaskDataSource
import com.example.planningapp.data.entity.TaskContent

class ContentOfTaskRepository (private val dataSource: ContentOfTaskDataSource)
{

    suspend fun insertContent(taskContent: TaskContent)
    = dataSource.insertContent(taskContent)

    suspend fun updateContent(taskContent: TaskContent)
    = dataSource.updateContent(taskContent)

    suspend fun getAllContents(): HashMap<Int, TaskContent>
    = dataSource.getAllContents()

    suspend fun getTask(taskId: Int): TaskContent
    = dataSource.getTask(taskId)

}