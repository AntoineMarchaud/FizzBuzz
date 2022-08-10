package com.amarchaud.fizzbuzz.di

import com.amarchaud.fizzbuzz.domain.repository.FizzBuzzRepository
import com.amarchaud.fizzbuzz.domain.usecase.GetFizzBuzzUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {
    @Provides
    fun provideConnectUseCase(
        fizzBuzzRepository: FizzBuzzRepository
    ): GetFizzBuzzUseCase =
        GetFizzBuzzUseCase(
            repository = fizzBuzzRepository
        )
}