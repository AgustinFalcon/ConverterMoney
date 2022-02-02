package com.example.convertermoney

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.convertermoney.databinding.ActivityMainBinding
import com.example.convertermoney.databinding.FragmentMainBinding
import com.example.convertermoney.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val viewModel : MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMainBinding.inflate(layoutInflater, container, false)



        binding.btnConvert.setOnClickListener {
            viewModel.convert(
                binding.etFrom.text.toString(),
                binding.spFromCurrency.selectedItem.toString(),
                binding.spToCurrency.selectedItem.toString()
            )
        }

        //Flow states
        CoroutineScope(Dispatchers.Main).launch{
            viewModel.conversion.collect { event ->
                when(event){
                    is MainViewModel.CurrencyEvent.Success -> {
                        binding.progressBar.isVisible =  false
                        binding.tvResult.setTextColor(Color.BLACK)
                        binding.tvResult.text = event.resultText
                    }
                    is MainViewModel.CurrencyEvent.Failure -> {
                        binding.progressBar.isVisible = false
                        binding.tvResult.setTextColor(Color.RED)
                        binding.tvResult.text = event.errorText
                    }
                    is MainViewModel.CurrencyEvent.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                    else -> Unit
                }}
        }




        return binding.root
    }


}