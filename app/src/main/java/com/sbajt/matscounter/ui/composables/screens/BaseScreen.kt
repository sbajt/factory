package com.sbajt.matscounter.ui.composables.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sbajt.matscounter.ui.composables.views.AppBarAction
import com.sbajt.matscounter.ui.models.appBars.AppBarActionType
import com.sbajt.matscounter.ui.models.appBars.AppBarState
import com.sbajt.matscounter.ui.models.appBars.BaseAppBarState
import com.sbajt.matscounter.ui.navigation.ItemBuildPath
import com.sbajt.matscounter.ui.theme.FactoryTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseScreen(
    modifier: Modifier = Modifier,
    appBarState: AppBarState?,
    navController: NavController,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets(0.dp),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.padding(bottom = FactoryTheme.dimensions.medium),
                        style = FactoryTheme.typography.titleTextNormal,
                        color = FactoryTheme.colors.primary,
                        text = if (appBarState is AppBarState.Empty || appBarState == null) {
                            ""
                        } else
                            (appBarState as? AppBarState.ItemListAppBar)?.title ?: ""
                    )
                },
                actions = {
                    if ((appBarState as? BaseAppBarState)?.actionList?.isNotEmpty() == true) {
                        AppBarAction(
                            action = AppBarActionType.ACTION_BUILD_PATH,
                            onClick = {
                                navController.navigate(ItemBuildPath)
                            },

                            )
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