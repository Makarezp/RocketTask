package com.task.spacex.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.task.spacex.util.OffsetDateTimeConverter
import java.time.OffsetDateTime

@Database(
    entities = [
        LaunchEntity::class,
        PageKeyEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class SpaceXDatabase : RoomDatabase() {

    abstract fun launchDao(): LaunchDao
    abstract fun pageKeys(): PageKeyDao

}

class Converters {

    @TypeConverter
    fun toString(value: OffsetDateTime): String {
        return  OffsetDateTimeConverter.toString(value)
    }

    @TypeConverter
    fun toZonedDateTime(value: String): OffsetDateTime {
        return OffsetDateTimeConverter.fromString(value)
    }
}
