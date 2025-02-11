package com.example.planningapp.data.repository

import com.example.planningapp.data.datasource.CheckBoxMissionDataSource

class CheckBoxMissionRepository(private val dataSource: CheckBoxMissionDataSource)
{
    suspend fun test() = dataSource.testRemove()
}