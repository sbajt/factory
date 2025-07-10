package com.sbajt.matscounter.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sbajt.matscounter.ui.composables.MainScreen
import com.sbajt.matscounter.ui.theme.MatsCounterTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val viewModel: MainScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getMainUiState()
                    .collect { mainUiState ->
                        setContent {
                            MatsCounterTheme {
                                MainScreen(
                                    uiState = mainUiState
                                )
                            }
                        }
                    }
            }
        }
    }
}
