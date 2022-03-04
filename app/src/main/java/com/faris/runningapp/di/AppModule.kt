package com.faris.runningapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.faris.runningapp.db.RunningDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRunningDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        RunningDatabase::class.java,
        RunningDatabase.DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideRunDao(database: RunningDatabase) = database.getRunDao()


}