package project.st991488064.vnj.fragments

import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.content.SharedPreferences
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.ItemAnimator
import com.google.android.material.snackbar.Snackbar
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import project.st991488064.vnj.adapter.CurrentListAdapter
import project.st991488064.vnj.R
import project.st991488064.vnj.cycling.CyclingViewModel
import project.st991488064.vnj.databinding.FragmentTitleBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


class TitleFragment : Fragment(), SensorEventListener {

    private var sensorManager: SensorManager? = null
    private var running = false
    private var totalSteps = 0f
    private var previousTotalSteps = 0f

    private val cyclingModel: CyclingViewModel by viewModels()

//    @RequiresApi(Build.VERSION_CODES.O)
//    val currentDateTime = LocalDateTime.now()
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    val curDate = currentDateTime.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentTitleBinding>(
            inflater, R.layout.fragment_title, container, false
        )


        sensorManager =
            (activity as AppCompatActivity).getSystemService(SENSOR_SERVICE) as SensorManager

        loadData()

        binding.tvStepsTaken.setOnClickListener {
            previousTotalSteps = totalSteps
            binding.tvStepsTaken.text = 0.toString()
            saveData()
            Snackbar.make(requireView(), "Long tap to reset steps", Snackbar.LENGTH_SHORT)
                .show()
        }

        binding.floatingActionButton.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_titleFragment_to_addActivityFragment)
        }

        //recycler view
        val adapter = CurrentListAdapter()
        val recyclerView = binding.recycleView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val itemAnimator: ItemAnimator = DefaultItemAnimator()
        itemAnimator.addDuration = 1000
        itemAnimator.removeDuration = 1000
        recyclerView.itemAnimator = itemAnimator

        //userViewModel
//        cyclingModel.getAll.observe(viewLifecycleOwner, Observer { cycle ->
//            adapter.setData(cycle)
//        })

        cyclingModel.getAllForToday.observe(viewLifecycleOwner, Observer { cycle ->
            adapter.setData(cycle)
        })




        //adapter.setData(cyclingModel.getAllForToday("Dec 20, 2020"))




        //Dark Theme
        binding.themeImage.setOnClickListener { chooseThemeDialog() }

        //SetOptionsMenu
        setHasOptionsMenu(true)

        //Get BMI Index
        val preference: SharedPreferences = requireActivity().getSharedPreferences(
            "STATE",
            Context.MODE_PRIVATE
        )

        val data: String? = preference.getString("BMI", null)
        binding.TVBMI.text = data


        return binding.root
    }

    private fun chooseThemeDialog() {

        val builder = AlertDialog.Builder(this.requireContext())
        val styles = arrayOf("Light", "Dark", "System default")
        val checkedItem = 0

        builder.setSingleChoiceItems(styles, checkedItem) { dialog, which ->
            when (which) {
                0 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    (activity as AppCompatActivity).delegate.applyDayNight()
                    dialog.dismiss()
                }
                1 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    (activity as AppCompatActivity).delegate.applyDayNight()
                    dialog.dismiss()
                }
                2 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    (activity as AppCompatActivity).delegate.applyDayNight()
                    dialog.dismiss()
                }

            }

        }

        val dialog = builder.create()
        dialog.show()
    }

    override fun onResume() {
        super.onResume()
        running = true
        val stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if (stepSensor == null) {

        } else {
            sensorManager?.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (running) {
            totalSteps = event!!.values[0]
            val currentSteps = totalSteps.toInt() - previousTotalSteps.toInt()
            val tv = requireView().findViewById<TextView>(R.id.tv_stepsTaken)
            tv!!.text = ("$currentSteps")

            val circularProgressBar =
                requireView().findViewById<CircularProgressBar>(R.id.circularProgressBar)
            circularProgressBar.apply {
                setProgressWithAnimation(currentSteps.toFloat())
            }
        }

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    private fun saveData() {
        val sharedPreferences =
            (activity as AppCompatActivity).getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putFloat("key1", previousTotalSteps)
        editor.apply()
    }

    private fun loadData() {
        val sharedPreferences =
            (activity as AppCompatActivity).getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val savedNumber = sharedPreferences.getFloat("key1", 0f)
        previousTotalSteps = savedNumber
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(
            item,
            requireView().findNavController()
        )
                || super.onOptionsItemSelected(item)
    }




}