package com.sbajt.matscounter.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sbajt.matscounter.ui.composables.screens.MainScreen
import com.sbajt.matscounter.ui.theme.FactoryTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        initBackPress()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                setContent {
                    setupContent()
                }
            }
        }
    }

    private fun initBackPress() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                runOnBackPressed()
                if (isEnabled) {
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        })
    }

    private fun runOnBackPressed() {
        lifecycleScope.launch {
            if (::navController.isInitialized) {
                if (navController.previousBackStackEntry == null) {
                    finish()
                }
            }
        }
    }

    override fun onBackPressed() {
        runOnBackPressed()
        super.onBackPressed()
    }

    @Composable
    private fun setupContent() {
        FactoryTheme {
            val bottomPadding = WindowInsets.navigationBars.asPaddingValues()

            navController = rememberNavController()
            MainScreen(
                modifier = Modifier.padding(bottom = bottomPadding.calculateBottomPadding()),
                navController = navController,
            )
        }
    }
}

