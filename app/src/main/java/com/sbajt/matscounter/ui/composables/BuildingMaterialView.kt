package com.sbajt.matscounter.ui.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sbajt.matscounter.ui.models.BuildingMaterialUiState

@Composable
fun BuildingMaterialView(
    uiState: BuildingMaterialUiState,
    selectedItemCount: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.width(135.dp)
    ) {
        val nameText by remember(key1 = uiState.name ?: "") {
            mutableStateOf(uiState.name ?: "")
        }
        Text(
            fontFamily = FontFamily.SansSerif,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            text = nameText
        )
        if (uiState.count > 0) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.End,
                fontFamily = FontFamily.SansSerif,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                text = if (selectedItemCount > 0) {
                    "x${uiState.count * selectedItemCount}"
                } else {
                    "x${uiState.count}"
                }
            )
        }
    }
}

@PreviewLightDark
@Composable
fun previewBuildingMaterialView(@PreviewParameter(BuildingMaterialUiStateProvider::class) uiState: BuildingMaterialUiState) {
    BuildingMaterialView(
        uiState = uiState,
        selectedItemCount = 0,
    )
}
