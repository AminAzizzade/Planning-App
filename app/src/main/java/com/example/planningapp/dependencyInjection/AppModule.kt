package com.example.planningapp.dependencyInjection


import android.content.Context
import androidx.room.Room
import com.example.planningapp.data.datasource.DailyPlanningDataSource
import com.example.planningapp.data.repository.DailyPlanningRepository
import com.example.planningapp.room.Database
import com.example.planningapp.room.TimeLineTaskDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
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
    fun provideTimeLineTaskDAO(@ApplicationContext context: Context): TimeLineTaskDAO
    {
        val database = Room.databaseBuilder(context, Database::class.java, "planyourcareer.db")
            .createFromAsset("planyourcareer.db").build()
        return database.getTimeLineTaskDAO()
    }
}


