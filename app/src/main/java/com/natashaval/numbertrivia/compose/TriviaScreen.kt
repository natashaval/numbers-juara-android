package com.natashaval.numbertrivia.compose

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.natashaval.numbertrivia.R
import com.natashaval.numbertrivia.compose.ui.DetailScreen
import com.natashaval.numbertrivia.compose.ui.FavoriteScreen
import com.natashaval.numbertrivia.compose.ui.NumberScreen
import com.natashaval.numbertrivia.compose.ui.theme.NumberTriviaTheme
import com.natashaval.numbertrivia.compose.viewmodel.ComposeViewModel
import com.natashaval.numbertrivia.compose.viewmodel.FavoriteViewModel

enum class TriviaScreen(@StringRes val title: Int) {
    Number(title = R.string.label_number_compose),
    Favorite(title = R.string.action_favorite),
    Detail(title = R.string.label_detail)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NumberTopAppBar(
    currentScreen: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    isMainScreen: Boolean = false,
    onActionFavoriteClicked: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        title = { Text(text = currentScreen) },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        },
        actions = {
            if (isMainScreen) {
                IconButton(onClick = onActionFavoriteClicked) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = stringResource(R.string.action_favorite)
                    )
                }
            }
        })
}

@Composable
fun NumberTriviaComposeApp(
    navController: NavHostController = rememberNavController(),
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry?.destination?.route ?: TriviaScreen.valueOf(TriviaScreen.Number.name).toString()
    Scaffold(
        topBar = {
            NumberTopAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { if (navController.previousBackStackEntry != null) navController.navigateUp() },
                isMainScreen = currentScreen == TriviaScreen.Number.name,
                onActionFavoriteClicked = {
                    navController.navigate(TriviaScreen.Favorite.name)
                }
            )
        }
    ) { innerPadding ->
//        https://developer.android.com/codelabs/basic-android-kotlin-compose-navigation
        NavHost(
            navController = navController,
            startDestination = TriviaScreen.Number.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = TriviaScreen.Number.name) { backStackEntry ->
                val viewModel : ComposeViewModel = hiltViewModel(backStackEntry)
                NumberScreen(
                    viewModel = viewModel,
                    onNumberDetailClicked = { number ->
                        navController.navigate("${TriviaScreen.Detail.name}/$number")
                    },
                    modifier = Modifier.fillMaxHeight()
                )
            }
            composable(route = TriviaScreen.Favorite.name) {
                FavoriteScreen(
                    viewModel = hiltViewModel<FavoriteViewModel>(),
                    onNumberDetailClicked = { number ->
                        navController.navigate("${TriviaScreen.Detail.name}/$number")
                    },
                    modifier = Modifier.fillMaxHeight()
                )
            }
            // Avoid passing complex data when navigating, instead pass minimum necessary information
            composable(route = "${TriviaScreen.Detail.name}/{numberId}") { backStackEntry ->
                // https://developer.android.com/develop/ui/compose/libraries#hilt-navigation
//                https://stackoverflow.com/a/78377921

                // check if coming from NumberScreen or FavoriteScreen
                val previousScreen = navController.previousBackStackEntry?.destination?.route
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry(TriviaScreen.Number.name)
                }
                val numberId = backStackEntry.arguments?.getString("numberId")
                numberId?.let {
                    val parentViewModel = hiltViewModel<ComposeViewModel>(parentEntry)
                    DetailScreen(
                        previousScreen = previousScreen.orEmpty(),
                        numberId = it,
                        viewModel = parentViewModel,
                        modifier = Modifier.fillMaxHeight()
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NumberTriviaComposePreview() {
    NumberTriviaTheme {
        NumberTriviaComposeApp()
    }
}