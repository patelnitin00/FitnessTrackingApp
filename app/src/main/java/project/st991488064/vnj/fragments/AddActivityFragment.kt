package project.st991488064.vnj.fragments

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_add_activity.*
import project.st991488064.vnj.R
import project.st991488064.vnj.cycling.CyclingViewModel
import project.st991488064.vnj.database.models.CyclingEntity
import project.st991488064.vnj.database.models.RunningEntity
import project.st991488064.vnj.database.models.WeightLiftingEntity
import project.st991488064.vnj.databinding.FragmentAddActivityBinding
import project.st991488064.vnj.running.RunningViewModel
import project.st991488064.vnj.weightLifting.WeightLiftingViewModel
import java.text.SimpleDateFormat
import java.util.*


class AddActivityFragment : Fragment() {

    private val cyclingModel: CyclingViewModel by viewModels()
    private val runningModel: RunningViewModel by viewModels()
    private val weightLiftingModel: WeightLiftingViewModel by viewModels()

    private val activities = arrayOf("Cycling", "Running", "WeightLifting")

    var dropDownText: String? = null

    var startHour: Int = 0
    var startMinute: Int = 0
    var startTimeForCal: Long = 0L

    var endHour: Int = 0
    var endMinute: Int = 0
    var endTimeForCal: Long = 0L


    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentAddActivityBinding>(
            inflater,
            R.layout.fragment_add_activity,
            container,
            false
        )


        // Activity Dropdown List
        val adapter = ArrayAdapter(
            this.requireContext(), R.layout.support_simple_spinner_dropdown_item, activities
        )

        binding.filledExposedDropdown.setAdapter(adapter)

        binding.filledExposedDropdown.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, p2, _ ->

                dropDownText = activities[p2]

