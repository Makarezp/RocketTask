package com.task.spacex.repository.db

import androidx.room.*

@Dao
interface PageKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pageKey: PageKeyEntity)

    @Delete
    suspend fun delete(pageKey: PageKeyEntity)

}
