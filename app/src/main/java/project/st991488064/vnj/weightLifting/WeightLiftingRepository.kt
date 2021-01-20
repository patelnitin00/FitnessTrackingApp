package project.st991488064.vnj.weightLifting

import androidx.lifecycle.LiveData
import project.st991488064.vnj.database.dao.WeightLiftingDao
import project.st991488064.vnj.database.models.WeightLiftingEntity

class WeightLiftingRepository(private val weightLiftingDao: WeightLiftingDao) {

    val getAllWeightLiftingData: LiveData<List<WeightLiftingEntity>> =
        weightLiftingDao.getAllWeightLiftingData()

    //suspend keyword because we are going to use coroutines in ViewModel
    fun insertWeightLiftingData(weightLiftingEntity: WeightLiftingEntity) {
        weightLiftingDao.insertWeightLiftingData(weightLiftingEntity)
    }

    suspend fun updateWeightLiftingEntity(weightLiftingEntity: WeightLiftingEntity) {
        weightLiftingDao.updateWeightLiftingEntity(weightLiftingEntity)
    }

    suspend fun deleteWeightLiftingEntity(id: Long) {
        weightLiftingDao.deleteWeightLiftingEntity(id)
    }

    suspend fun deleteAllWeightLiftingEntity() {
        weightLiftingDao.deleteAllWeightLiftingEntity()
    }

}