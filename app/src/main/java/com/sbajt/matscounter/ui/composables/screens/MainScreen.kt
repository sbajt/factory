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
import com.sbajt.matscounter.ui.models.ItemGroupType
import com.sbajt.matscounter.ui.navigation.ItemBuildPath
import com.sbajt.matscounter.ui.navigation.ItemDetails
import com.sbajt.matscounter.ui.navigation.ItemList
import com.sbajt.matscounter.ui.theme.FactoryTheme
import com.sbajt.matscounter.ui.viewModels.ItemBuildPathScreenViewModel
import com.sbajt.matscounter.ui.viewModels.ItemDetailsScreenViewModel
import com.sbajt.matscounter.ui.viewModels.ItemListScreenViewModel

typealias OnItemSelected = (String, ItemGroupType) -> Unit
typealias OnCountChange = (Int) -> Unit
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
            val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
            ItemListScreen(
                modifier = modifier.background(FactoryTheme.colors.background),
                uiState = uiState,
                onNavigate = {
                    viewModel.navigateToItemDetails(navController = navController)
                },
                onItemSelected = viewModel::updateSelectedItem,
                navController = navController,
            )
        }
        composable<ItemDetails> {
            val viewModel = viewModel<ItemDetailsScreenViewModel>()
            val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

            ItemDetailsScreen(
                modifier = modifier.background(FactoryTheme.colors.background),
                uiState = uiState,
                onCountChange = viewModel::updateSelectedItemAmount,
                navController = navController,
                onNavigate = {
                    viewModel.navigateToBuildPath(navController = navController)
                }
            )
        }
        composable<ItemBuildPath> {
            val uiState =
                viewModel<ItemBuildPathScreenViewModel>().uiState.collectAsStateWithLifecycle().value
            ItemBuildPathScreen(
                modifier = modifier.background(FactoryTheme.colors.background),
                uiState = uiState
            )
        }
    }
}


