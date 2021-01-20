package project.st991488064.vnj.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import project.st991488064.vnj.database.dao.CyclingDao
import project.st991488064.vnj.database.dao.RunningDao
import project.st991488064.vnj.database.dao.WeightLiftingDao
import project.st991488064.vnj.database.models.CyclingEntity
import project.st991488064.vnj.database.models.RunningEntity
import project.st991488064.vnj.database.models.WeightLiftingEntity

@Database(
    entities = [CyclingEntity::class, RunningEntity::class, WeightLiftingEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ActivityDatabase : RoomDatabase() {

    abstract fun cyclingDao(): CyclingDao
    abstract fun runningDao(): RunningDao
    abstract fun weightLiftingDao(): WeightLiftingDao

    companion object {

        //making CyclingDatabase as a singleton class - can be initiated once and then we will use the same instance
        //having multiple instance will be costlier ane will degrade the app performance
        //volatile makes any writes to this variable will be immediately visible to other threads
        @Volatile
        private var INSTANCE: ActivityDatabase? = null

        fun getInstance(context: Context): ActivityDatabase {

            //synchronised help us to protect concurrent execution from multiple thread
            kotlin.synchronized(this) {

                var instant = INSTANCE

                //checking if instance is null or not
                //if null we will create a new instance else we will return the already existing one
                if (instant == null) {
                    instant = Room.databaseBuilder(
                        context.applicationContext,
                        ActivityDatabase::class.java,
                        "activities.db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instant
                }

                return instant
            }
        }

    }

}