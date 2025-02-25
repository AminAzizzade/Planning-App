package com.example.planningapp.data.datasource

import android.util.Log
import com.example.planningapp.data.entity.TaskContent
import com.example.planningapp.data.entity.TaskLabel
import com.example.planningapp.room.TaskContentDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContentOfTaskDataSource(private val dao: TaskContentDAO)
{
    suspend fun getContents(taskId: Int): TaskContent = withContext(Dispatchers.IO)
    {

        Log.e("TaskContentScreen", "istenilen taskId: $taskId")
        return@withContext dao.getContents(taskId)
    }

    suspend fun insertContent(taskContent: TaskContent) = withContext(Dispatchers.IO)
    {
        dao.insertContent(taskContent)
    }

    suspend fun updateContent(taskContent: TaskContent) = withContext(Dispatchers.IO)
    {
        Log.e("TaskContentScreen",
            "contendId: ${taskContent.contentId} \n"+
                    "updateLabel: ${taskContent.label}")
        dao.updateContent(
            contentId = taskContent.contentId,
            missionNote = taskContent.missionNote,
            label = taskContent.label.toString()
        )
    }

    suspend fun removeContent(taskContent: TaskContent) = withContext(Dispatchers.IO)
    {
        dao.removeContent(taskContent.contentId)
    }

    suspend fun getAllContents(): HashMap<Int, TaskContent> = withContext(Dispatchers.IO)
    {
        val list = dao.getAllContents()
        val map = HashMap<Int, TaskContent>()
        for (content in list)
        {
            map[content.taskId] = content
        }
        return@withContext map
    }


}