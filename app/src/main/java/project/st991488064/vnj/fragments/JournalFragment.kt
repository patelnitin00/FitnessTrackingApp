package project.st991488064.vnj.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import project.st991488064.vnj.R
import project.st991488064.vnj.adapter.JournalListAdapter
import project.st991488064.vnj.cycling.CyclingViewModel
import project.st991488064.vnj.databinding.FragmentJournalBinding
import project.st991488064.vnj.running.RunningViewModel
import project.st991488064.vnj.weightLifting.WeightLiftingViewModel

class JournalFragment : Fragment() {

    private val cyclingModel: CyclingViewModel by viewModels()
    private val runningModel: RunningViewModel by viewModels()
    private val weightLiftingModel: WeightLiftingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentJournalBinding>(
            inflater, R.layout.fragment_journal, container, false
        )


        val adapter = JournalListAdapter()
        val recyclerView = binding.recycleView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        cyclingModel.getAll.observe(viewLifecycleOwner, Observer { cycle ->
            adapter.setData(cycle)
        })

        //SetOptionsMenu
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu_journal, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.deleteAll -> deleteAllActivities()
        }
        return NavigationUI.onNavDestinationSelected(
            item,
            requireView().findNavController()
        )
                || super.onOptionsItemSelected(item)
    }

    private fun deleteAllActivities() {

        MaterialAlertDialogBuilder(requireContext())
            .setMessage("Are you sure you want to delete all Activities ? ")
            .setNegativeButton(resources.getString(R.string.cancel)) { _, _ ->
                // Respond to negative button press
            }
            .setPositiveButton(resources.getString(R.string.delete)) { _, _ ->
                cyclingModel.deleteAllCyclingEntity()
                runningModel.deleteAllRunningEntity()
                weightLiftingModel.deleteAllWeightLiftingEntity()

                Snackbar.make(
                    requireView(),
                    "All Activities has been deleted..",
                    Snackbar.LENGTH_LONG
                )
                findNavController().navigate(R.id.action_journalFragment_self)
            }
            .show()

    }

}