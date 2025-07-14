package com.sbajt.matscounter.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.ui.Modifier
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
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.fetchData()
                viewModel.uiState
                    .collect { mainUiState ->
                        setContent {
                            MatsCounterTheme {
                                val topPadding = WindowInsets.statusBars.asPaddingValues()
                                val bottomPadding = WindowInsets.navigationBars.asPaddingValues()
                                MainScreen(
                                    modifier = Modifier.padding(
                                        top = topPadding.calculateTopPadding(),
                                        bottom = bottomPadding.calculateBottomPadding()
                                    ),
                                    uiState = mainUiState,
                                    onItemSelected = { selectedItemName, selectedItemGroupType ->
                                        viewModel.updateSelectedItem(selectedItemName, selectedItemGroupType)
                                    },
                                )
                            }
                        }
                    }
            }
        }
    }
}
