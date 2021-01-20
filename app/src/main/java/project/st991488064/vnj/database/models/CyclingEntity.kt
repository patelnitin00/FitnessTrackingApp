package project.st991488064.vnj.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cycling")
data class CyclingEntity(

    @PrimaryKey(autoGenerate = true)
    var id: Long,

    @ColumnInfo(name = "activityName")
    var activityName: String = "Cycling",

    @ColumnInfo(name = "distance")
    var distance: String,

    @ColumnInfo(name = "activityDate")
    var activityDate: String,

    @ColumnInfo(name = "startTime")
    var startTime: String,

    @ColumnInfo(name = "endTime")
    var endTime: String,

    @ColumnInfo(name = "duration")
    var duration: String,

    @ColumnInfo(name = "activityAddTime")
    var activityAddTime: String

)