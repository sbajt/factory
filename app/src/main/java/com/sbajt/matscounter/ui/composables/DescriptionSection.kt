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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sbajt.matscounter.ui.models.DescriptionSectionUiState
import com.sbajt.matscounter.ui.theme.MatsCounterTheme

@Composable
fun DescriptionSection(
    uiState: DescriptionSectionUiState,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.Start) {
        ItemView(
            modifier = Modifier.padding(start = 8.dp, top = 8.dp),
            uiState = uiState.selectedItem,
            onItemSelected = { _, _ -> },
        )
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                fontFamily = FontFamily.SansSerif,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 14.sp,
                text = remember { "Materials:" },
            )
            if (uiState.selectedItem?.buildingMaterials?.isNotEmpty() == true) {
                LazyColumn {
                    items(
                        count = uiState.selectedItem.buildingMaterials.size,
                        key = { index -> uiState.selectedItem.buildingMaterials[index].name.toString() }
                    ) { index ->
                        BuildingMaterialView(
                            uiState = uiState.selectedItem.buildingMaterials[index],
                            selectedItemCount = uiState.selectedItemCount,
                            modifier = Modifier.width(120.dp)
                        )
                    }
                }
            } else {
                noMaterialsView()
            }
        }
    }
}

@Composable
private fun noMaterialsView() {
    Text(
        fontFamily = FontFamily.SansSerif,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        fontSize = 12.sp,
        text = remember { "No materials" },
    )
}

@PreviewLightDark
@Composable
fun DescriptionSectionPreview(@PreviewParameter(DescriptionSectionUiStateProvider::class) uiState: DescriptionSectionUiState) {
    MatsCounterTheme {
        DescriptionSection(
            uiState = uiState
        )
    }
}
