package com.sbajt.matscounter.ui.composables

import androidx.collection.buildIntSet
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sbajt.matscounter.R
import com.sbajt.matscounter.ui.mappers.getName
import com.sbajt.matscounter.ui.models.ItemBuildPathUiState
import com.sbajt.matscounter.ui.theme.MatsCounterTheme

@Composable
fun ItemBuildPathScreen(
    uiState: ItemBuildPathUiState,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            ItemView(
                modifier = Modifier.padding(start = 8.dp, top = 8.dp),
                uiState = uiState.selectedItem,
                onItemSelected = { _, _ -> },
            )
        }

        if (uiState.buildPathList.isNotEmpty()) {
            uiState.buildPathList.forEach { buildMaterialList ->
                item("arrow_${buildMaterialList.groupType}") {
                    Image(
                        modifier = Modifier
                            .padding(4.dp)
                            .size(54.dp),
                        painter = painterResource(id = R.drawable.ic_arrow),
                        contentDescription = "Arrow icon",
                    )
                }
                item("group_type_${buildMaterialList.groupType}") {
                    Text(
                        modifier = Modifier.padding(bottom = 8.dp),
                        fontFamily = FontFamily.SansSerif,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 14.sp,
                        text = remember { buildMaterialList.groupType.getName() },
                    )
                }
                buildMaterialList.buildingMaterialsList.forEach { buildMaterial ->
                    item("item_${buildMaterial.name}_${buildMaterialList.groupType}") {
                        BuildMaterialView(
                            uiState = buildMaterial,
                            selectedItemCount = uiState.selectedItemCount,
                        )
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
fun MainScreenPreview(
    @PreviewParameter(ItemBuildPathUiStateProvider::class) uiState: ItemBuildPathUiState
) {
    MatsCounterTheme {
        ItemBuildPathScreen(uiState = uiState)
    }
}
