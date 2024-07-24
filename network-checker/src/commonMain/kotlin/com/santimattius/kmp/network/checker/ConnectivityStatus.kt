package com.santimattius.kmp.network.checker

import kotlinx.coroutines.flow.MutableStateFlow

expect class ConnectivityStatus {
    val isNetworkConnected: MutableStateFlow<Boolean>
    fun start()
    fun stop()
}