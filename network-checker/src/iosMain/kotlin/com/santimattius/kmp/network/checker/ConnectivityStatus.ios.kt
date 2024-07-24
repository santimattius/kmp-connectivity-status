package com.santimattius.kmp.network.checker

import co.touchlab.kermit.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import platform.Network.nw_interface_type_wifi
import platform.Network.nw_path_get_status
import platform.Network.nw_path_monitor_cancel
import platform.Network.nw_path_monitor_create
import platform.Network.nw_path_monitor_create_with_type
import platform.Network.nw_path_monitor_set_queue
import platform.Network.nw_path_monitor_set_update_handler
import platform.Network.nw_path_monitor_start
import platform.Network.nw_path_status_invalid
import platform.Network.nw_path_status_satisfied
import platform.darwin.dispatch_get_main_queue

actual class ConnectivityStatus {

    private val monitor = nw_path_monitor_create_with_type(required_interface_type = nw_interface_type_wifi)

    private var _isNetworkAvailable = MutableStateFlow(false)

    init {
        nw_path_monitor_set_update_handler(monitor) { path ->
            val pathStatus = nw_path_get_status(path)
            Logger.i { "Status: $pathStatus" }
            _isNetworkAvailable.update { pathStatus == nw_path_status_satisfied }
        }
        nw_path_monitor_set_queue(monitor, dispatch_get_main_queue())

    }

    actual val isNetworkConnected: MutableStateFlow<Boolean>
        get() = _isNetworkAvailable

    actual fun start() {
        nw_path_monitor_start(monitor)
    }

    actual fun stop() {
        nw_path_monitor_cancel(monitor)
    }
}