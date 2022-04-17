package com.natashaval.numbertrivia.viewmodel

import androidx.lifecycle.*
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
  private val _trivia = MutableLiveData<NumberData>()
  val trivia: LiveData<NumberData> = _trivia

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
      val (num, desc) = result.separateNumber()
      val numberData = repository.getNumberDataFromTrivia(num, desc)
      numberData?.let {
        _trivia.postValue(it)
      } ?: run {
        _trivia.postValue(NumberData(number = num, description = desc, isFavorite = false))
      }
    }
  }

  fun insertNumberData(number: String, description: String) {
    val numberData = NumberData(
      number = number,
      description = description,
      isFavorite = true)
    viewModelScope.launch {
      repository.insertNumberData(numberData)
      setStatus(ADD_TO_FAVORITE_KEY)
    }
  }

  fun setStatus(status: String) {
    _status.value = status
  }

  fun getAllNumbers(): LiveData<List<NumberData>> {
    return repository.getAllNumbers().asLiveData()
  }
}

fun String.separateNumber(): Pair<String, String> {
  val splitString = this.split(" ", limit = 2)
  return Pair(splitString[0], splitString[1])
}