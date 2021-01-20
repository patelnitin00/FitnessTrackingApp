package project.st991488064.vnj.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import project.st991488064.vnj.database.models.WeightLiftingEntity

@Dao
interface WeightLiftingDao {

    @Query("SELECT * FROM weightlifting")
    fun getAllWeightLiftingData(): LiveData<List<WeightLiftingEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeightLiftingData(weightLiftingEntity: WeightLiftingEntity)

    @Update
    suspend fun updateWeightLiftingEntity(weightLiftingEntity: WeightLiftingEntity)

    @Query("delete from weightlifting")
    suspend fun deleteAllWeightLiftingEntity()

    @Query("delete from weightlifting where id = :id")
    suspend fun deleteWeightLiftingEntity(id: Long)

}