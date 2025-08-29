package com.sbajt.matscounter.ui.composables.screens

import androidx.compose.foundation.background
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sbajt.matscounter.ui.composables.previewProviders.MainScreenPreviewProvider
import com.sbajt.matscounter.ui.models.ItemGroupType
import com.sbajt.matscounter.ui.models.screens.ItemListScreenUiState
import com.sbajt.matscounter.ui.navigation.ItemBuildComponents
import com.sbajt.matscounter.ui.navigation.ItemDetails
import com.sbajt.matscounter.ui.navigation.ItemList
import com.sbajt.matscounter.ui.theme.MatsCounterTheme
import com.sbajt.matscounter.ui.viewModels.ItemBuildPathScreenViewModel
import com.sbajt.matscounter.ui.viewModels.ItemDetailsScreenViewModel
import com.sbajt.matscounter.ui.viewModels.ItemUiStateListViewModel

typealias OnItemSelected = (String?, ItemGroupType?) -> Unit
typealias OnCountChange = (Int) -> Unit

@Composable
fun MainScreen(
    navController: NavHostController,
    onItemSelected: OnItemSelected,
    onCountChange: OnCountChange,
    modifier: Modifier = Modifier
) {
    Surface {
        NavHost(
            modifier = modifier.background(MatsCounterTheme.colors.background),
            navController = navController,
            startDestination = ItemList
        ) {

            composable<ItemList> {
                val uiState = viewModel<ItemUiStateListViewModel>().uiState.collectAsStateWithLifecycle().value
                ItemListScreen(
                    uiState = uiState,
                    onItemSelected = onItemSelected,
                )
            }
            composable<ItemDetails> {
                val uiState = viewModel<ItemDetailsScreenViewModel>().uiState.collectAsStateWithLifecycle().value
                ItemDetailsScreen(
                    uiState = uiState,
                    navController = navController,
                    onCountChange = onCountChange,
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

@PreviewLightDark
@Composable
fun MainScreenPreview(@PreviewParameter(MainScreenPreviewProvider::class) uiState: ItemListScreenUiState) {
    MatsCounterTheme {
        MainScreen(
            modifier = Modifier.background(MatsCounterTheme.colors.background),
            navController = rememberNavController(),
            onItemSelected = { _, _ -> },
            onCountChange = {}
        )
    }
}
