package com.task.spacex.repository.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.ZonedDateTime

@Entity(tableName = "launches")
data class LaunchEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "rocket")
    val rocket: String,
    @ColumnInfo(name = "patchUrl")
    val patchUrl: String?,
    @ColumnInfo(name = "zonedDate")
    val zonedDateTime: ZonedDateTime
)
