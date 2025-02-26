package com.example.planningapp.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planningapp.data.entity.CheckBoxMission
import com.example.planningapp.data.entity.TaskContent
import com.example.planningapp.data.repository.CheckBoxMissionRepository
import com.example.planningapp.data.repository.ContentOfTaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContentOfTaskViewModel @Inject constructor(
    private var repositoryCoT: ContentOfTaskRepository,
    private var repositoryCBM: CheckBoxMissionRepository
) : ViewModel()
{

    val allTasks = MutableLiveData<HashMap<Int, TaskContent>>()
    val allMissions = MutableLiveData<HashMap<Int, List<CheckBoxMission>>>()

    init{
        getAllTasks()
        getAllMissions()
    }

    fun resetViewModel()
    {
        getAllTasks()
        getAllMissions()
    }

    private fun getAllTasks()
    {
        CoroutineScope(Dispatchers.Main).launch {
            allTasks.value = repositoryCoT.getAllContents()
        }
    }

    private fun getAllMissions()
    {
        CoroutineScope(Dispatchers.Main).launch {
            allMissions.value = repositoryCBM.getAllMissions()
        }
    }

    fun insertTaskContent(taskContent: TaskContent)
    {
        CoroutineScope(Dispatchers.Main).launch {
            repositoryCoT.insertContent(taskContent)
        }
    }

    fun insertMission(mission: CheckBoxMission)
    {
        CoroutineScope(Dispatchers.Main).launch {
            repositoryCBM.insertMission(mission)
        }
    }

    fun deleteMission(mission: CheckBoxMission) {
        CoroutineScope(Dispatchers.Main).launch {
            repositoryCBM.deleteMission(mission)
        }
    }

    fun updateTaskContent(taskContent: TaskContent)
    {
        viewModelScope.launch {
            repositoryCoT.updateContent(taskContent)
        }
    }

    fun updateOneMission(mission: CheckBoxMission)
    {
        CoroutineScope(Dispatchers.Main).launch {
            repositoryCBM.updateMission(mission)
        }
    }
}