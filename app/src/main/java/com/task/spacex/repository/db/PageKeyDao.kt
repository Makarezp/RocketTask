package com.task.spacex.repository.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PageKeyDao {

    @Query("SELECT * FROM pagekeys WHERE id=:id")
    suspend fun pageKeyById(id: String): PageKeyEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pageKey: PageKeyEntity)

    @Query("DELETE FROM pagekeys")
    suspend fun deleteAll()

}
