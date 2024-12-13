package com.natashaval.numbertrivia.model

// to show as Trivia Detail
data class Trivia(
  val number: String,
  val description: String,
  val isFavorite: Boolean = false
)