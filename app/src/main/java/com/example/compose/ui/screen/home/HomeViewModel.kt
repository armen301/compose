package com.example.compose.ui.screen.home

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose.R
import com.example.compose.data.DoorService
import com.example.compose.data.DoorState
import com.example.compose.ui.component.DoorsData
import com.example.compose.ui.component.EngineData
import com.example.compose.ui.theme.Gray
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val service: DoorService,
) : ViewModel() {

    private val _viewState = MutableStateFlow(ViewState())
    val viewState = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            service.doorState.collect {
                val oldState = _viewState.value
                _viewState.value = when (it) {
                    DoorState.LOCKED -> oldState.copy(
                        doorState = it,
                        doorsData = DoorsData(
                            statusText = "Locked",
                            lockColor = Gray,
                            unlockColor = Color.Black,
                        )
                    )
                    DoorState.UNLOCKED -> oldState.copy(
                        doorState = it,
                        doorsData = DoorsData(
                            statusText = "Unlocked",
                            lockColor = Color.Black,
                            unlockColor = Gray,
                        )
                    )
                }
            }
        }
    }

    fun openDoors() {
        viewModelScope.launch {
            _viewState.value = _viewState.value.copy(
                doorsData = DoorsData(
                    statusText = "...",
                    lockColor = Color.Black,
                    unlockColor = Gray,
                    unlockedProcessing = true
                )
            )
            service.openDoors()
        }
    }

    fun closeDoors() {
        viewModelScope.launch {
            _viewState.value = _viewState.value.copy(
                doorsData = DoorsData(
                    statusText = "...",
                    lockColor = Gray,
                    unlockColor = Color.Black,
                    lockedProcessing = true
                )
            )
            service.closeDoors()
        }
    }
}

@Immutable
data class ViewState(
    val image: Int = R.drawable.imp_car,
    val refreshText: String = "Updated 1min ago",
    val doorsData: DoorsData = DoorsData(),
    val engineData: EngineData = EngineData(),
    val doorState: DoorState = DoorState.LOCKED,
)
