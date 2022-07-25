package com.example.compose.ui.component

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Place
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.compose.R

sealed class Screen(val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {

    object Home : Screen("home", R.string.home, Icons.Default.Home)

    object Vehicle : Screen("vehicle", R.string.vehicle, Icons.Default.DirectionsCar)

    object Location : Screen("location", R.string.location, Icons.Default.Place)

    object More : Screen("more", R.string.more, Icons.Default.MoreHoriz)
}


