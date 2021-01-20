package project.st991488064.vnj.running

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import project.st991488064.vnj.database.ActivityDatabase
import project.st991488064.vnj.database.models.RunningEntity

//ViewModel provides data to UI
//ViewModel acts as center for communication between Repository and UI
//Extends AndroidViewModel - different than regular ViewModel as it contains Application reference
class RunningViewModel(application: Application) : AndroidViewModel(application) {

    private val getAllRunningData: LiveData<List<RunningEntity>>
    private val runningRepository: RunningRepository

    //init block will be first executed when CyclingViewModel will be called
    init {

        val runningDao = ActivityDatabase.getInstance(application).runningDao()
        runningRepository = RunningRepository(runningDao)
        getAllRunningData = runningRepository.getAllRunningData
    }

    //function to add running data
    fun insertRunningData(runningEntity: RunningEntity) {
        //viewModelScope is part of kotlin coroutines
        //Dispatcher.IO makes this code to run in Background thread
        viewModelScope.launch(Dispatchers.IO) {
            runningRepository.insertRunningData(runningEntity)
        }
    }

    //function to update data
    fun updateRunningEntity(runningEntity: RunningEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            runningRepository.updateRunningEntity(runningEntity)
        }
    }

    fun deleteRunningEntity(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            runningRepository.deleteRunningEntity(id)
        }
    }

    //function to delete all data
    fun deleteAllRunningEntity() {
        viewModelScope.launch(Dispatchers.IO) {
            runningRepository.deleteAllRunningEntity()
        }
    }


}