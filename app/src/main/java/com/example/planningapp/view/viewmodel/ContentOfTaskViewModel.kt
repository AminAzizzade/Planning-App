package com.example.planningapp.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
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
    savedStateHandle: SavedStateHandle,
    private var repositoryCoT: ContentOfTaskRepository,
    private var repositoryCBM: CheckBoxMissionRepository
) : ViewModel()
{

    val taskId: String? = savedStateHandle["taskId"]

    val allTasks = MutableLiveData<HashMap<Int, TaskContent>>()
    val allMissions = MutableLiveData<HashMap<Int, List<CheckBoxMission>>>()

    val task = MutableLiveData<TaskContent>()

    init{
        if (
            taskId != null
        ) {
            //val taskId = taskId.toInt()
            getAllTasks()
            getAllMissions()
        }
    }

    fun resetViewModel()
    {
        if (
            taskId != null
        ) {
            getAllTasks()
            getAllMissions()
        }
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

    fun checkMission(missionId: Int)
    {
        CoroutineScope(Dispatchers.Main).launch {
            repositoryCBM.checkMission(missionId)
        }
    }

    fun uncheckMission(missionId: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            repositoryCBM.uncheckMission(missionId)
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
}