package com.sbajt.matscounter.ui.composables.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.sbajt.matscounter.ui.composables.views.AppBarAction
import com.sbajt.matscounter.ui.models.appBars.AppBarState
import com.sbajt.matscounter.ui.models.appBars.BaseAppBarState
import com.sbajt.matscounter.ui.navigation.ItemBuildPath
import com.sbajt.matscounter.ui.theme.FactoryTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldLayout(
    modifier: Modifier = Modifier,
    appBarState: AppBarState?,
    navController: NavController? = null,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.padding(bottom = FactoryTheme.dimensions.medium),
                        style = FactoryTheme.typography.titleTextNormal,
                        color = FactoryTheme.colors.primary,
                        text = if (appBarState is AppBarState.Empty || appBarState == null) {
                            ""
                        } else {
                            (appBarState as? BaseAppBarState)?.title
                                ?: ""
                        }
                    )
                },
                actions = {
                    if (navController != null && appBarState is BaseAppBarState) {
                        appBarState.actionList.forEach {
                            AppBarAction(
                                action = it,
                                onClick = { navController.navigate(ItemBuildPath) })
                        }
                    }
                },
                windowInsets = TopAppBarDefaults.windowInsets,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = FactoryTheme.colors.secondary,
                ),
            )
        }
    ) { innerPadding ->
        content(innerPadding)
    }
}
