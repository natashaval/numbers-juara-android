package com.natashaval.numbertrivia.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.natashaval.numbertrivia.R
import com.natashaval.numbertrivia.databinding.FragmentDetailBinding
import com.natashaval.numbertrivia.model.NumberData
import com.natashaval.numbertrivia.viewmodel.NumberViewModel

class DetailFragment : Fragment() {

  private var _binding: FragmentDetailBinding? = null
  private val binding get() = _binding!!

  private val viewModel: NumberViewModel by activityViewModels()

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View {
    _binding = FragmentDetailBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewModel.trivia.observe(viewLifecycleOwner) { numberData ->
      bind(numberData)
      copyToClipboard(numberData.getTrivia())
      composeEmail(numberData.getTrivia())
    }
  }

  private fun bind(numberData: NumberData) {
    binding.lNumber.apply {
      btNumber.isClickable = false
      btNumber.text = numberData.number
      tvDesc.text = numberData.description
      setImageFavorite(numberData.isFavorite)
    }
  }

  private fun copyToClipboard(trivia: String) {
    binding.btCopy.setOnClickListener {
      val clipboard = activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
      val clip: ClipData = ClipData.newPlainText("number trivia", trivia)
      clipboard.setPrimaryClip(clip)
      Toast.makeText(requireContext(), "Trivia copied!", Toast.LENGTH_SHORT).show()
    }
  }

  private fun composeEmail(trivia: String) {
    binding.btEmail.setOnClickListener {
      val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, "Number Trivia Today!")
        putExtra(Intent.EXTRA_TEXT, trivia)
      }
      if (activity?.packageManager?.resolveActivity(intent, 0) != null) {
        startActivity(intent)
      }
    }
  }

  private fun setImageFavorite(isFavorite: Boolean) {
    binding.lNumber.ivFavorite.apply {
      if (isFavorite) setImageResource(R.drawable.ic_favorite_filled)
      else setImageResource(R.drawable.ic_favorite_outlined)
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}