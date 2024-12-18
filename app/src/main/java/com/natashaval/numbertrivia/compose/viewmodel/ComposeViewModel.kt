package com.natashaval.numbertrivia.compose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natashaval.numbertrivia.compose.model.Trivia
import com.natashaval.numbertrivia.model.NumberData
import com.natashaval.numbertrivia.repository.NumberRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

//https://developer.android.com/codelabs/basic-android-kotlin-compose-viewmodel-and-state#4

@HiltViewModel
class ComposeViewModel @Inject constructor(
    private val repository: NumberRepository
) : ViewModel() {
    val initialTrivia = Trivia(
        number = "42",
        description = "is the answer to the Ultimate Question of Life, the Universe, and Everything."
    )
    private val _uiState = MutableStateFlow(initialTrivia)
    val uiState: StateFlow<Trivia> = _uiState.asStateFlow()

    private val _detailUiState = MutableStateFlow(initialTrivia)
    val detailUiState = _detailUiState.asStateFlow()

    init {
        getNumberApi(number = "random", type = "trivia")
    }

    fun getNumberApi(number: String, type: String?) {
        viewModelScope.launch {
            val numberInput = number.ifEmpty { "random" }
            val result = repository.getNumberApi(numberInput, type)
            val (num, desc) = result.separateNumber()
            _uiState.update {
                Trivia(number = num, description = desc)
            }
        }
    }

    fun getNumberData(number: String) {
        viewModelScope.launch {
            repository.getNumberData(number.toLong()).collectLatest { numberData ->
                _detailUiState.update {
                    numberData.toTrivia()
                }
            }
        }
    }

    fun insertOrUpdate(trivia: Trivia, isFavorite: Boolean) {
        val numberData = trivia.toNumberData(isFavorite)
        viewModelScope.launch {
            numberData?.let { data ->
                repository.insertOrUpdate(data)
                _uiState.update { // to handle UI state if Favorite from Number or Number -> Detail
                    data.toTrivia()
                }
            }
        }
    }

    private fun Trivia.toNumberData(isFavorite: Boolean): NumberData? {
        try {
            return NumberData(
                id = this.id,
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
        id = this.id,
        number = this.number.toString(),
        description = this.description,
        isFavorite = this.isFavorite
    )

    private fun String.separateNumber(): Pair<String, String> {
        val splitString = this@separateNumber.split(" ", limit = 2)
        return Pair(splitString[0], splitString[1])
    }
}