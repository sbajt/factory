package com.sbajt.matscounter.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.sbajt.matscounter.ui.models.DescriptionSectionUiState
import com.sbajt.matscounter.ui.models.ItemGroupType
import com.sbajt.matscounter.ui.models.ItemUiState
import com.sbajt.matscounter.ui.theme.MatsCounterTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@Composable
fun DescriptionSection(
    uiState: DescriptionSectionUiState,
    itemUiStatList: List<ItemUiState>,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.Start) {
        ItemView(
            modifier = Modifier.padding(start = 8.dp, top = 8.dp),
            uiState = uiState.selectedItem,
            onItemSelected = { _, _ -> },
        )
        if (uiState.selectedItem != null) {
            MaterialsListView(
                buildingMaterialsList = uiState.selectedItem.buildingMaterials,
                selectedItemCount = uiState.selectedItemCount,
                titleText = "Materials:"
            )
            MaterialsListView(
                buildingMaterialsList = uiState.selectedItem.mapToBasicMaterialList(itemUiStatList),
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
                        modifier = Modifier.width(120.dp)
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

private fun ItemUiState.mapToBasicMaterialList(itemUiStatList: List<ItemUiState>): ImmutableList<BuildingMaterialUiState> {
    val basicMaterialsMap = mutableMapOf<String, Int>()
    processItem(this, itemUiStatList, basicMaterialsMap)
    return basicMaterialsMap.map {
        BuildingMaterialUiState(
            name = it.key,
            count = it.value,
        )
    }.toImmutableList()
}

private fun processItem(item: ItemUiState, itemUiStatList: List<ItemUiState>, basicMaterialsMap: MutableMap<String, Int>) {
    item.buildingMaterials.forEach { buildingMaterial ->
        processBuildingMaterial(
            uiState = buildingMaterial,
            itemUiStatList = itemUiStatList,
            basicMaterialsMap = basicMaterialsMap
        )
    }
}

private fun processBuildingMaterial(
    uiState: BuildingMaterialUiState,
    itemUiStatList: List<ItemUiState>,
    basicMaterialsMap: MutableMap<String, Int>
) {
    val itemUiState = itemUiStatList.find { it.name == uiState.name }
    when {
        itemUiState?.groupType == ItemGroupType.BASIC_MATERIAL -> {
            basicMaterialsMap[uiState.name!!] = (basicMaterialsMap[uiState.name] ?: 0) + uiState.count
        }
        itemUiState != null -> {
            processItem(itemUiState, itemUiStatList, basicMaterialsMap)
        }
    }
}

@PreviewLightDark
@Composable
fun DescriptionSectionPreview(@PreviewParameter(DescriptionSectionUiStateProvider::class) uiState: DescriptionSectionUiState) {
    MatsCounterTheme {
        DescriptionSection(
            uiState = uiState,
            itemUiStatList = persistentListOf(),
        )
    }
}