                when {
                    activities[p2] == activities[0] -> {
                        binding.sets.visibility = GONE
                        binding.distance.visibility = VISIBLE
                    }
                    activities[p2] == activities[1] -> {
                        binding.sets.visibility = GONE
                        binding.distance.visibility = VISIBLE
                    }
                    else -> {
                        binding.sets.visibility = VISIBLE
                        binding.distance.visibility = GONE
                    }
                }
            }


        //Material DatePicker
        val builder: MaterialDatePicker.Builder<*> =
            MaterialDatePicker.Builder.datePicker()
        builder.setTitleText("Select Activity Date")
        val picker: MaterialDatePicker<*> = builder.build()

        // Birthday Selection
        binding.filledExposedDropdownDate.setOnClickListener {
            picker.show(parentFragmentManager, "DATE_PICKER")
        }

        picker.addOnPositiveButtonClickListener {
            binding.filledExposedDropdownDate.setText(picker.headerText)
        }


        // Start Time Picker
        binding.filledExposedDropdownStartTime.setOnClickListener {
            val cal: Calendar = Calendar.getInstance()
            val timePickerDialog = TimePickerDialog.OnTimeSetListener { _, hour, minute ->

                startHour = hour * 60
                startMinute = minute

                startTimeForCal = (startHour + startMinute).toLong()

                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                val format: String = SimpleDateFormat("HH:mm a").format(cal.time)
                binding.filledExposedDropdownStartTime.setText(format)
            }
            TimePickerDialog(
                requireContext(),
                timePickerDialog,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }

        //End Time Picker
        binding.filledExposedDropdownEndTime.setOnClickListener {
            val cal: Calendar = Calendar.getInstance()
            val timePickerDialog = TimePickerDialog.OnTimeSetListener { _, hour, minute ->

                endHour = hour * 60
                endMinute = minute

                endTimeForCal = (endHour + endMinute).toLong()


                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                val format: String = SimpleDateFormat("HH:mm a").format(cal.time)
                binding.filledExposedDropdownEndTime.setText(format)
            }
            TimePickerDialog(
                requireContext(),
                timePickerDialog,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }

        binding.saveButton.setOnClickListener {
            insertDataToDatabase()
        }

        return binding.root
    }


    //database stuff
    @SuppressLint("SimpleDateFormat")
    private fun insertDataToDatabase() {

        val distance = editDistance.text.toString()
        Log.i("Distance is: ", distance)

        val sets = editSets.text.toString()
        Log.i("Sets", sets)

        val startDate = filled_exposed_dropdown_date.text.toString()
        Log.i("Date is: ", startDate)

        val startTime = filled_exposed_dropdown_startTime.text.toString()
        Log.i("Start Time is: ", startTime)
        val endTime = filled_exposed_dropdown_endTime.text.toString()
        Log.i("End time is: ", endTime)

        val timeDifference = calcDifference(startTimeForCal, endTimeForCal)
        Log.i("Difference time is: ", timeDifference)

        val currentTime = System.currentTimeMillis();

        val dateFormat = SimpleDateFormat("HH:mm a")

        val activityAddedTime = dateFormat.format(Date(currentTime))
        Log.i("Current Time", activityAddedTime)



        if (inputCheck(startDate, startTime, timeDifference)) {

            if (dropDownText == activities[2]) {
                if (setsCheck(sets)) {

                    //Create Sets Object
                    val weightLiftingEntity = WeightLiftingEntity(
                        0,
                        "Weight Lifting",
                        sets,
                        startDate,
                        startTime,
                        endTime,
                        timeDifference,
                        activityAddedTime
                    )


                    //Add to database
                    weightLiftingModel.insertWeightLiftingData(weightLiftingEntity)

                    Snackbar.make(
                        requireView(),
                        "Successfully Added Weight Lifting Activity",
                        Snackbar.LENGTH_SHORT
                    )
                        .show()
                    findNavController().navigate(R.id.action_addActivityFragment_to_titleFragment)
                } else {
                    Snackbar.make(requireView(), "Please fill SETS field", Snackbar.LENGTH_SHORT)
                        .show()
                }
            } else {

                if (distanceCheck(distance)) {
                    if (dropDownText == activities[0]) {

                        //Create Cycling Object
                        val cyclingEntity = CyclingEntity(
                            0,
                            "Cycling",
                            distance,
                            startDate,
                            startTime,
                            endTime,
                            timeDifference,
                            activityAddedTime
                        )


                        //Add to database
                        cyclingModel.insertCyclingData(cyclingEntity)

                        Snackbar.make(
                            requireView(),
                            "Successfully Added Cycling Activity",
                            Snackbar.LENGTH_SHORT
                        )
                            .show()
                        findNavController().navigate(R.id.action_addActivityFragment_to_titleFragment)
                    } else {
                        //create Running Object
                        val runningEntity = RunningEntity(
                            0,
                            "Running",
                            distance,
                            startDate,
                            startTime,
                            endTime,
                            timeDifference,
                            activityAddedTime
                        )

                        //add to database
                        runningModel.insertRunningData(runningEntity)
                        Snackbar.make(
                            requireView(),
                            "Successfully Added Running Activity",
                            Snackbar.LENGTH_SHORT
                        )
                            .show()


                        findNavController().navigate(R.id.action_addActivityFragment_to_titleFragment)

                    }

                } else {
                    Snackbar.make(
                        requireView(),
                        "Please fill DISTANCE field",
                        Snackbar.LENGTH_SHORT
                    )
                        .show()
                }
            }
        } else {
            Snackbar.make(requireView(), "Please fill all fields", Snackbar.LENGTH_SHORT)
                .show()
        }


    }

    private fun inputCheck(startDate: String, startTime: String, endTime: String): Boolean {
        return !(TextUtils.isEmpty(startDate) && TextUtils.isEmpty(startTime) && TextUtils.isEmpty(
            endTime
        ))

    }

    private fun distanceCheck(distance: String): Boolean {
        return !(TextUtils.isEmpty(distance))
    }

    private fun setsCheck(weight: String): Boolean {
        return !(TextUtils.isEmpty(weight))
    }
    //database stuff ends here


    //fun to calculate time duration
    private fun calcDifference(startTimeToCalc: Long, endTimeToCalc: Long): String {

        var diffString: String = ""

        var diff: Long = 0L
        var hour: Long = 0L
        var minute: Long = 0L

        diff = if (endTimeToCalc > startTimeToCalc) {
            (endTimeForCal - startTimeForCal)

        } else {
            (((endTimeToCalc + 720) + 720) - startTimeToCalc)
        }

        hour = diff / 60
        minute = diff % 60

        diffString = hour.toString() + "h " + minute.toString() + "m"

        return diffString
    }


}
