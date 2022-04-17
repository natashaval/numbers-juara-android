package com.natashaval.numbertrivia.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.natashaval.numbertrivia.R
import com.natashaval.numbertrivia.adapter.FavoriteAdapter
import com.natashaval.numbertrivia.databinding.FragmentFavoriteBinding
import com.natashaval.numbertrivia.viewmodel.NumberViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

  private var _binding: FragmentFavoriteBinding? = null
  private val binding get() = _binding!!

  private val viewModel: NumberViewModel by activityViewModels()

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View {
    _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    val favoriteAdapter = FavoriteAdapter {
      Toast.makeText(requireContext(), "clicked ${it.number}", Toast.LENGTH_SHORT).show()
    }
    binding.rvFavorite.apply {
      adapter = favoriteAdapter
    }

    viewModel.getAllNumbers().observe(viewLifecycleOwner) { dataList ->
      dataList?.let {
        favoriteAdapter.submitList(it)
      }
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}