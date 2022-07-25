package com.example.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compose.data.DoorState
import com.example.compose.ui.component.BottomNavigationBar
import com.example.compose.ui.component.Screen.Home
import com.example.compose.ui.component.Screen.Location
import com.example.compose.ui.component.Screen.More
import com.example.compose.ui.component.Screen.Vehicle
import com.example.compose.ui.component.ScreenTitleComponent
import com.example.compose.ui.component.SnackBarHostComponent
import com.example.compose.ui.screen.LocationScreen
import com.example.compose.ui.screen.MoreScreen
import com.example.compose.ui.screen.VehicleScreen
import com.example.compose.ui.screen.home.HomeScreen
import com.example.compose.ui.theme.ComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val screens = listOf(Home, Vehicle, Location, More)

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTheme {
                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()
                val state by viewModel.viewState.collectAsState()
                ObserveCommand(scaffoldState = scaffoldState, viewModel)

                Scaffold(
                    topBar = { TopBarComponent(viewState = state) },
                    scaffoldState = scaffoldState,
                    snackbarHost = { SnackBarHostComponent(snackBarHostState = scaffoldState.snackbarHostState) },
                    bottomBar = { BottomNavigationBar(screens, navController) }
                ) {
                    Box(modifier = Modifier.padding(it)) {
                        SetupNavigationComponent(navController = navController, viewModel)
                    }
                }
            }
        }
    }
}

@Composable
private fun TopBarComponent(viewState: MainViewModel.ViewState) {
    Box(
        modifier = Modifier
            .padding(top = 32.dp, bottom = 16.dp)
            .fillMaxWidth()
    ) {
        ScreenTitleComponent(primaryText = viewState.carName, secondaryText = viewState.gasMil)
    }
}

@Composable
private fun ObserveCommand(scaffoldState: ScaffoldState, viewModel: MainViewModel = viewModel()) {
    val scope = rememberCoroutineScope()
    val lockedText = stringResource(id = R.string.doors_locked)
    val unlockedText = stringResource(id = R.string.doors_unlocked)
    viewModel.command.collectWhileStarted(LocalContext.current as MainActivity) {
        when (it) {
            is Command.ShowSnackBar -> {
                when (it.doorState) {
                    DoorState.LOCKED -> {
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar(lockedText)
                        }
                    }
                    DoorState.UNLOCKED -> {
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar(unlockedText)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SetupNavigationComponent(navController: NavHostController, parentViewModel: MainViewModel) {
    NavHost(navController = navController, startDestination = Home.route) {
        composable(Home.route) { HomeScreen(parenViewModel = parentViewModel) }
        composable(Vehicle.route) { VehicleScreen() }
        composable(Location.route) { LocationScreen() }
        composable(More.route) { MoreScreen() }
    }
}

