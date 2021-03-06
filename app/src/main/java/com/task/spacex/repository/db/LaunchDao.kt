package com.task.spacex.repository.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LaunchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(launches: List<LaunchEntity>)

    @Query("SELECT * FROM launches WHERE id = :id")
    suspend fun getLaunchById(id: String): LaunchEntity

    @Query("SELECT * FROM launches")
    fun getLaunches(): PagingSource<Int, LaunchEntity>

    @Query("DELETE FROM launches")
    suspend fun deleteAll()
}
