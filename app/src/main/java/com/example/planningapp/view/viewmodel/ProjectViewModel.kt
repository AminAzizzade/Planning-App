package com.example.planningapp.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.planningapp.data.entity.project.Project
import com.example.planningapp.data.entity.project.ProjectHistory
import com.example.planningapp.data.entity.project.ProjectTask
import com.example.planningapp.data.entity.project.ProjectTaskResource
import com.example.planningapp.data.repository.project.ProjectHistoryRepository
import com.example.planningapp.data.repository.project.ProjectRepository
import com.example.planningapp.data.repository.project.ProjectTaskRepository
import com.example.planningapp.data.repository.project.ProjectTaskResourceRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProjectViewModel @Inject constructor(
    private val projectRepository: ProjectRepository,
    private val projectHistoryRepository: ProjectHistoryRepository,
    private val projectTaskRepository: ProjectTaskRepository,
    private val projectTaskResourceRepository: ProjectTaskResourceRepository
) : ViewModel()
{
    val projects = MutableLiveData<List<Project>>()
    val historiesForProject = MutableLiveData<HashMap<Int, List<ProjectHistory>>>()
    val tasksForProject = MutableLiveData<HashMap<Int, List<ProjectTask>>>()
    val resourcesForTask = MutableLiveData<HashMap<Int, List<ProjectTaskResource>>>()



    fun getAllProjects()
    {
        CoroutineScope(Dispatchers.Main).launch{
            projects.value = projectRepository.getAllProjects()
        }
    }

    fun insertProject(project: Project)
    {
        CoroutineScope(Dispatchers.Main).launch{
            projectRepository.insertProject(project)
        }
    }

    fun deleteProject(projectId: Int)
    {
        CoroutineScope(Dispatchers.Main).launch {
            projectRepository.deleteProject(projectId)
        }
    }



    fun getAllHistoriesForProject()
    {
        CoroutineScope(Dispatchers.Main).launch {
            historiesForProject.value = projectHistoryRepository.getAllProjectHistory()
        }
    }

    fun insertProjectHistory(projectHistory: ProjectHistory)
    {
        CoroutineScope(Dispatchers.Main).launch {
            projectHistoryRepository.insertProjectHistory(projectHistory)
        }
    }

    fun deleteProjectHistory(projectHistoryId: Int)
    {
        CoroutineScope(Dispatchers.Main).launch {
            projectHistoryRepository.deleteProjectHistory(projectHistoryId)
        }
    }



    fun getAllTasksForProject()
    {
        CoroutineScope(Dispatchers.Main).launch {
            tasksForProject.value = projectTaskRepository.getAllProjectTasks()
        }
    }

    fun insertProjectTask(projectTask: ProjectTask)
    {
        CoroutineScope(Dispatchers.Main).launch {
            projectTaskRepository.insertProjectTask(projectTask)
        }
    }

    fun deleteProjectTask(projectTaskId: Int)
    {
        CoroutineScope(Dispatchers.Main).launch {
            projectTaskRepository.deleteProjectTask(projectTaskId)
        }
    }



    fun getAllResourcesForTask()
    {
        CoroutineScope(Dispatchers.Main).launch {
            resourcesForTask.value = projectTaskResourceRepository.getAllProjectTaskResources()
        }
    }

    fun insertProjectTaskResource(projectTaskResource: ProjectTaskResource)
    {
        CoroutineScope(Dispatchers.Main).launch {
            projectTaskResourceRepository.insertProjectTaskResource(projectTaskResource)
        }
    }

    fun deleteProjectTaskResource(projectTaskResourceId: Int)
    {
        CoroutineScope(Dispatchers.Main).launch {
            projectTaskResourceRepository.deleteProjectTaskResource(projectTaskResourceId)
        }
    }

    fun updateProjectTaskResource(projectTaskResource: ProjectTaskResource)
    {
        CoroutineScope(Dispatchers.Main).launch {
            projectTaskResourceRepository.updateProjectTaskResource(projectTaskResource)
        }
    }
}