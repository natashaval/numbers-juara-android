package com.natashaval.numbertrivia.compose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natashaval.numbertrivia.compose.model.Trivia
import com.natashaval.numbertrivia.model.NumberData
import com.natashaval.numbertrivia.repository.NumberRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

//https://developer.android.com/codelabs/basic-android-kotlin-compose-viewmodel-and-state#4

@HiltViewModel
class ComposeViewModel @Inject constructor(
    private val repository: NumberRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        Trivia(
            number = "42",
            description = "is the answer to the Ultimate Question of Life, the Universe, and Everything."
        )
    )
    val uiState: StateFlow<Trivia> = _uiState.asStateFlow()

    init {
        getNumberApi(number = "random", type = "trivia")
    }

    fun getNumberApi(number: String, type: String?) {
        viewModelScope.launch {
            val  numberInput = number.ifEmpty { "random" }
            val result = repository.getNumberApi(numberInput, type)
            val (num, desc) = result.separateNumber()
            _uiState.update {
                Trivia(num, desc)
            }
        }
    }

    fun insertOrUpdate(trivia: Trivia, isFavorite: Boolean) {
        val numberData = trivia.toNumberData(isFavorite)
        viewModelScope.launch {
            numberData?.let {
                repository.insertOrUpdate(numberData)
                _uiState.update {
                    repository.getNumberData(numberData.number).first().toTrivia()
                }
            }
        }
    }

    private fun Trivia.toNumberData(isFavorite: Boolean): NumberData? {
        try {
            return NumberData(
            number = this.number.toLong(),
            description = this.description,
            isFavorite = isFavorite
            )
        } catch (e: NumberFormatException) {
            Timber.e("Failed to parse number String to Long")
            return null
        }
    }

    private fun NumberData.toTrivia() = Trivia(
        number = this.number.toString(),
        description = this.description,
        isFavorite = this.isFavorite
    )

    private fun String.separateNumber(): Pair<String, String> {
        val splitString = this@separateNumber.split(" ", limit = 2)
        return Pair(splitString[0], splitString[1])
    }
}