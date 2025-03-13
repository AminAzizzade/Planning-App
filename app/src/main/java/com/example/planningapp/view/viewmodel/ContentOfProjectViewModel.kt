package com.example.planningapp.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.planningapp.data.entity.project.ProjectHistory
import com.example.planningapp.data.entity.project.ProjectTask
import com.example.planningapp.data.repository.project.ProjectHistoryRepository
import com.example.planningapp.data.repository.project.ProjectTaskRepository
import com.example.planningapp.data.repository.project.ProjectTaskResourceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContentOfProjectViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val projectHistoryRepository: ProjectHistoryRepository,
    private val projectTaskRepository: ProjectTaskRepository,
    private val projectTaskResourceRepository: ProjectTaskResourceRepository
) : ViewModel()
{
    val projectId: String? = savedStateHandle["projectId"]

    val history = MutableLiveData<List<ProjectHistory>>()
    val tasks = MutableLiveData<List<ProjectTask>>()

    init
    {
        if (
            projectId != null
        )
        {
            val projectId = projectId.toInt()
            getProjectHistory(projectId)
            getProjectTasks(projectId)
        }
    }

    fun resetViewModel() {
        if (
            projectId != null
        )
        {
            val projectId = projectId.toInt()
            getProjectHistory(projectId)
            getProjectTasks(projectId)
        }
    }

    private fun getProjectHistory(projectId: Int)
    {
        CoroutineScope(Dispatchers.Main).launch {
            history.value = projectHistoryRepository.getProjectHistory(projectId)
        }
    }

    private fun getProjectTasks(projectId: Int)
    {
        CoroutineScope(Dispatchers.Main).launch {
            tasks.value = projectTaskRepository.getProjectTasks(projectId)
        }
    }

    fun insertProjectHistory(newHistory: ProjectHistory)
    {
        CoroutineScope(Dispatchers.Main).launch {
            projectHistoryRepository.insertProjectHistory(newHistory)
        }
    }

    fun insertProjectTask(newTask: ProjectTask)
    {
        CoroutineScope(Dispatchers.Main).launch {
            projectTaskRepository.insertProjectTask(newTask)
        }
    }

    fun deleteProjectHistory(projectHistoryId: Int)
    {
        CoroutineScope(Dispatchers.Main).launch {
            projectHistoryRepository.deleteProjectHistory(projectHistoryId)
        }
    }

    fun deleteProjectTask(projectTaskId: Int)
    {
        CoroutineScope(Dispatchers.Main).launch {
            projectTaskRepository.deleteProjectTask(projectTaskId)
        }
    }
}