package com.natashaval.numbertrivia.viewmodel

import androidx.lifecycle.ViewModel
import com.natashaval.numbertrivia.repository.NumberRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NumberViewModel @Inject constructor(
  private val repository: NumberRepository
) : ViewModel() {

}