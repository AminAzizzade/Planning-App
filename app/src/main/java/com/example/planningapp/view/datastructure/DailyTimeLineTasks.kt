package com.example.planningapp.view.datastructure


import com.example.planningapp.data.entity.TimeLineTask


class DailyTimeLineTasks {

    val timeLineList = ArrayList<TimeLineTask>()
    private val timeLineSet = HashSet<Int>() // Mutable Set

    fun addTimeLine(task: TimeLineTask) {
        getTaskDuration(task)?.let {
            timeLineSet.addAll(it) // Kullanılabilir zaman aralığını ekle
            addingTimeLineInOrder(task)
        }
    }

    fun clearAllTimeLine() {
        timeLineList.clear()
        timeLineSet.clear()
    }


    fun addAllTimeLine(tasks: List<TimeLineTask>) {
        tasks.forEach { task ->
            addTimeLine(task)
        }
    }

    private fun getTaskDuration(task: TimeLineTask): HashSet<Int>? {
        val newTimeLineSet = HashSet(timeLineSet)
        var index = task.startTime
        val end = task.endTime

        // Zaman çakışması kontrolü
        while (index < end) { // end dahil edilmez
            if (newTimeLineSet.contains(index)) {
                return null // Çakışma var, null döner
            }
            index += 5
        }

        // Çakışma yoksa, zaman aralığını ekle
        index = task.startTime
        while (index < end) { // end dahil edilmez
            newTimeLineSet.add(index)
            index += 5
        }

        return newTimeLineSet
    }

    private fun addingTimeLineInOrder(task: TimeLineTask) {
        val start = task.startTime

        var index = 0
        val length = timeLineList.size
        if (length == 0) return timeLineList.add(index, task)

        while (index < length) {
            if (timeLineList[index].startTime >= start)
                break
            index++
        }
        timeLineList.add(index, task)
    }

}
