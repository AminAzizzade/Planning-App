package com.example.planningapp.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.planningapp.data.entity.project.ProjectDescription
import com.example.planningapp.data.entity.project.ProjectHistory
import com.example.planningapp.data.entity.project.ProjectTask
import com.example.planningapp.data.repository.project.ProjectDescriptionRepository
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
    private val projectTaskResourceRepository: ProjectTaskResourceRepository,
    private val projectDescriptionRepository: ProjectDescriptionRepository
) : ViewModel()
{
    val projectId: String? = savedStateHandle["projectId"]

    val history = MutableLiveData<List<ProjectHistory>>()
    val tasks = MutableLiveData<List<ProjectTask>>()
    val projectDescription = MutableLiveData<ProjectDescription>()

    init
    {
        if (
            projectId != null
        )
        {
            val projectId = projectId.toInt()
            getProjectHistory(projectId)
            getProjectTasks(projectId)
            getProjectDescription(projectId)
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
            getProjectDescription(projectId)
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

    private fun getProjectDescription(projectId: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            projectDescription.value = projectDescriptionRepository.getProjectDescription(projectId)
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

    fun insertProjectDescription(projectId: Int, description: String) {
        CoroutineScope(Dispatchers.Main).launch {
            projectDescriptionRepository.insertProjectDescription(projectId, description)
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

    fun deleteProjectDescription(projectDescriptionId: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            projectDescriptionRepository.deleteProjectDescription(projectDescriptionId)
        }
    }

    fun updateProjectDescription(projectDescriptionId: Int, description: String) {
        CoroutineScope(Dispatchers.Main).launch {
            projectDescriptionRepository.updateProjectDescription(projectDescriptionId, description)
        }
    }
}