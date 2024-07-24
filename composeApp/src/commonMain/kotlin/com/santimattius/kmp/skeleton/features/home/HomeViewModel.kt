package com.santimattius.kmp.skeleton.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santimattius.kmp.network.checker.ConnectivityStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn


data class HomeUiState(
    val isConnected: Boolean = false,
)

class HomeViewModel(
    private val connectivityStatus: ConnectivityStatus
) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = connectivityStatus.isNetworkConnected
        .map { isConnected ->
            HomeUiState(isConnected)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = _state.value
        )

    init {
        connectivityStatus.start()
    }

    override fun onCleared() {
        connectivityStatus.stop()
        super.onCleared()
    }
}