package com.srhdp.comicslibrary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.srhdp.comicslibrary.ui.theme.ComicsLibraryTheme
import com.srhdp.comicslibrary.view.CharacterBottomNav
import com.srhdp.comicslibrary.view.CollectionScreen
import com.srhdp.comicslibrary.view.LibraryScreen
import com.srhdp.comicslibrary.viewmodel.LibraryApiViewModel
import dagger.hilt.android.AndroidEntryPoint

sealed class Destination(val route: String) {
    object Library : Destination("library")
    object Collection : Destination("collection")
    object CharacterDetail : Destination("character/{characterId}") {
        fun createRoute(characterId: Int?) = "character/$characterId"
    }
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val lvm by viewModels<LibraryApiViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComicsLibraryTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    CharacterScaffold(navController = navController, lvm)
                }
            }
        }
    }
}

@Composable
fun CharacterScaffold(navController: NavHostController,  lvm: LibraryApiViewModel) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = { CharacterBottomNav(navController = navController) }
    ) { paddingValues ->
        NavHost(navController = navController, startDestination = Destination.Library.route) {
            composable(Destination.Library.route) {
                LibraryScreen(navController, lvm, paddingValues)
            }
            composable(Destination.Collection.route) {
                CollectionScreen()
            }
            composable(Destination.CharacterDetail.route) {

            }
        }

    }
}

