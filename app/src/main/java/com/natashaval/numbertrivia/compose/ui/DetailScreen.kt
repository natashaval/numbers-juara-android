package com.natashaval.numbertrivia.compose.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.natashaval.numbertrivia.R
import com.natashaval.numbertrivia.compose.TriviaScreen
import com.natashaval.numbertrivia.compose.ui.theme.NumberTriviaTheme
import com.natashaval.numbertrivia.compose.viewmodel.ComposeViewModel


@Composable
fun CustomOutlinedIconButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    @StringRes stringRes: Int,
    onClick: () -> Unit
) {
    OutlinedButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Icon(imageVector = icon, contentDescription = stringResource(stringRes))
        Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
        Text(text = stringResource(stringRes))
    }
}

@Composable
fun CustomIconButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    @StringRes stringRes: Int,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = onClick,
    ) {
        Icon(imageVector = icon, contentDescription = stringResource(stringRes))
        Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
        Text(text = stringResource(stringRes))
    }
}

@Composable
fun DetailCopySendButton(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        CustomOutlinedIconButton(
            modifier = Modifier,
            icon = Icons.Default.ContentCopy,
            stringRes = R.string.copy,
            onClick = {}
        )
        CustomIconButton(
            modifier = Modifier,
            icon = Icons.Default.MailOutline,
            stringRes = R.string.email,
            onClick = {}
        )
    }
}

@Preview
@Composable
fun DetailCopySendPreview() {
    NumberTriviaTheme {
        DetailCopySendButton()
    }
}

@Composable
fun DetailScreen(
    numberId: String,
    previousScreen: String,
    viewModel: ComposeViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    // differentiate detail and favorite
    val triviaUiState by viewModel.uiState.collectAsState()
    val detailUiState by viewModel.detailUiState.collectAsState()
    if (previousScreen == TriviaScreen.Favorite.name) { // if numberId from FavoriteScreen different from the number from NumberScreen
        viewModel.getNumberData(numberId)
    }
    val detailTrivia = when (previousScreen) {
        TriviaScreen.Favorite.name -> {
            detailUiState
        }

        TriviaScreen.Number.name -> {
            triviaUiState
        }

        else -> {
            viewModel.initialTrivia
        }
    }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        NumberDescLayout(
            trivia = detailTrivia,
            onNumberDetailClicked = {},
            onFavoriteIconClicked = { isFavorite ->
                // Update the trivia with the new favorite status
                val updatedTrivia = detailTrivia.copy(isFavorite = isFavorite)
                viewModel.insertOrUpdate(updatedTrivia, isFavorite, previousScreen)
            },
        )
        DetailCopySendButton()
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    NumberTriviaTheme {
        DetailScreen(
            numberId = "42",
            previousScreen = TriviaScreen.Number.name,
            modifier = Modifier.fillMaxHeight()
        )
    }
}