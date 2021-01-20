package project.st991488064.vnj.database.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ActivitiesEntity(

        var id: Long,

        var activityName: String,

        var distance: String,

        var activityDate: String,

        var startTime: String,

        var endTime: String,

        var duration: String,

        var activityAddTime: String

) : Parcelable
