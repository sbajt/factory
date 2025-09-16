package com.sbajt.matscounter.ui.composables.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sbajt.matscounter.R
import com.sbajt.matscounter.ui.actionBarSubject
import com.sbajt.matscounter.ui.models.ActionBarActions
import com.sbajt.matscounter.ui.models.ItemGroupType
import com.sbajt.matscounter.ui.navigation.ItemBuildPath
import com.sbajt.matscounter.ui.navigation.ItemDetails
import com.sbajt.matscounter.ui.navigation.ItemList
import com.sbajt.matscounter.ui.theme.FactoryTheme
import com.sbajt.matscounter.ui.viewModels.ItemBuildPathScreenViewModel
import com.sbajt.matscounter.ui.viewModels.ItemDetailsScreenViewModel
import com.sbajt.matscounter.ui.viewModels.ItemListViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update

typealias OnItemSelected = (String?, ItemGroupType?) -> Unit
typealias OnCountChange = (Int) -> Unit
typealias OnNavigate = () -> Unit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = FactoryTheme.colors.secondary,
                    titleContentColor = FactoryTheme.colors.secondary,
                ),
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        color = FactoryTheme.colors.primary
                    )
                },
                actions = {
                    val tint = FactoryTheme.colors.primary
                    actionBarSubject.collect {
                        it.forEach { action ->
                            when (action) {
                                ActionBarActions.SHOW_ITEM_BUILD_PATH -> {
                                    Icon(
                                        modifier = Modifier.clickable {
                                            navController.navigate(ItemBuildPath)
                                        },
                                        imageVector = Icons.Default.Info,
                                        contentDescription = "Item Details",
                                        tint = tint
                                    )
                                }
                            }
                        }
                    }
                }
            )
        },
    ) { paddingValues ->
        MainScreenContent(
            modifier = Modifier.padding(paddingValues),
            navController = navController
        )
    }
}

@Composable
fun MainScreenContent(
    navController: NavHostController,
    modifier: Modifier,
) {
    NavHost(
        modifier = modifier.background(FactoryTheme.colors.background),
        navController = navController,
        startDestination = ItemList
    ) {

        composable<ItemList> {
            actionBarSubject.update { listOf() }
            val viewModel = viewModel<ItemListViewModel>()
            val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
            ItemListScreen(
                uiState = uiState,
                onItemSelected = { itemName, itemGroupType ->
                    viewModel::updateSelectedItem
                }
            )
        }
        composable<ItemDetails> {
            val viewModel = viewModel<ItemDetailsScreenViewModel>()
            val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
            actionBarSubject.update { listOf(ActionBarActions.SHOW_ITEM_BUILD_PATH) }
            ItemDetailsScreen(
                uiState = uiState,
                onCountChange = viewModel::updateSelectedItemAmount,
                onNavigate = {
                    viewModel::navigateToBuildPath
                }
            )
        }
        composable<ItemBuildPath> {
            actionBarSubject.update { listOf() }
            val uiState = viewModel<ItemBuildPathScreenViewModel>().uiState.collectAsStateWithLifecycle().value
            ItemBuildPathScreen(
                uiState = uiState
            )
        }
    }
}


