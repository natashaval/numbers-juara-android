package com.natashaval.numbertrivia.compose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natashaval.numbertrivia.compose.model.Trivia
import com.natashaval.numbertrivia.repository.NumberRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: NumberRepository
): ViewModel() {

    val favoriteTriviaList: StateFlow<List<Trivia>> = repository.getAllNumbers()
        .map { numberDataList ->
            numberDataList.map { numberData ->
                Trivia(
                    number = numberData.number.toString(),
                    description = numberData.description,
                    isFavorite = numberData.isFavorite
                )
            }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

}