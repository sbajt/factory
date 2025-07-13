package com.sbajt.matscounter.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sbajt.matscounter.ui.models.DescriptionSectionUiState
import com.sbajt.matscounter.ui.theme.MatsCounterTheme

@Composable
internal fun DescriptionSection(
    uiState: DescriptionSectionUiState,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterName(
                    resImageName = uiState.selectedItem?.imageName
                ),
                contentDescription = uiState.selectedItem?.name ?: "Item Icon",
            )
            Text(
                text = uiState.selectedItem?.name ?: "",
            )
        }
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
