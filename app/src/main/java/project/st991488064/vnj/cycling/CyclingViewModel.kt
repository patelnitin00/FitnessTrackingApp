package project.st991488064.vnj.cycling

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import project.st991488064.vnj.database.ActivityDatabase
import project.st991488064.vnj.database.models.ActivitiesEntity
import project.st991488064.vnj.database.models.CyclingEntity
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*


//ViewModel provides data to UI
//ViewModel acts as center for communication between Repository and UI
//Extends AndroidViewModel - different than regular ViewModel as it contains Application reference
class CyclingViewModel(application: Application) : AndroidViewModel(application) {

    private val getAllCyclingData: LiveData<List<CyclingEntity>>
    private val cyclingRepository: CyclingRepository
    val getAll: LiveData<List<ActivitiesEntity>>
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    val currentDateTime = LocalDateTime.now()
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    val curDate = currentDateTime.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))

    val currentDate: String = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date())
     //Log.i("cdate is", currentDate)

    val getAllForToday: LiveData<List<ActivitiesEntity>>


    //init block will be first executed when CyclingViewModel will be called
    init {

        val cyclingDao = ActivityDatabase.getInstance(application).cyclingDao()
        cyclingRepository = CyclingRepository(cyclingDao)
        getAllCyclingData = cyclingRepository.getAllCyclingData
        getAll = cyclingRepository.getAll
        getAllForToday = cyclingRepository.getAllForToday(currentDate)

    }


//    fun getAllForToday(currentDate : String){
//        viewModelScope.launch(Dispatchers.IO){
//            cyclingRepository.getAllForToday(currentDate)
//        }
//    }


    //function to add cycling data
    fun insertCyclingData(cyclingEntity: CyclingEntity) {
        //viewModelScope is part of kotlin coroutines
        //Dispatcher.IO makes this code to run in Background thread
        viewModelScope.launch(Dispatchers.IO) {
            cyclingRepository.insertCyclingData(cyclingEntity)
        }
    }


    //function to update data
    fun updateCyclingEntity(cyclingData: CyclingEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            cyclingRepository.updateCyclingEntity(cyclingData)
        }
    }

    fun deleteCyclingEntity(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            cyclingRepository.deleteCyclingEntity(id)
        }
    }

    //function to delete all data
    fun deleteAllCyclingEntity() {
        viewModelScope.launch(Dispatchers.IO) {
            cyclingRepository.deleteAllCyclingEntity()
        }
    }


}