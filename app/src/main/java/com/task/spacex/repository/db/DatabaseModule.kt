package com.task.spacex.repository.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): SpaceXDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            SpaceXDatabase::class.java,
            "SpaceXRepository.db"
        ).build()

    @Singleton
    @Provides
    fun provideLaunchDao(database: SpaceXDatabase): LaunchDao =
        database.launchDao()

    @Singleton
    @Provides
    fun providePageKeyDao(database: SpaceXDatabase): PageKeyDao =
        database.pageKeys()

}
