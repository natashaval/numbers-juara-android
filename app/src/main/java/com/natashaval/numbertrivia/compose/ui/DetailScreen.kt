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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.natashaval.numbertrivia.R
import com.natashaval.numbertrivia.compose.ui.theme.NumberTriviaTheme


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
        onClick = onClick
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
fun DetailScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        NumberDescLayout()
        DetailCopySendButton()
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    NumberTriviaTheme {
        DetailScreen(
            modifier = Modifier.fillMaxHeight()
        )
    }
}