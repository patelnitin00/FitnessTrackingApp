package project.st991488064.vnj.running

import androidx.lifecycle.LiveData
import project.st991488064.vnj.database.dao.RunningDao
import project.st991488064.vnj.database.models.RunningEntity

//repository is a best practice for separating code from architecture
class RunningRepository(private val runningDao: RunningDao) {

    val getAllRunningData: LiveData<List<RunningEntity>> = runningDao.getAllRunningData()

    //suspend keyword because we are going to use coroutines in ViewModel
    fun insertRunningData(runningEntity: RunningEntity) {
        runningDao.insertRunningData(runningEntity)
    }

    suspend fun updateRunningEntity(runningEntity: RunningEntity) {
        runningDao.updateRunningEntity(runningEntity)
    }

    suspend fun deleteRunningEntity(id: Long) {
        runningDao.deleteRunningEntity(id)
    }

    suspend fun deleteAllRunningEntity() {
        runningDao.deleteAllRunningEntity()
    }
}