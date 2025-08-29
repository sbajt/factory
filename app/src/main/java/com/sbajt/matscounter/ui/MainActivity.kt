package com.sbajt.matscounter.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sbajt.matscounter.ui.composables.screens.MainScreen
import com.sbajt.matscounter.ui.navigation.ItemDetails
import com.sbajt.matscounter.ui.theme.MatsCounterTheme
import com.sbajt.matscounter.ui.viewModels.ItemUiStateListViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                setContent {
                    setupContent()
                }
            }
        }

        onBackPressedDispatcher.addCallback(onBackPressedCallback = onBackPressedCallback)
    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {

        override fun handleOnBackPressed() {
            lifecycleScope.launch {
                if (::navController.isInitialized) {
                    val destination = navController.currentBackStackEntry?.destination
                    if (destination?.route == ItemDetails.ROUTE) {
                        ItemUiStateListViewModel().removeSelectedItem()
                    }
                }
                this@MainActivity.onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    @Composable
    private fun setupContent(
        modifier: Modifier = Modifier
    ) {
        MatsCounterTheme {
            val topPadding = WindowInsets.statusBars.asPaddingValues()
            val bottomPadding = WindowInsets.navigationBars.asPaddingValues()
            navController = rememberNavController()
            MainScreen(
                modifier = modifier.padding(top = topPadding.calculateTopPadding(), bottom = bottomPadding.calculateBottomPadding()),
                onCountChange = {},
                onItemSelected = { item, groupType -> },
                navController = navController,
            )
        }
    }
}

