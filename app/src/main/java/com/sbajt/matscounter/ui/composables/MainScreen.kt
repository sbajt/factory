package com.sbajt.matscounter.ui.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.navigation.NavHostController
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
    navController: NavHostController,
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
                navController = navController,
                onItemSelected = onItemSelected,
                onCountChange = onCountChange
            )
        }
    }
}


@Composable
fun ContentScreen(
    uiState: MainScreenUiState.Content,
    navController: NavHostController,
    onItemSelected: OnItemSelected,
    onCountChange: OnCountChange,
    modifier: Modifier = Modifier
) {
    Surface {
        NavHost(
            modifier = modifier,
            navController = navController,
            startDestination = ItemList
        ) {

            composable<ItemList> {
                ShowContentOrEmpty(uiState.itemUiStateList) { itemList ->
                    ItemListScreen(
                        uiState = itemList,
                        onItemSelected = onItemSelected,
                    )
                }
            }
            composable<ItemDetails> {
                ShowContentOrEmpty(uiState.itemDetailsUiState) { detailsUiState ->
                    ItemDetailsScreen(
                        uiState = detailsUiState,
                        navController = navController, // todo fix this
                        onCountChange = onCountChange
                    )
                }
            }
            composable<ItemBuildComponents> {
                ShowContentOrEmpty(uiState.itemBuildPathUiState) { buildPathUiState ->
                    ItemBuildPathScreen(
                        uiState = buildPathUiState
                    )
                }
            }
        }
    }
}

@Composable
inline fun <T> ShowContentOrEmpty(data: T?, crossinline content: @Composable (T) -> Unit) {
    if (data != null) {
        content(data)
    } else {
        EmptyScreen()
    }
}



@PreviewLightDark
@Composable
fun MainScreenPreview(@PreviewParameter(MainScreenPreviewProvider::class) uiState: MainScreenUiState) {
    MatsCounterTheme {
        MainScreen(
            uiState = uiState,
            navController = rememberNavController(),
            onItemSelected = { _, _ -> },
            onCountChange = {}
        )
    }
}
