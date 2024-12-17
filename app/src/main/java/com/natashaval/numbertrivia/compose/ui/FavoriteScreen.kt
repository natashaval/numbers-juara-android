package com.natashaval.numbertrivia.compose.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.natashaval.numbertrivia.R
import com.natashaval.numbertrivia.compose.ui.theme.NumberTriviaTheme
import com.natashaval.numbertrivia.compose.model.Trivia
import com.natashaval.numbertrivia.compose.viewmodel.ComposeViewModel
import com.natashaval.numbertrivia.compose.viewmodel.FavoriteViewModel

@Composable
fun FavoriteItem(
    trivia: Trivia,
    onNumberDetailClicked: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextButton(
            onClick = {
                onNumberDetailClicked(trivia.number)
            },
        ) {
            Text(
                modifier = Modifier,
                text = trivia.number,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.SemiBold,
            )
        }
        Text(
            modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_small)),
            text = trivia.description,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FavoriteItemPreview() {
    val trivia = Trivia(
        number = stringResource(R.string.tools_number),
        description = stringResource(R.string.tools_desc)
    )
    NumberTriviaTheme {
        FavoriteItem(trivia)
    }
}

@Composable
fun FavoriteColumnList(
    triviaList: List<Trivia>,
    onNumberDetailClicked: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(2.dp),
        contentPadding = PaddingValues(dimensionResource(R.dimen.padding_small))
    ) {
        items(triviaList) { trivia ->
            FavoriteItem(
                trivia = trivia,
                onNumberDetailClicked = onNumberDetailClicked)
        }
    }
}

@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel = viewModel(),
    onNumberDetailClicked: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val triviaList = viewModel.favoriteTriviaList.collectAsState()
    FavoriteColumnList(
        triviaList = triviaList.value,
        onNumberDetailClicked = onNumberDetailClicked,
        modifier = modifier
    )
}

@Preview
@Composable
fun FavoriteColumnListScreen() {
    val triviaList = List(3) {
        Trivia(
            number = stringResource(R.string.tools_number),
            description = stringResource(R.string.tools_desc)
        )
    }
    NumberTriviaTheme {
        FavoriteColumnList(triviaList)
    }
}