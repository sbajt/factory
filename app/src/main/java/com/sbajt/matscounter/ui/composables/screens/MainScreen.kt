package com.sbajt.matscounter.ui.composables.screens

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sbajt.matscounter.ui.appBarSubject
import com.sbajt.matscounter.ui.models.ItemGroupType
import com.sbajt.matscounter.ui.models.appBars.ActionBarActions
import com.sbajt.matscounter.ui.models.appBars.AppBarState
import com.sbajt.matscounter.ui.navigation.ItemBuildPath
import com.sbajt.matscounter.ui.navigation.ItemDetails
import com.sbajt.matscounter.ui.navigation.ItemList
import com.sbajt.matscounter.ui.theme.FactoryTheme
import com.sbajt.matscounter.ui.viewModels.ItemBuildPathScreenViewModel
import com.sbajt.matscounter.ui.viewModels.ItemDetailsScreenViewModel
import com.sbajt.matscounter.ui.viewModels.ItemListViewModel
import kotlinx.coroutines.flow.update

typealias OnItemSelected = (String?, ItemGroupType?) -> Unit
typealias OnCountChange = (Int) -> Unit
typealias OnNavigate = () -> Unit

@Composable
fun MainScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        modifier = modifier.background(FactoryTheme.colors.background),
        navController = navController,
        startDestination = ItemList
    ) {

        composable<ItemList> {
            val viewModel = viewModel<ItemListViewModel>()
            val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

            ItemListScreen(
                uiState = uiState,
                onItemSelected = { itemName, itemGroupType ->
                    viewModel.updateSelectedItem(
                        selectedItemName = itemName,
                        selectedItemGroupType = itemGroupType,
                        navController = navController,
                    )
                }
            )
        }
        composable<ItemDetails> {
            val viewModel = viewModel<ItemDetailsScreenViewModel>()
            val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
            appBarSubject.update {
                AppBarState.ItemList(
                    title = "Details",
                    actionList = listOf(ActionBarActions.SHOW_ITEM_BUILD_PATH),
                )
            }
            ItemDetailsScreen(
                uiState = uiState,
                onCountChange = viewModel::updateSelectedItemAmount,
                onNavigate = {
                    viewModel.navigateToBuildPath(navController = navController)

                }
            )
        }
        composable<ItemBuildPath> {
            appBarSubject.update {
                AppBarState.ItemList(
                    title = "Build path",
                    actionList = emptyList(),
                )
            }
            val uiState =
                viewModel<ItemBuildPathScreenViewModel>().uiState.collectAsStateWithLifecycle().value
            ItemBuildPathScreen(
                uiState = uiState
            )
        }
    }
}


