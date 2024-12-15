package com.natashaval.numbertrivia.compose.model

// to show as Trivia Detail
data class Trivia(
  val number: String,
  val description: String,
  val isFavorite: Boolean = false
)