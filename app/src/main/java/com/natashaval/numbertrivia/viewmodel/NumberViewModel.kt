package com.natashaval.numbertrivia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natashaval.numbertrivia.model.NumberData
import com.natashaval.numbertrivia.model.Trivia
import com.natashaval.numbertrivia.repository.NumberRepository
import com.natashaval.numbertrivia.ui.NumberFragment.Companion.ADD_TO_FAVORITE_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NumberViewModel @Inject constructor(
  private val repository: NumberRepository
) : ViewModel() {
  private val _trivia = MutableLiveData<String>()
  val trivia: LiveData<String> = _trivia

  // status if insert / delete finished
  private val _status = MutableLiveData<String>()
  val status: LiveData<String> = _status

  init {
    getNumberApi(number = "random", type = "trivia")
  }

  fun getNumberApi(number: String, type: String?) {
    viewModelScope.launch {
      val checkNumber = number.ifBlank { "random" }
      val result = repository.getNumberApi(checkNumber, type)
      _trivia.postValue(result)
    }
  }

  fun insertNumberData(number: String) {
    val numberData = NumberData(number = number, isFavorite = true)
    viewModelScope.launch {
      repository.insertNumberData(numberData)
      setStatus(ADD_TO_FAVORITE_KEY)
    }
  }

  fun setStatus(status: String) {
    _status.value = status
  }
}

fun String.separateNumber(): Pair<String, String> {
  val splitString = this.split(" ", limit = 2)
  return Pair(splitString[0], splitString[1])
}