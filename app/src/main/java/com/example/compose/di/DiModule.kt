package com.example.compose.di

import com.example.compose.data.DefaultDoorRepository
import com.example.compose.data.DispatcherProvider
import com.example.compose.data.DoorRepository
import com.example.compose.data.DoorService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DiModule {

    @Singleton
    @Provides
    fun provideDoorRepository(): DoorRepository {
        return DefaultDoorRepository()
    }

    @Provides
    fun provideDispatcherProvider(): DispatcherProvider {
        return object : DispatcherProvider {
            override val main = Dispatchers.Main
            override val io = Dispatchers.IO
            override val default = Dispatchers.Default
            override val unconfined = Dispatchers.Unconfined
        }
    }

    @Singleton
    @Provides
    fun provideDoorService(
        repository: DoorRepository,
        dispatcherProvider: DispatcherProvider
    ): DoorService {
        return DoorService(repository, dispatcherProvider)
    }
}
