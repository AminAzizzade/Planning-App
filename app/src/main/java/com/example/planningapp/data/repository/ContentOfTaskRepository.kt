package com.example.planningapp.data.repository

import com.example.planningapp.data.datasource.ContentOfTaskDataSource

class ContentOfTaskRepository (private val dataSource: ContentOfTaskDataSource)
{
    suspend fun test() = dataSource.testRemove()
}