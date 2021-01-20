package project.st991488064.vnj.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import project.st991488064.vnj.R
import project.st991488064.vnj.databinding.FragmentAboutUsBinding

class AboutUsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentAboutUsBinding>(
            inflater, R.layout.fragment_about_us, container, false
        )

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.title))
            .setMessage(resources.getString(R.string.supporting_text))
            .setPositiveButton(resources.getString(R.string.done)) { _, _ ->
                findNavController().navigate(R.id.action_aboutUsFragment_to_titleFragment)
            }
            .show()


        return binding.root
    }

}