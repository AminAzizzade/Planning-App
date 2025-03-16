package com.example.planningapp.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.planningapp.data.entity.project.Project
import com.example.planningapp.data.repository.project.ProjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectViewModel @Inject constructor(
    private val projectRepository: ProjectRepository,
) : ViewModel()
{
    val projects = MutableLiveData<List<Project>>()

    init {
        resetViewModel()
    }

    fun resetViewModel()
    {
        getAllProjects()
    }

    private fun getAllProjects()
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
}