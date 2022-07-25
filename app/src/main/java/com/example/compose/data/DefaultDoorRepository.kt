package com.example.compose.data

class DefaultDoorRepository : DoorRepository {
    override suspend fun openDoors(): ResourceResponse<Unit> {
        return ResourceResponse.Success(Unit)
    }

    override suspend fun closeDoors(): ResourceResponse<Unit> {
        return ResourceResponse.Success(Unit)
    }
}

interface DoorRepository {
    suspend fun openDoors(): ResourceResponse<Unit>
    suspend fun closeDoors(): ResourceResponse<Unit>
}

sealed class ResourceResponse<T> {
    class Success<T>(val data: T) : ResourceResponse<T>()
    class HttpError<T>(val status: Int) : ResourceResponse<T>()
    class Fail<T>(val t: Throwable) : ResourceResponse<T>()
}


