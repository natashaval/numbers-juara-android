package com.natashaval.numbertrivia.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.natashaval.numbertrivia.compose.ui.theme.NumberTriviaTheme

class ComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NumberTriviaTheme {
                NumberTriviaComposeApp()
            }
        }
    }
}