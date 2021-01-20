package project.st991488064.vnj.fragments

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import project.st991488064.vnj.R
import project.st991488064.vnj.databinding.FragmentSplashScreenBinding


class SplashScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentSplashScreenBinding>(
            inflater,
            R.layout.fragment_splash_screen,
            container,
            false
        )
        activity?.actionBar?.hide()

//        Handler().postDelayed({
//            findNavController().navigate(R.id.action_splashScreenFragment_to_titleFragment)
//        }, 3000)

        return binding.root
    }

}