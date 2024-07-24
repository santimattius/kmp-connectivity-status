package com.santimattius.kmp.network.checker

import kotlinx.coroutines.flow.MutableStateFlow
import platform.Network.nw_path_get_status
import platform.Network.nw_path_monitor_cancel
import platform.Network.nw_path_monitor_create
import platform.Network.nw_path_monitor_set_queue
import platform.Network.nw_path_monitor_set_update_handler
import platform.Network.nw_path_monitor_start
import platform.Network.nw_path_status_satisfied
import platform.darwin.dispatch_get_main_queue

actual class ConnectivityStatus {

    private val monitor = nw_path_monitor_create()

    private var _isNetworkAvailable = MutableStateFlow(false)

    init {
        nw_path_monitor_set_update_handler(monitor) { path ->
            val pathStatus = nw_path_get_status(path)
            _isNetworkAvailable.value = pathStatus == nw_path_status_satisfied
        }
    }

    actual val isNetworkConnected: MutableStateFlow<Boolean>
        get() = _isNetworkAvailable

    actual fun start() {
        nw_path_monitor_set_queue(monitor, dispatch_get_main_queue())
        nw_path_monitor_start(monitor)
    }

    actual fun stop() {
        nw_path_monitor_cancel(monitor)
    }
}