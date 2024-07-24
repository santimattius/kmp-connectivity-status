package com.santimattius.kmp.skeleton.di

import com.santimattius.kmp.network.checker.ConnectivityStatus
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single<ConnectivityStatus> { ConnectivityStatus() }
    }