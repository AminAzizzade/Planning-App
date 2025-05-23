package com.example.planningapp.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.planningapp.data.entity.Day
import com.example.planningapp.data.entity.TaskStatus
import com.example.planningapp.data.repository.DailyPlanningRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyPlanningViewModel @Inject constructor(private var repository: DailyPlanningRepository) : ViewModel()
{
    val monthDays = MutableLiveData<HashMap<Int, Day>>()

    fun resetViewModel(month: Int, year: Int)
    {
        getMonthDays(month, year)
    }

    fun getMonthDays(month: Int, year: Int)
    {
        CoroutineScope(Dispatchers.Main).launch {
            monthDays.value = repository.uploadTimeLineTasksForMonth(month, year)
        }
    }

    fun insertTimeLineTask(day: Int, month: Int, year: Int, taskName : String, startTime: Int, endTime: Int)
    {
        CoroutineScope(Dispatchers.Main).launch {
            repository.insertDatabase(day, month, year, taskName, startTime, endTime)
        }

    }


    fun deleteTimeLineTask(id:Int) {
        CoroutineScope(Dispatchers.Main).launch {
            repository.deleteTimeLineTaskById(id)
        }
    }


    fun updateTimeLineTask(id: Int, taskName: String, startTime: Int, endTime: Int,) {
        CoroutineScope(Dispatchers.Main).launch {
            repository.updateTimeLineTaskById(id, taskName, startTime, endTime)
        }
    }


    fun updateTaskStatus(id: Int, status: TaskStatus) {
        CoroutineScope(Dispatchers.Main).launch {
            repository.updateTaskStatusById(id, status)
        }
    }
}