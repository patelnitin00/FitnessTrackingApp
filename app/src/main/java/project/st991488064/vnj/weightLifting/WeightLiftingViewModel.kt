package project.st991488064.vnj.weightLifting

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import project.st991488064.vnj.database.ActivityDatabase
import project.st991488064.vnj.database.models.WeightLiftingEntity

//ViewModel provides data to UI
//ViewModel acts as center for communication between Repository and UI
//Extends AndroidViewModel - different than regular ViewModel as it contains Application reference
class WeightLiftingViewModel(application: Application) : AndroidViewModel(application) {

    private val getAllWeightLiftingData: LiveData<List<WeightLiftingEntity>>
    private val weightLiftingRepository: WeightLiftingRepository

    //init block will be first executed when CyclingViewModel will be called
    init {

        val weightingLiftingDao = ActivityDatabase.getInstance(application).weightLiftingDao()
        weightLiftingRepository = WeightLiftingRepository(weightingLiftingDao)
        getAllWeightLiftingData = weightLiftingRepository.getAllWeightLiftingData

    }

    //function to add weightlifting data
    fun insertWeightLiftingData(weightLiftingEntity: WeightLiftingEntity) {
        //viewModelScope is part of kotlin coroutines
        //Dispatcher.IO makes this code to run in Background thread
        viewModelScope.launch(Dispatchers.IO) {
            weightLiftingRepository.insertWeightLiftingData(weightLiftingEntity)
        }
    }


    //function to update data
    fun updateWeightLiftingEntity(weightLiftingEntity: WeightLiftingEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            weightLiftingRepository.updateWeightLiftingEntity(weightLiftingEntity)
        }
    }

    fun deleteWeightLiftingEntity(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            weightLiftingRepository.deleteWeightLiftingEntity(id)
        }
    }


    //function to delete all data
    fun deleteAllWeightLiftingEntity() {
        viewModelScope.launch(Dispatchers.IO) {
            weightLiftingRepository.deleteAllWeightLiftingEntity()
        }

    }


}