package com.task.spacex.repository.db

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.OffsetDateTime

@Entity(tableName = "launches")
data class LaunchEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "missionName")
    val missionName: String,
    @ColumnInfo(name = "patchUrl")
    val patchUrl: String?,
    @ColumnInfo(name = "zonedDate")
    val offsetDateTime: OffsetDateTime,
    @ColumnInfo(name = "success")
    val success: Boolean?,
    @ColumnInfo(name = "upcoming")
    val upcoming: Boolean,
    @ColumnInfo(name = "webcast")
    val webcast: String?,
    @ColumnInfo(name = "article")
    val article: String?,
    @ColumnInfo(name = "wikipedia")
    val wikipedia: String?,
    @Embedded
    val rocket: Rocket
) {

    data class Rocket(
        @ColumnInfo(name = "name")
        val name: String,
        @ColumnInfo(name = "type")
        val type: String
    )
}
