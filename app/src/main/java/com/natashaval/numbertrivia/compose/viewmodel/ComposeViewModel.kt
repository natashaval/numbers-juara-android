package com.natashaval.numbertrivia.compose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natashaval.numbertrivia.compose.model.Trivia
import com.natashaval.numbertrivia.repository.NumberRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
            val result = repository.getNumberApi(number, type)
            val (num, desc) = result.separateNumber()
            _uiState.update {
                Trivia(num, desc)
            }
        }
    }

    private fun String.separateNumber(): Pair<String, String> {
        val splitString = this@separateNumber.split(" ", limit = 2)
        return Pair(splitString[0], splitString[1])
    }
}