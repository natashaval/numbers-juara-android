package com.natashaval.numbertrivia.compose

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.natashaval.numbertrivia.R
import com.natashaval.numbertrivia.compose.ui.DetailScreen
import com.natashaval.numbertrivia.compose.ui.FavoriteScreen
import com.natashaval.numbertrivia.compose.ui.theme.NumberTriviaTheme

enum class TriviaScreen {
    Number, Favorite, Detail
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NumberTopAppBar(
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    isMainScreen: Boolean = false,
    modifier: Modifier = Modifier
) {
    TopAppBar(modifier = modifier, colors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer
    ), title = {
        Text(stringResource(R.string.app_name) + "Compose")
    }, navigationIcon = {
        if (canNavigateBack) {
            IconButton(onClick = navigateUp) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back_button)
                )
            }
        }
    }, actions = {
        if (isMainScreen) {
            IconButton(
                onClick = {},
            ) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = stringResource(R.string.action_favorite)
                )
            }
        }
    })
}

@Preview(
    name = "top app bar", showBackground = true
)
@Composable
fun SmallTopAppBarPreview() {
    NumberTriviaTheme {
        Scaffold(topBar = {
            NumberTopAppBar(canNavigateBack = false, navigateUp = {})
        }) { innerPadding ->
            Greeting("Bing")
        }
    }
}

@Composable
fun NumberTriviaComposeApp(
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        topBar = {
            NumberTopAppBar(
                canNavigateBack = false,
                navigateUp = {}
            )
        }
    ) { innerPadding ->
//        https://developer.android.com/codelabs/basic-android-kotlin-compose-navigation
        NavHost(
            navController = navController,
            startDestination = TriviaScreen.Number.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = TriviaScreen.Number.name) {

            }
            composable(route = TriviaScreen.Favorite.name) {
                FavoriteScreen(
                    triviaList = listOf()
                )
            }
            composable(route = TriviaScreen.Detail.name) {
                DetailScreen()
            }
        }
    }
}