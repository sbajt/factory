package com.sbajt.matscounter.ui.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.sbajt.matscounter.ui.models.ItemGroupType
import com.sbajt.matscounter.ui.models.MainScreenUiState
import com.sbajt.matscounter.ui.navigation.ItemBuildComponents
import com.sbajt.matscounter.ui.navigation.ItemDetails
import com.sbajt.matscounter.ui.navigation.ItemList
import com.sbajt.matscounter.ui.theme.MatsCounterTheme

typealias OnItemSelected = (String?, ItemGroupType?) -> Unit
typealias OnCountChange = (Int) -> Unit

@Composable
fun MainScreen(
    uiState: MainScreenUiState,
    onItemSelected: OnItemSelected,
    onCountChange: OnCountChange,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxSize(),
    ) {
        when (uiState) {
            is MainScreenUiState.Loading -> LoadingScreen()
            is MainScreenUiState.Empty -> EmptyScreen()
            is MainScreenUiState.Content -> ContentScreen(
                uiState = uiState,
                onItemSelected = onItemSelected,
                onCountChange = onCountChange
            )
        }
    }
}


@Composable
fun ContentScreen(
    uiState: MainScreenUiState.Content,
    onItemSelected: OnItemSelected,
    onCountChange: OnCountChange,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    Surface {
        NavHost(
            modifier = modifier,
            navController = navController,
            startDestination = ItemList
        ) {
            composable<ItemList> {
                if (uiState.itemUiStateList.isNotEmpty()) {
                    ItemListScree(
                        uiState = uiState.itemUiStateList,
                        onItemSelected = onItemSelected,
                    )
                } else {
                    EmptyScreen()
                }
            }
            composable<ItemDetails> {
                uiState.itemDetailsUiState?.let {
                    ItemDetailsScreen(
                        uiState = it,
                        onCountChange = onCountChange
                    )
                } ?: EmptyScreen()
            }
            composable<ItemBuildComponents> {
                uiState.itemBuildPathUiState?.let {
                    ItemBuildPathScreen(
                        uiState = it,
                    )
                } ?: EmptyScreen()
            }
        }
    }
    navController.graph = navController.createGraph(startDestination = ItemList) {
        composable<ItemList> {}
        composable<ItemDetails> {}
        composable<ItemBuildComponents> {}
    }
}

@PreviewLightDark
@Composable
fun MainScreenPreview(@PreviewParameter(MainScreenPreviewProvider::class) uiState: MainScreenUiState) {
    MatsCounterTheme {
        MainScreen(
            uiState = uiState,
            onItemSelected = { _, _ -> },
            onCountChange = {}
        )
    }
}
