package com.santimattius.kmp.skeleton.di

import com.santimattius.kmp.skeleton.features.home.HomeViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

val sharedModules = module {}

val homeModule = module {
    viewModelOf(::HomeViewModel)
}

expect val platformModule: Module


fun applicationModules() = listOf(sharedModules, homeModule, platformModule)