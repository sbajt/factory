package com.sbajt.matscounter.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import com.sbajt.matscounter.ui.theme.MatsCounterTheme
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MainScreenViewModel by viewModel()

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
    private fun setupContent() {
        val mainUiState by viewModel.uiState.collectAsStateWithLifecycle()
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
                onCountChange = { newItemCount ->
                    viewModel.updateSelectedItemCount(newItemCount)
                }
            )
        }
    }
}
