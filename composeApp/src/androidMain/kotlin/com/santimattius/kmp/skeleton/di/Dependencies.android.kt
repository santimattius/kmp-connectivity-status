package com.santimattius.kmp.skeleton.di

import android.content.Context
import com.santimattius.kmp.network.checker.ConnectivityStatus
import com.santimattius.kmp.skeleton.applicationContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        factory<Context> {
            val context = applicationContext ?: run {
                throw IllegalArgumentException("Application context is null")
            }
            context
        }
        single<ConnectivityStatus> { ConnectivityStatus(get()) }
    }