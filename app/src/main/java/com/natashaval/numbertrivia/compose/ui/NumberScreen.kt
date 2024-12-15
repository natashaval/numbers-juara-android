package com.natashaval.numbertrivia.compose.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.viewmodel.compose.viewModel
import com.natashaval.numbertrivia.R
import com.natashaval.numbertrivia.compose.model.Trivia
import com.natashaval.numbertrivia.compose.ui.theme.NumberTriviaTheme
import com.natashaval.numbertrivia.compose.viewmodel.ComposeViewModel

@Composable
fun NumberDescLayout(
    trivia: Trivia,
    onNumberDetailClicked: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                onClick = onNumberDetailClicked,
                modifier = Modifier
                    .defaultMinSize(minWidth = 1.dp, minHeight = 1.dp)
                    .weight(1f)
                    .padding(start = 40.dp),
            ) {
                Text(
                    text = trivia.number,
                    style = MaterialTheme.typography.displayLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }
            IconButton(
                onClick = {}
            ) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = stringResource(R.string.action_favorite)
                )
            }
        }
        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = trivia.description,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NumberDescLayoutPreview() {
    NumberTriviaTheme {
        NumberDescLayout(
            trivia = Trivia(
                number = stringResource(R.string.tools_number),
                description = stringResource(R.string.tools_desc)
            )
        )
    }
}

// https://developer.android.com/develop/ui/compose/state#state-hoisting
@Composable
fun NumberTextField(
    numberInput: String,
    onNumberChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    TextField(
        value = numberInput,
        onValueChange = { input ->
            if (input.isDigitsOnly()) onNumberChange(input)
        },
        modifier = modifier,
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_123),
                contentDescription = ""
            )
        },
        label = {
            Text(
                text = stringResource(R.string.insert_number)
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions({ keyboardController?.hide() })
    )
}

@Composable
fun NumberChip(@StringRes stringRes: Int) {
    var selected by remember { mutableStateOf(false) }
    FilterChip(
        onClick = { selected = !selected },
        label = {
            Text(stringResource(stringRes))
        },
        selected = selected,
    )
}

@Composable
fun SelectionLayout(
    number: String,
    onNumberChange: (String) -> Unit,
    onChipChange: (String) -> Unit,
    onGenerateButtonClicked: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .imePadding(), // windowSoftInput adjust resize, so button not hidden behind keyboard
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NumberTextField(
            numberInput = number,
            onNumberChange = onNumberChange,
            modifier = Modifier
        )
        Row(
            modifier = Modifier.padding(top = 16.dp)
        ) {
            NumberChip(R.string.chip_trivia)
            Spacer(modifier = Modifier.size(24.dp))
            NumberChip(R.string.chip_math)
        }
        CustomIconButton(
            modifier = Modifier.padding(top = 16.dp),
            icon = Icons.Default.Lightbulb,
            stringRes = R.string.generate,
            onClick = onGenerateButtonClicked,
        )
    }
}

@Preview
@Composable
fun SelectionPreview() {
    NumberTriviaTheme {
        SelectionLayout(number = "", onNumberChange = {}, onChipChange = {})
    }
}

@Composable
fun NumberScreen(
    viewModel: ComposeViewModel = viewModel(),
    onNumberDetailClicked: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val triviaUiState by viewModel.uiState.collectAsState()
    var numberInput by rememberSaveable { mutableStateOf("") }
    var chipType by rememberSaveable { mutableStateOf("trivia") }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        NumberDescLayout(
            trivia = triviaUiState,
            onNumberDetailClicked = onNumberDetailClicked
        )
        SelectionLayout(
            number = numberInput,
            onNumberChange = { numberInput = it },
            onChipChange = { chipType = it },
            onGenerateButtonClicked = {
                viewModel.getNumberApi(number = numberInput, type = chipType)
            },
            modifier = Modifier.padding(top = 32.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NumberScreenPreview() {
    NumberTriviaTheme {
        NumberScreen(
            modifier = Modifier.fillMaxHeight()
        )
    }
}