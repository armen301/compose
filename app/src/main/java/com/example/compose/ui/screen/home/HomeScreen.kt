package com.example.compose.ui.screen.home

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.compose.MainViewModel
import com.example.compose.R
import com.example.compose.data.DoorState
import com.example.compose.ui.component.DoorsComponent
import com.example.compose.ui.component.EngineComponent
import com.example.compose.ui.component.IconWithTextComponent
import com.example.compose.ui.theme.HomeBackground
import com.example.compose.ui.theme.TextSecondary
import java.util.Locale

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel(), parenViewModel: MainViewModel) {

    val state by viewModel.viewState.collectAsState()

    val openDialog = remember { mutableStateOf<DialogData?>(null) }
    val scrollState = rememberScrollState()
    Box {
        BackgroundComponent()
        Column(modifier = Modifier.verticalScroll(scrollState)) {
            Box(modifier = Modifier.fillMaxWidth()) {
                IconWithTextComponent(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .align(Alignment.Center),
                    iconResource = R.drawable.ic_refresh,
                    text = state.refreshText,
                    style = MaterialTheme.typography.body2.copy(color = TextSecondary),
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painterResource(id = state.image),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 16.dp),
                    contentDescription = null
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Divider(
                        thickness = 3.dp,
                        modifier = Modifier.width(32.dp),
                        color = MaterialTheme.colors.secondary
                    )
                    Divider(
                        color = TextSecondary,
                        modifier = Modifier
                            .width(48.dp)
                            .padding(horizontal = 8.dp),
                        thickness = 3.dp,
                    )
                    Divider(
                        color = TextSecondary,
                        modifier = Modifier.width(32.dp),
                        thickness = 3.dp,
                    )
                }

                Row(
                    modifier = Modifier
                        .padding(horizontal = 32.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    val unlockHandler = if (state.doorState == DoorState.LOCKED) {
                        val confirmText =
                            stringResource(id = R.string.confirm_text, DoorState.UNLOCKED.toStr().replaceFirstChar {
                                if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                            })
                        val message = stringResource(
                            id = R.string.dialog_message,
                            DoorState.UNLOCKED.toStr(),
                            parenViewModel.viewState.value.carName
                        );
                        {
                            openDialog.value = DialogData(
                                message = message,
                                confirmText = confirmText
                            )
                        }
                    } else {
                        {}
                    }
                    val lockHandler = if (state.doorState == DoorState.UNLOCKED) {
                        val confirmText =
                            stringResource(id = R.string.confirm_text, DoorState.LOCKED.toStr().replaceFirstChar {
                                if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                            })
                        val message = stringResource(
                            id = R.string.dialog_message,
                            DoorState.LOCKED.toStr(),
                            parenViewModel.viewState.value.carName
                        );
                        {
                            openDialog.value = DialogData(
                                message = message,
                                confirmText = confirmText
                            )
                        }
                    } else {
                        {}
                    }
                    DoorsComponent(data = state.doorsData, onLock = lockHandler, onUnlock = unlockHandler)
                    EngineComponent(data = state.engineData, onStart = { }, onStop = {})
                }
            }
        }

        val dialogData = openDialog.value
        if (dialogData != null) {
            DialogComponent(
                dialogData,
                onCancel = { openDialog.value = null },
                onConfirm = {
                    when (state.doorState) {
                        DoorState.LOCKED -> viewModel.openDoors()
                        DoorState.UNLOCKED -> viewModel.closeDoors()
                    }

                    openDialog.value = null
                }
            )
        }
    }
}

@Composable
private fun DoorState.toStr(): String {
    return when (this) {
        DoorState.LOCKED -> stringResource(id = R.string.lock)
        DoorState.UNLOCKED -> stringResource(id = R.string.unlock)
    }
}

@Composable
private fun DialogComponent(data: DialogData, onCancel: () -> Unit, onConfirm: () -> Unit) {

    AlertDialog(
        onDismissRequest = { },
        title = { Text(text = "Are you sure?") },
        text = { Text(text = data.message) },
        confirmButton = {
            Button(onClick = onConfirm, colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue)) {
                Text(text = data.confirmText, color = MaterialTheme.colors.primary)
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text(text = "Cancel", color = Color.Blue)
            }
        },
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    )
}

private class DialogData(
    val message: String,
    val confirmText: String,
)

@Composable
fun BackgroundComponent() {
    Column(Modifier.fillMaxHeight()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            HomeBackground,
                            MaterialTheme.colors.primary
                        )
                    )
                )
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = HomeBackground)
        )
    }
}

@Preview
@Composable
fun HomePreview() {
//    HomeScreen()
}
