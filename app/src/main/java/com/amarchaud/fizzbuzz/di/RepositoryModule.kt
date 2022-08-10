package com.amarchaud.fizzbuzz.di

import com.amarchaud.fizzbuzz.data.repository.FizzBuzzRepositoryImpl
import com.amarchaud.fizzbuzz.domain.repository.FizzBuzzRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindDataRepo(repository: FizzBuzzRepositoryImpl): FizzBuzzRepository
}