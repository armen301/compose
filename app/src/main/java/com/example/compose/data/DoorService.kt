package com.example.compose.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch


class DoorService(
    private val repository: DoorRepository,
    dispatcherProvider: DispatcherProvider,
) {

    private val coroutineScope by lazy {
        CoroutineScope(Job() + dispatcherProvider.default)
    }

    private val _doorsState = MutableSharedFlow<DoorState>(replay = 1)
    val doorState = _doorsState.asSharedFlow()

    suspend fun openDoors() {
        coroutineScope.launch {
            delay(5000)
            val state =
                if (repository.openDoors() is ResourceResponse.Success) DoorState.UNLOCKED else DoorState.LOCKED
            _doorsState.tryEmit(state)
        }
    }

    suspend fun closeDoors() {
        coroutineScope.launch {
            delay(5000)
            val state =
                if (repository.closeDoors() is ResourceResponse.Success) DoorState.LOCKED else DoorState.UNLOCKED
            _doorsState.tryEmit(state)
        }
    }

}

