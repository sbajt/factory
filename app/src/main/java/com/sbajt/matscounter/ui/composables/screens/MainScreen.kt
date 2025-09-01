package com.sbajt.matscounter.ui.composables.screens

import androidx.compose.foundation.background
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sbajt.matscounter.ui.models.ItemGroupType
import com.sbajt.matscounter.ui.navigation.ItemBuildComponents
import com.sbajt.matscounter.ui.navigation.ItemDetails
import com.sbajt.matscounter.ui.navigation.ItemList
import com.sbajt.matscounter.ui.theme.MatsCounterTheme
import com.sbajt.matscounter.ui.viewModels.ItemBuildPathScreenViewModel
import com.sbajt.matscounter.ui.viewModels.ItemDetailsScreenViewModel
import com.sbajt.matscounter.ui.viewModels.ItemUiStateListViewModel

typealias OnItemSelected = (String?, ItemGroupType?) -> Unit
typealias OnCountChange = (Int) -> Unit
typealias OnNavigate = () -> Unit

@Composable
fun MainScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Surface {
        NavHost(
            modifier = modifier.background(MatsCounterTheme.colors.background),
            navController = navController,
            startDestination = ItemList
        ) {

            composable<ItemList> {
                val viewModel = viewModel<ItemUiStateListViewModel>()
                val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
                ItemListScreen(
                    uiState = uiState,
                    onItemSelected = { itemName, itemGroupType ->
                        viewModel.updateSelectedItem(navController = navController, selectedItemName = itemName, selectedItemGroupType = itemGroupType)
                    }
                )
            }
            composable<ItemDetails> {
                val viewModel = viewModel<ItemDetailsScreenViewModel>()
                val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
                ItemDetailsScreen(
                    uiState = uiState,
                    onCountChange = viewModel::updateSelectedItemAmount,
                    onNavigate = {
                        viewModel.navigateToBuildPath(navController = navController)
                    }
                )
            }
            composable<ItemBuildComponents> {
                val uiState = viewModel<ItemBuildPathScreenViewModel>().uiState.collectAsStateWithLifecycle().value
                ItemBuildPathScreen(
                    uiState = uiState
                )
            }
        }
    }
}
