package com.example.planningapp.dependencyInjection


import android.content.Context
import androidx.room.Room
import com.example.planningapp.data.datasource.CheckBoxMissionDataSource
import com.example.planningapp.data.datasource.ContentOfTaskDataSource
import com.example.planningapp.data.datasource.DailyPlanningDataSource
import com.example.planningapp.data.datasource.project.ProjectDataSource
import com.example.planningapp.data.datasource.project.ProjectHistoryDataSource
import com.example.planningapp.data.datasource.project.ProjectTaskDataSource
import com.example.planningapp.data.datasource.project.ProjectTaskResourceDataSource
import com.example.planningapp.data.repository.CheckBoxMissionRepository
import com.example.planningapp.data.repository.ContentOfTaskRepository
import com.example.planningapp.data.repository.DailyPlanningRepository
import com.example.planningapp.data.repository.project.ProjectHistoryRepository
import com.example.planningapp.data.repository.project.ProjectRepository
import com.example.planningapp.data.repository.project.ProjectTaskRepository
import com.example.planningapp.data.repository.project.ProjectTaskResourceRepository
import com.example.planningapp.room.CheckBoxMissionDAO
import com.example.planningapp.room.Database
import com.example.planningapp.room.TaskContentDAO
import com.example.planningapp.room.TimeLineTaskDAO
import com.example.planningapp.room.project.ProjectDAO
import com.example.planningapp.room.project.ProjectHistoryDAO
import com.example.planningapp.room.project.ProjectTaskDAO
import com.example.planningapp.room.project.ProjectTaskResourceDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule
{
    @Provides
    @Singleton
    fun provideDailyPlanningRepository(dataSource: DailyPlanningDataSource): DailyPlanningRepository{
        return DailyPlanningRepository(dataSource)
    }

    @Provides
    @Singleton
    fun provideDailyPlanningDataSource(timeLineTaskDAO: TimeLineTaskDAO): DailyPlanningDataSource {
        return DailyPlanningDataSource(timeLineTaskDAO)
    }




    @Provides
    @Singleton
    fun provideContentOfTaskRepository(dataSource: ContentOfTaskDataSource): ContentOfTaskRepository{
        return ContentOfTaskRepository(dataSource)
    }

    @Provides
    @Singleton
    fun provideContentOfTaskDataSource(taskContentDAO: TaskContentDAO): ContentOfTaskDataSource {
        return ContentOfTaskDataSource(taskContentDAO)
    }




    @Provides
    @Singleton
    fun provideCheckBoxMissionRepository(dataSource: CheckBoxMissionDataSource): CheckBoxMissionRepository {
        return CheckBoxMissionRepository(dataSource)
    }

    @Provides
    @Singleton
    fun provideCheckBoxMissionDataSource(checkBoxMissionDAO: CheckBoxMissionDAO): CheckBoxMissionDataSource {
        return CheckBoxMissionDataSource(checkBoxMissionDAO)
    }




    @Provides
    @Singleton
    fun provideProjectRepository(dataSource: ProjectDataSource): ProjectRepository {
        return ProjectRepository(dataSource)
    }

    @Provides
    @Singleton
    fun provideProjectDataSource(projectDAO: ProjectDAO): ProjectDataSource {
        return ProjectDataSource(projectDAO)
    }




    @Provides
    @Singleton
    fun provideProjectHistoryRepository(dataSource: ProjectHistoryDataSource): ProjectHistoryRepository {
        return ProjectHistoryRepository(dataSource)
    }

    @Provides
    @Singleton
    fun provideProjectHistoryDataSource(projectHistoryDAO: ProjectHistoryDAO): ProjectHistoryDataSource {
        return ProjectHistoryDataSource(projectHistoryDAO)
    }




    @Provides
    @Singleton
    fun provideProjectTaskRepository(dataSource: ProjectTaskDataSource): ProjectTaskRepository {
        return ProjectTaskRepository(dataSource)
    }

    @Provides
    @Singleton
    fun provideProjectTaskDataSource(projectTaskDAO: ProjectTaskDAO): ProjectTaskDataSource {
        return ProjectTaskDataSource(projectTaskDAO)
    }


    @Provides
    @Singleton
    fun provideProjectTaskResourceRepository(dataSource: ProjectTaskResourceDataSource): ProjectTaskResourceRepository {
        return ProjectTaskResourceRepository(dataSource)
    }

    @Provides
    @Singleton
    fun provideProjectTaskResourceDataSource(projectTaskResourceDAO: ProjectTaskResourceDAO): ProjectTaskResourceDataSource {
        return ProjectTaskResourceDataSource(projectTaskResourceDAO)
    }


    @Provides
    @Singleton
    fun provideTimeLineTaskDAO(@ApplicationContext context: Context): TimeLineTaskDAO
    {
        val database = Room.databaseBuilder(context, Database::class.java, "planyourcareer.db")
            .createFromAsset("planyourcareer.db").build()
        return database.getTimeLineTaskDAO()
    }

    @Provides
    @Singleton
    fun provideTaskContentDAO(@ApplicationContext context: Context): TaskContentDAO
    {
        val database = Room.databaseBuilder(context, Database::class.java, "planyourcareer.db")
            .createFromAsset("planyourcareer.db").build()
        return database.getTaskContentDAO()
    }

    @Provides
    @Singleton
    fun provideCheckBoxMissionDAO(@ApplicationContext context: Context): CheckBoxMissionDAO
    {
        val database = Room.databaseBuilder(context, Database::class.java, "planyourcareer.db")
            .createFromAsset("planyourcareer.db").build()
        return database.getCheckBoxMissionDAO()
    }




    @Provides
    @Singleton
    fun provideProjectDAO(@ApplicationContext context: Context): ProjectDAO
    {
        val database = Room.databaseBuilder(context, Database::class.java, "planyourcareer.db")
            .createFromAsset("planyourcareer.db").build()
        return database.getProjectDAO()
    }

    @Provides
    @Singleton
    fun provideProjectHistoryDAO(@ApplicationContext context: Context): ProjectHistoryDAO
    {
        val database = Room.databaseBuilder(context, Database::class.java, "planyourcareer.db")
            .createFromAsset("planyourcareer.db").build()
        return database.getProjectHistoryDAO()
    }

    @Provides
    @Singleton
    fun provideProjectTaskDAO(@ApplicationContext context: Context): ProjectTaskDAO
    {
        val database = Room.databaseBuilder(context, Database::class.java, "planyourcareer.db")
            .createFromAsset("planyourcareer.db").build()
        return database.getProjectTaskDAO()
    }

    @Provides
    @Singleton
    fun provideProjectTaskResourceDAO(@ApplicationContext context: Context): ProjectTaskResourceDAO
    {
        val database = Room.databaseBuilder(context, Database::class.java, "planyourcareer.db")
            .createFromAsset("planyourcareer.db").build()
        return database.getProjectTaskResourceDAO()
    }
}


