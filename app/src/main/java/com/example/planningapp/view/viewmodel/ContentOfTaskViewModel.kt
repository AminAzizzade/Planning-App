package com.example.planningapp.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planningapp.data.entity.CheckBoxMission
import com.example.planningapp.data.entity.TaskContent
import com.example.planningapp.data.repository.CheckBoxMissionRepository
import com.example.planningapp.data.repository.ContentOfTaskRepository
import com.example.planningapp.view.ContentMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContentOfTaskViewModel @Inject constructor(
    private var repositoryCoT: ContentOfTaskRepository,
    private var repositoryCBM: CheckBoxMissionRepository
) : ViewModel()
{

    val missions = MutableLiveData<List<CheckBoxMission>>(emptyList())
    val content = MutableLiveData<TaskContent>(null)
    val contentLabel = MutableLiveData(ContentMode.INSERT)
    val missionLabel = MutableLiveData(ContentMode.INSERT)


    fun abstractionForView(taskId: Int)
    {
        getTaskContents(taskId)

        if (content.value != null)
        {
            getMissions(content.value!!.contentId)
            contentLabel.value = ContentMode.UPDATE
            if (missions.value!!.isNotEmpty()) missionLabel.value = ContentMode.UPDATE
        }
    }

    fun insertTaskContent(taskContent: TaskContent)
    {
        viewModelScope.launch {
            repositoryCoT.insertContent(taskContent)
        }
    }

    fun insertMission(mission: CheckBoxMission)
    {
        viewModelScope.launch {
            repositoryCBM.insertMission(mission)
        }
    }

    fun deleteMission(mission: CheckBoxMission) {
        viewModelScope.launch {
            repositoryCBM.deleteMission(mission)
        }
    }

    fun updateTaskContent(taskContent: TaskContent)
    {
        viewModelScope.launch {
            repositoryCoT.updateContent(taskContent)
        }
    }

    fun updateMissions(missionList: List<CheckBoxMission>)
    {

        viewModelScope.launch {
            repositoryCBM.updateMissions(missionList)
        }
    }

    private fun getTaskContents(taskId: Int) {
        viewModelScope.launch {
            content.value = repositoryCoT.getContents(taskId)
        }
    }

    private fun getMissions(contentId: Int) {
        viewModelScope.launch {
            missions.value = repositoryCBM.getMissions(contentId)
        }
    }
}