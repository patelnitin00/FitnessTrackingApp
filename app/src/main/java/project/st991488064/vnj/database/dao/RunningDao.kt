package project.st991488064.vnj.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import project.st991488064.vnj.database.models.RunningEntity

@Dao
interface RunningDao {


    @Query("SELECT * FROM running")
    fun getAllRunningData(): LiveData<List<RunningEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRunningData(runningEntity: RunningEntity)

    @Update
    suspend fun updateRunningEntity(runningEntity: RunningEntity)

    @Query("delete from running")
    suspend fun deleteAllRunningEntity()

    @Query("delete from running where id = :id")
    suspend fun deleteRunningEntity(id: Long)

}