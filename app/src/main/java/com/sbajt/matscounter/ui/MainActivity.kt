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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sbajt.matscounter.ui.composables.MainScreen
import com.sbajt.matscounter.ui.models.MainScreenUiState
import com.sbajt.matscounter.ui.theme.MatsCounterTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val viewModel: MainScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                setContent {
                    setupContent()
                }
            }
        }
    }

    @Composable
    private fun setupContent(modifier: Modifier = Modifier) {
        MatsCounterTheme {
            val mainUiState by viewModel.uiState.collectAsStateWithLifecycle()
            val topPadding = WindowInsets.statusBars.asPaddingValues()
            val bottomPadding = WindowInsets.navigationBars.asPaddingValues()
            MainScreen(
                modifier = modifier.padding(
                    top = topPadding.calculateTopPadding(),
                    bottom = bottomPadding.calculateBottomPadding()
                ),
                uiState = mainUiState,
                onItemSelected = { selectedItemName, selectedItemGroupType ->
                    viewModel.updateSelectedItem(
                        selectedItemName = selectedItemName,
                        selectedItemGroupType = selectedItemGroupType,
                        (mainUiState as MainScreenUiState.Content).inputSectionUiState?.itemCount ?: 0
                    )
                },
                onCountChange = { newItemCount ->
                    viewModel.updateSelectedItemCount(newItemCount)
                }
            )
        }
    }
}
