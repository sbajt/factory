package com.sbajt.matscounter.ui.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
fun BuildMaterialView(
    uiState: BuildingMaterialUiState,
    selectedItemCount: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp),
    ) {
        Text(
            fontFamily = FontFamily.SansSerif,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            text = remember { uiState.name ?: "" }
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
fun previewBuildingMaterialView(@PreviewParameter(BuildMaterialUiStateProvider::class) uiState: BuildingMaterialUiState) {
    BuildMaterialView(
        uiState = uiState,
        selectedItemCount = 0,
    )
}
