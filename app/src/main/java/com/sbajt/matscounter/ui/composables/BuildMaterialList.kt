package com.sbajt.matscounter.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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
import com.sbajt.matscounter.ui.models.BuildMaterialListUiState

@Composable
fun BuildMaterialListView(
    uiState: BuildMaterialListUiState,
) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(
            modifier = Modifier.padding(bottom = 8.dp),
            fontFamily = FontFamily.SansSerif,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 14.sp,
            text = remember { uiState.titleText ?: "" },
        )
        if (uiState.buildingMaterialsList.isNotEmpty()) {
            uiState.buildingMaterialsList.forEachIndexed { index, state ->
                BuildMaterialView(
                    uiState = uiState.buildingMaterialsList[index],
                    selectedItemCount = uiState.selectedItemCount,
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
fun previewBuildingMaterialsListView(@PreviewParameter(BuildMaterialListUiStateProvider::class) uiState: BuildMaterialListUiState) {
    BuildMaterialListView(
        uiState = uiState,
    )
}

