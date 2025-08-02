package com.sbajt.matscounter.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sbajt.matscounter.ui.models.InputSectionUiState
import com.sbajt.matscounter.ui.models.ItemDetailsScreenUiState
import com.sbajt.matscounter.ui.navigation.ItemBuildComponents
import com.sbajt.matscounter.ui.theme.MatsCounterTheme

@Composable
fun ItemDetailsScreen(
    uiState: ItemDetailsScreenUiState,
    navController: NavHostController,
    onCountChange: OnCountChange,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(modifier = Modifier.padding(start = 8.dp, top = 8.dp)) {
            ItemView(
                modifier = Modifier.padding(start = 8.dp, top = 8.dp),
                uiState = uiState.selectedItem,
                onItemSelected = { _, _ -> },
            )
            if (uiState.selectedItem != null && uiState.selectedItemCount > 0) {
                InputSection(
                    modifier = Modifier.padding(start = 8.dp, top = 8.dp),
                    uiState = InputSectionUiState(
                        selectedItem = uiState.selectedItem,
                        itemCount = uiState.selectedItemCount,
                    ),
                    onCountChange = onCountChange
                )
            }
        }
        uiState.itemBuildMaterialsUiState?.let {
            BuildMaterialListView(
                uiState = it,
            )
        }
        uiState.itemBasicBuildMaterialsUiState?.let {
            BuildMaterialListView(
                uiState = it,
            )
        }
        Button(
            modifier = Modifier.padding(16.dp),
            onClick = {
                navController.navigate(ItemBuildComponents)
            }
        ) {
            Text(text = "Show build path")
        }
    }
}

@PreviewLightDark
@Composable
fun DescriptionSectionPreview(@PreviewParameter(ItemDetailsUiStateProvider::class) uiState: ItemDetailsScreenUiState) {
    MatsCounterTheme {
        ItemDetailsScreen(
            uiState = uiState,
            navController = rememberNavController(),
            onCountChange = {}
        )
    }
}
