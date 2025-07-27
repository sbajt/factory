package com.sbajt.matscounter.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sbajt.matscounter.ui.models.BuildingMaterialUiState
import com.sbajt.matscounter.ui.models.ItemDetailsScreenUiState
import com.sbajt.matscounter.ui.theme.MatsCounterTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@Composable
fun ItemDetailsScreen(
    uiState: ItemDetailsScreenUiState,
    onCountChange: OnCountChange,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(modifier = modifier, horizontalArrangement = Arrangement.Start) {
            ItemView(
                modifier = Modifier.padding(start = 8.dp, top = 8.dp),
                uiState = uiState.selectedItem,
                onItemSelected = { _, _ -> },
            )
            MaterialsListView(
                buildingMaterialsList = uiState.selectedItem?.buildingMaterials
                    ?: persistentListOf(),
                selectedItemCount = uiState.selectedItemCount,
                titleText = "Materials:"
            )
            MaterialsListView(
                buildingMaterialsList = uiState.buildingMaterialList.toImmutableList(),
                selectedItemCount = uiState.selectedItemCount,
                titleText = "Basic materials:"
            )
        }
    }
}

@Composable
private fun MaterialsListView(
    buildingMaterialsList: ImmutableList<BuildingMaterialUiState>,
    selectedItemCount: Int,
    titleText: String,
) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(
            fontFamily = FontFamily.SansSerif,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 14.sp,
            text = remember { titleText },
        )
        if (buildingMaterialsList.isNotEmpty()) {
            LazyColumn {
                items(
                    count = buildingMaterialsList.size,
                    key = { index -> buildingMaterialsList[index].name.toString() }
                ) { index ->
                    BuildingMaterialView(
                        uiState = buildingMaterialsList[index],
                        selectedItemCount = selectedItemCount,
                    )
                }
            }
        } else {
            NoMaterialsView()
        }
    }
}

@Composable
private fun NoMaterialsView() {
    Text(
        fontFamily = FontFamily.SansSerif,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        fontSize = 12.sp,
        text = remember { "No materials" },
    )
}

@PreviewLightDark
@Composable
fun DescriptionSectionPreview(@PreviewParameter(ItemDetailsUiStateProvider::class) uiState: ItemDetailsScreenUiState) {
    MatsCounterTheme {
        ItemDetailsScreen(
            uiState = uiState,
            onCountChange = {}
        )
    }
}
