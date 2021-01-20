package project.st991488064.vnj.fragments

import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import project.st991488064.vnj.R
import project.st991488064.vnj.databinding.FragmentBmiBinding


class BMIFragment : Fragment() {

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentBmiBinding>(
            inflater, R.layout.fragment_bmi, container, false
        )

        binding.calculateButton.setOnClickListener {
            val height = (binding.editHeight.text).toString().toFloat() / 100
            val weight = binding.editWeight.text.toString().toFloat()
            // This is formula to calculate BMI Index
            val result = weight / (height * height)
            val bmiFormatResult = "%.2f".format(result)
            binding.textBmiResult.text = bmiFormatResult

            val preference: SharedPreferences =
                requireActivity().getSharedPreferences("STATE", MODE_PRIVATE)
            preference.edit().putString("BMI", bmiFormatResult).apply()
        }

        return binding.root
    }


}