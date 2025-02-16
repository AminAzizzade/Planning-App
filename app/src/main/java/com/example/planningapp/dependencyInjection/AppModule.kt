package com.example.planningapp.dependencyInjection


import android.content.Context
import androidx.room.Room
import com.example.planningapp.data.datasource.CheckBoxMissionDataSource
import com.example.planningapp.data.datasource.ContentOfTaskDataSource
import com.example.planningapp.data.datasource.DailyPlanningDataSource
import com.example.planningapp.data.repository.CheckBoxMissionRepository
import com.example.planningapp.data.repository.ContentOfTaskRepository
import com.example.planningapp.data.repository.DailyPlanningRepository
import com.example.planningapp.room.CheckBoxMissionDAO
import com.example.planningapp.room.Database
import com.example.planningapp.room.TaskContentDAO
import com.example.planningapp.room.TimeLineTaskDAO
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
}


