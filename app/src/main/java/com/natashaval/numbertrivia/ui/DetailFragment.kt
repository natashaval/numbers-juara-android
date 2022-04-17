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
import androidx.navigation.fragment.navArgs
import com.natashaval.numbertrivia.R
import com.natashaval.numbertrivia.databinding.FragmentDetailBinding
import com.natashaval.numbertrivia.viewmodel.separateNumber

class DetailFragment : Fragment() {

  private var _binding: FragmentDetailBinding? = null
  private val binding get() = _binding!!

  private val navigationArgs: DetailFragmentArgs by navArgs()
  private val trivia by lazy { navigationArgs.trivia }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View {
    _binding = FragmentDetailBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    trivia?.let {
      bind(it)
      copyToClipboard(it)
      composeEmail(it)
    }
  }

  private fun bind(trivia: String) {
    binding.lNumber.apply {
      btNumber.isClickable = false
      ivFavorite.visibility = View.GONE
      val (number, desc) = trivia.separateNumber()
      btNumber.text = number
      tvDesc.text = desc
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

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}