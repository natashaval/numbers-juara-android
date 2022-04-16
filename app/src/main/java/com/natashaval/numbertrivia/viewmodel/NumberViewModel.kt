package com.natashaval.numbertrivia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natashaval.numbertrivia.repository.NumberRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NumberViewModel @Inject constructor(
  private val repository: NumberRepository
) : ViewModel() {
  private val _trivia = MutableLiveData<String>()
  val trivia: LiveData<String> = _trivia

  init {
    getNumber(number = "random", type = "trivia")
  }

  fun getNumber(number: String?, type: String?) {
    viewModelScope.launch {
      val result = repository.getNumber(number, type)
      _trivia.postValue(result)
    }
  }
}

fun String.separateNumber(): Pair<String, String> {
  val splitString = this.split(" ", limit = 2)
  return Pair(splitString[0], splitString[1])
}