package com.sbajt.matscounter.ui.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sbajt.matscounter.ui.models.DescriptionSectionUiState
import com.sbajt.matscounter.ui.theme.MatsCounterTheme

@Composable
fun DescriptionSection(
    uiState: DescriptionSectionUiState,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        ItemView(
            modifier = Modifier.padding(start = 8.dp, top = 8.dp),
            uiState = uiState.selectedItem,
            onItemSelected = { _, _ -> },
        )
        Text(
            text = remember(uiState.selectedItem?.name ?: "selectedItemKey") {
                uiState.selectedItem?.groupType?.name?.lowercase()
                    ?.replaceFirstChar { it.uppercase() }
                    ?.replace("_", " ")
                    ?: ""
            },
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DescriptionSectionPreview() {
    MatsCounterTheme {
        DescriptionSection(
            uiState = mockDescriptionSectionUiState(),
        )
    }
}

fun mockDescriptionSectionUiState() = DescriptionSectionUiState(
    selectedItem = mockItemUiState()
)
