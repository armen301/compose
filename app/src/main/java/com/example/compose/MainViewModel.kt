package com.example.compose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose.data.DoorService
import com.example.compose.data.DoorState
import com.example.compose.ui.screen.home.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    doorService: DoorService
) : ViewModel() {

    private val _command = Channel<Command>(BUFFERED)
    val command: Flow<Command> = _command.receiveAsFlow()

    private val _viewState = MutableStateFlow(ViewState())
    val viewState = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            doorService.doorState.collect {
                _command.trySend(Command.ShowSnackBar(it))
            }
        }
    }

    class ViewState(
        val carName: String = "QX 55",
        val gasMil: String = "120 ml",
    )
}

sealed class Command {
    class ShowSnackBar(val doorState: DoorState) : Command()
}

