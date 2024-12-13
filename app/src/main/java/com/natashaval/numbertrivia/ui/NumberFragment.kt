package com.natashaval.numbertrivia.ui

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.natashaval.numbertrivia.R
import com.natashaval.numbertrivia.databinding.FragmentNumberBinding
import com.natashaval.numbertrivia.model.NumberData
import com.natashaval.numbertrivia.viewmodel.NumberViewModel
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
    addMenu()
  }

  private fun observeNumberTrivia() {
    viewModel.trivia.observe(viewLifecycleOwner) { numberData ->
      binding.lNumber.apply {
        btNumber.text = String.format(numberData.number.toString())
        tvDesc.text = numberData.description
        btNumber.setOnClickListener {
          val action = NumberFragmentDirections.actionNumberFragmentToDetailFragment(
            number = numberData.number)
          findNavController().navigate(action)
        }
        setImageFavorite(numberData.isFavorite)
        changeFavorite(numberData)
      }
    }
  }

  private fun generateNewTrivia() {
    with(binding.lSelection) {
      btGenerate.setOnClickListener {
        val type = cgChip.findViewById<Chip>(cgChip.checkedChipId).text.toString().lowercase()
        val number = inputNumber.text.toString()
        viewModel.getNumberApi(number, type)
      }
    }
  }

  private fun changeFavorite(numberData: NumberData) {
    binding.lNumber.ivFavorite.setOnClickListener {
      // flip favorite
      viewModel.insertOrUpdate(numberData, !numberData.isFavorite)
      setImageFavorite(!numberData.isFavorite)
    }
  }

  private fun setImageFavorite(isFavorite: Boolean) {
    binding.lNumber.ivFavorite.setImageResource(
      if (isFavorite) R.drawable.ic_favorite_filled
      else R.drawable.ic_favorite_outlined
    )
  }

  private fun addMenu() {
    requireActivity().addMenuProvider(object : MenuProvider {
      override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.layout_menu, menu)
      }

      override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId) {
          R.id.action_favorite -> {
            val action = NumberFragmentDirections.actionNumberFragmentToFavoriteFragment()
            findNavController().navigate(action)
            return true
          }
          else -> false
        }
      }

    }, viewLifecycleOwner)
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}