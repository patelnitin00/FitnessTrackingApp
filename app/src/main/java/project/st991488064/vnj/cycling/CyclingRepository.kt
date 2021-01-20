package project.st991488064.vnj.cycling

import androidx.lifecycle.LiveData
import project.st991488064.vnj.database.dao.CyclingDao
import project.st991488064.vnj.database.models.ActivitiesEntity
import project.st991488064.vnj.database.models.CyclingEntity


//repository is a best practice for separating code from architecture
class CyclingRepository(private val cyclingDao: CyclingDao) {

    val getAllCyclingData: LiveData<List<CyclingEntity>> = cyclingDao.getAllCyclingData()



    //suspend keyword because we are going to use coroutines in ViewModel
    fun insertCyclingData(cyclingEntity: CyclingEntity) {
        cyclingDao.insertCyclingData(cyclingEntity)
    }

    suspend fun updateCyclingEntity(cyclingData: CyclingEntity) {
        cyclingDao.updateCyclingEntity(cyclingData)
    }

    suspend fun deleteAllCyclingEntity() {
        cyclingDao.deleteAllCyclingEntity()
    }

    suspend fun deleteCyclingEntity(id: Long) {
        cyclingDao.deleteCyclingEntity(id)
    }

    //common data
    val getAll: LiveData<List<ActivitiesEntity>> = cyclingDao.getAll()

    //val getAllForToday: LiveData<List<ActivitiesEntity>> = cyclingDao.getAllForToday(currentDate : String)

     fun getAllForToday(currentDate : String): LiveData<List<ActivitiesEntity>>{
       return cyclingDao.getAllForToday(currentDate)
    }


}