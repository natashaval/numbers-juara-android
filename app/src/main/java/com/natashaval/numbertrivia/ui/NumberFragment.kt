package com.natashaval.numbertrivia.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.natashaval.numbertrivia.R
import com.natashaval.numbertrivia.databinding.FragmentNumberBinding
import com.natashaval.numbertrivia.viewmodel.NumberViewModel

class NumberFragment : Fragment() {

  private var _binding: FragmentNumberBinding? = null
  private val binding get() = _binding!!
  private val viewModel: NumberViewModel by viewModels()

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View {
    _binding = FragmentNumberBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}