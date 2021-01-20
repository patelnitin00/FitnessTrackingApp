package project.st991488064.vnj.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import project.st991488064.vnj.database.models.ActivitiesEntity
import project.st991488064.vnj.database.models.CyclingEntity


@Dao
interface CyclingDao {

    @Query("SELECT * FROM cycling")
    fun getAllCyclingData(): LiveData<List<CyclingEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCyclingData(cyclingData: CyclingEntity)

    @Update
    suspend fun updateCyclingEntity(cyclingData: CyclingEntity)


//    @Query("select * from (SELECT * FROM cycling union select * from running union select * from weightlifting ) order by activityDate desc")
//    fun getAll(): LiveData<List<ActivitiesEntity>>

    @Query("SELECT * FROM cycling union select * from running union select * from weightlifting order by activityDate desc")
    fun getAll(): LiveData<List<ActivitiesEntity>>

    @Query("SELECT * FROM cycling where activityDate = :currentDate union select * from running where activityDate = :currentDate union select * from weightlifting where activityDate = :currentDate")
     fun getAllForToday(currentDate : String): LiveData<List<ActivitiesEntity>>

    @Query("delete from cycling")
    suspend fun deleteAllCyclingEntity()

    @Query("delete from cycling where id = :id")
    suspend fun deleteCyclingEntity(id: Long)
    
}