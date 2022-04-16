package com.natashaval.numbertrivia.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.chip.Chip
import com.natashaval.numbertrivia.R
import com.natashaval.numbertrivia.databinding.FragmentNumberBinding
import com.natashaval.numbertrivia.viewmodel.NumberViewModel
import com.natashaval.numbertrivia.viewmodel.separateNumber
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NumberFragment : Fragment() {

  private var _binding: FragmentNumberBinding? = null
  private val binding get() = _binding!!
  private val viewModel: NumberViewModel by activityViewModels()

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View {
    _binding = FragmentNumberBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    observeNumberTrivia()
    generateNewTrivia()
  }

  private fun observeNumberTrivia() {
    viewModel.trivia.observe(viewLifecycleOwner) {
      binding.lNumber.apply {
        val (number, desc) = it.separateNumber()
        btNumber.text = number
        tvDesc.text = desc
      }
    }
  }

  private fun generateNewTrivia() {
    with(binding.lSelection) {
      btGenerate.setOnClickListener {
        val type = cgChip.findViewById<Chip>(cgChip.checkedChipId).text.toString().lowercase()
        val number = inputNumber.text.toString()
        viewModel.getNumber(number, type)
      }
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}