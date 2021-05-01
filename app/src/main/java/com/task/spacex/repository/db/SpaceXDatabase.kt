package com.task.spacex.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        LaunchEntity::class,
        PageKeyEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class SpaceXDatabase : RoomDatabase() {

    abstract fun launchDao(): LaunchDao
    abstract fun pageKeys(): PageKeyDao

}
