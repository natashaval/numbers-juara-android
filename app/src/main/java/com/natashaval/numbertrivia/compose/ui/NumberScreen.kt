package com.natashaval.numbertrivia.compose.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.natashaval.numbertrivia.R
import com.natashaval.numbertrivia.compose.ui.theme.NumberTriviaTheme

@Composable
fun NumberDescLayout(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
//            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                onClick = {},
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 40.dp),
            ) {
                Text(
                    text = stringResource(R.string.tools_number),
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
            text = stringResource(R.string.tools_desc),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NumberDescLayoutPreview() {
    NumberTriviaTheme {
        NumberDescLayout()
    }
}

@Composable
fun NumberTextField(modifier: Modifier = Modifier) {
    TextField(
        value = "",
        onValueChange = {},
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
        }
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
fun SelectionLayout(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NumberTextField(
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
            onClick = {},
        )
    }
}

@Preview
@Composable
fun SelectionPreview() {
    NumberTriviaTheme {
        SelectionLayout()
    }
}

@Composable
fun NumberScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        NumberDescLayout()
        SelectionLayout(
            modifier = Modifier.padding(top = 32.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NumberScreenPreview() {
    NumberTriviaTheme {
        NumberScreen(
            modifier = Modifier.fillMaxHeight(),
        )
    }
}