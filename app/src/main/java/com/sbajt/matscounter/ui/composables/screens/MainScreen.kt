package com.sbajt.matscounter.ui.composables.screens

import androidx.compose.foundation.background
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sbajt.matscounter.ui.models.screens.BaseScreeUiState
import com.sbajt.matscounter.ui.models.screens.ItemBuildPathScreenUiState
import com.sbajt.matscounter.ui.models.screens.ItemDetailsScreenUiState
import com.sbajt.matscounter.ui.models.screens.ItemListScreenUiState
import com.sbajt.matscounter.ui.navigation.ItemBuildPath
import com.sbajt.matscounter.ui.navigation.ItemDetails
import com.sbajt.matscounter.ui.navigation.ItemList
import com.sbajt.matscounter.ui.theme.FactoryTheme
import com.sbajt.matscounter.ui.viewModels.ItemBuildPathScreenViewModel
import com.sbajt.matscounter.ui.viewModels.ItemDetailsScreenViewModel
import com.sbajt.matscounter.ui.viewModels.ItemListScreenViewModel

typealias OnNavigate = () -> Unit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = ItemList
    ) {

        composable<ItemList> {
            val viewModel = viewModel<ItemListScreenViewModel>()
            val screenUiState =
                viewModel.uiState.collectAsStateWithLifecycle().value
            Screen(uiState = screenUiState) { uiState ->
                ItemListScreen(
                    modifier = modifier.background(FactoryTheme.colors.background),
                    uiState = uiState as ItemListScreenUiState,
                    onItemSelected = { name, type ->
                        viewModel.updateSelectedItem(
                            selectedItemName = name,
                            selectedItemGroupType = type,
                            navController = navController
                        )
                    },
                )
            }
        }
        composable<ItemDetails> {
            val viewModel = viewModel<ItemDetailsScreenViewModel>()
            val screenUiState =
                viewModel.uiState.collectAsStateWithLifecycle().value
            Screen(uiState = screenUiState) { uiState ->
                ItemDetailsScreen(
                    modifier = modifier.background(FactoryTheme.colors.background),
                    uiState = uiState as ItemDetailsScreenUiState,
                    onCountChange = viewModel::updateSelectedItemAmount,
                    navController = navController,
                    onNavigate = {
                        viewModel.navigateToBuildPath(navController = navController)
                    }
                )
            }
        }
        composable<ItemBuildPath> {
            val viewModel = viewModel<ItemBuildPathScreenViewModel>()
            val screenUiState =
                viewModel.uiState.collectAsStateWithLifecycle().value
            Screen(uiState = screenUiState) { uiState ->
                ItemBuildPathScreen(
                    modifier = modifier.background(FactoryTheme.colors.background),
                    uiState = uiState as ItemBuildPathScreenUiState,
                )
            }
        }
    }
}

@Composable
fun <ScreenUiState : BaseScreeUiState> Screen(
    uiState: ScreenUiState,
    content: @Composable (ScreenUiState) -> Unit
) {
    when (uiState) {
        BaseScreeUiState.Empty -> EmptyScreen()
        BaseScreeUiState.Loading -> LoadingScreen()
        is ItemListScreenUiState -> content.invoke(uiState)
        is ItemDetailsScreenUiState -> content.invoke(uiState)
        is ItemBuildPathScreenUiState -> content.invoke(uiState)
    }
}


