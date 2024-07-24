package com.santimattius.kmp.skeleton.features.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.santimattius.kmp.skeleton.core.ui.components.AppBar
import kmp_connectivity_status.composeapp.generated.resources.Res
import kmp_connectivity_status.composeapp.generated.resources.compose_multiplatform
import kmp_connectivity_status.composeapp.generated.resources.ic_connection_off
import kmp_connectivity_status.composeapp.generated.resources.ic_connection_on
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun HomeScreen(
    screenModel: HomeViewModel = koinViewModel<HomeViewModel>(),
) {
    val state by screenModel.state.collectAsStateWithLifecycle()
    Scaffold(
        topBar = { AppBar(title = "Connectivity Status") },
    ) {
        Box(
            modifier = Modifier.fillMaxSize().padding(it),
            contentAlignment = Alignment.Center
        ) {
            if (state.isConnected){
                Image(
                    painterResource(Res.drawable.ic_connection_on),
                    null,
                    colorFilter = ColorFilter.tint(Color.Green),
                )
            }else{
                Image(
                    painterResource(Res.drawable.ic_connection_off),
                    null,
                    colorFilter = ColorFilter.tint(Color.Red),
                )
            }
        }
    }
}
