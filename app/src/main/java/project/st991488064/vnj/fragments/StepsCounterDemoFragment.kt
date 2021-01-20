package project.st991488064.vnj.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import project.st991488064.vnj.R
import project.st991488064.vnj.databinding.FragmentStepsCounterDemoBinding

class StepsCounterDemoFragment : Fragment() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentStepsCounterDemoBinding>(
            inflater, R.layout.fragment_steps_counter_demo, container, false
        )

        binding.webView.webViewClient = WebViewClient()
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.userAgentString = "Chrome/56.0.0.0 Mobile"
        binding.webView.loadUrl("https://www.youtube.com/watch?v=TtQsfIWRk9E&feature=youtu.be")

        return binding.root
    }

}