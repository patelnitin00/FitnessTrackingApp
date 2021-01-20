package project.st991488064.vnj.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weightlifting")
data class WeightLiftingEntity(

    @PrimaryKey(autoGenerate = true)
    var id: Long,

    @ColumnInfo(name = "activityName")
    var activityName: String = "Weight Lifting",

    @ColumnInfo(name = "sets")
    var sets: String,

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