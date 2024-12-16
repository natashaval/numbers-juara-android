package com.natashaval.numbertrivia.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.natashaval.numbertrivia.BuildConfig
import com.natashaval.numbertrivia.compose.ui.theme.NumberTriviaTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        setContent {
            NumberTriviaTheme {
                NumberTriviaComposeApp()
            }
        }
    }
}