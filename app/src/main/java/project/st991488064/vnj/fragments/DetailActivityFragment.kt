package project.st991488064.vnj.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import project.st991488064.vnj.R
import project.st991488064.vnj.cycling.CyclingViewModel
import project.st991488064.vnj.databinding.FragmentDetailActivityBinding
import project.st991488064.vnj.running.RunningViewModel
import project.st991488064.vnj.weightLifting.WeightLiftingViewModel
import java.util.*

class DetailActivityFragment : Fragment() {

    private val cyclingModel: CyclingViewModel by viewModels()
    private val runningModel: RunningViewModel by viewModels()
    private val weightLiftingModel: WeightLiftingViewModel by viewModels()

    private val args by navArgs<DetailActivityFragmentArgs>()

    var actID: Long = 0L
    var actName: String = ""

    @SuppressLint("DefaultLocale", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentDetailActivityBinding>(
            inflater,
            R.layout.fragment_detail_activity,
            container,
            false
        )

        actName = args.currentActivityEntity.activityName
        actID = args.currentActivityEntity.id
        val distanceString = args.currentActivityEntity.distance + " km"
        val setsString = args.currentActivityEntity.distance + " sets"
        binding.ActivityType.text = args.currentActivityEntity.activityName
        binding.date.text = args.currentActivityEntity.activityDate
        binding.startTime.text = args.currentActivityEntity.startTime
        binding.endTime.text = args.currentActivityEntity.endTime
        //binding.distance.setText(args.currentActivityEntity.distance)
        binding.duration.text = args.currentActivityEntity.duration
        binding.time.text = args.currentActivityEntity.activityAddTime

        when {
            actName.toLowerCase() == "cycling" -> {
                binding.distanceText.text = "Distance"
                binding.distance.text = distanceString
                binding.imageView.setImageResource(R.drawable.ic_baseline_directions_bike_24)
            }
            actName.toLowerCase() == "running" -> {
                binding.distanceText.text = "Distance"
                binding.distance.text = distanceString
                binding.imageView.setImageResource(R.drawable.ic_baseline_directions_run_24)
            }
            actName.toLowerCase() == "weight lifting" -> {
                binding.distanceText.text = "Total Sets"
                binding.distance.text = setsString
                binding.imageView.setImageResource(R.drawable.ic_baseline_fitness_center_24)
            }
            else -> {
                binding.imageView.setImageResource(R.drawable.ic_baseline_error_outline_24)
            }
        }

        binding.editButton.setOnClickListener {
            val action =
                DetailActivityFragmentDirections.actionDetailActivityFragmentToEditActivityFragment(
                    args.currentActivityEntity
                )
            findNavController().navigate(action)
        }

        binding.deleteButton.setOnClickListener {
            deleteActivity()
        }

        return binding.root
    }


    private fun deleteActivity() {

        MaterialAlertDialogBuilder(requireContext())
            .setMessage("Delete ${args.currentActivityEntity.activityName} activity?")
            .setNegativeButton(resources.getString(R.string.cancel)) { _, _ ->
                // Respond to negative button press
            }
            .setPositiveButton(resources.getString(R.string.delete)) { _, _ ->
                // Respond to positive button press
                when {
                    actName.toLowerCase(Locale.ROOT) == "cycling" -> {

                        cyclingModel.deleteCyclingEntity(actID)
                        Snackbar.make(
                            requireView(),
                            "Cycling activity deleted",
                            Snackbar.LENGTH_SHORT
                        )
                        findNavController().navigate(R.id.action_detailActivityFragment_to_titleFragment)

                    }
                    actName.toLowerCase(Locale.ROOT) == "running" -> {

                        runningModel.deleteRunningEntity(actID)
                        Snackbar.make(
                            requireView(),
                            "Running activity deleted",
                            Snackbar.LENGTH_SHORT
                        )
                        findNavController().navigate(R.id.action_detailActivityFragment_to_titleFragment)

                    }
                    actName.toLowerCase(Locale.ROOT) == "weight lifting" -> {

                        weightLiftingModel.deleteWeightLiftingEntity(actID)
                        Snackbar.make(
                            requireView(),
                            "Weight Lifting Activity Deleted",
                            Snackbar.LENGTH_SHORT
                        )
                        findNavController().navigate(R.id.action_detailActivityFragment_to_titleFragment)

                    }
                    else -> {
                        Snackbar.make(
                            requireView(),
                            "Something Went Wrong!!",
                            Snackbar.LENGTH_SHORT
                        )
                    }
                }

            }
            .show()

    }


}

