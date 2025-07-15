package com.sbajt.matscounter.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sbajt.matscounter.ui.models.ItemGroupType
import com.sbajt.matscounter.ui.models.MainUiState
import com.sbajt.matscounter.ui.theme.MatsCounterTheme

typealias OnItemSelected = (String?, ItemGroupType?) -> Unit
typealias OnCountChange = (Int) -> Unit

@Composable
fun MainScreen(
    uiState: MainUiState,
    onItemSelected: OnItemSelected,
    onCountChange: OnCountChange,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        with(uiState) {
            DescriptionSection(
                modifier = Modifier.weight(0.1f),
                uiState = descriptionUiState,
            )
            inputSectionUiState.selectedItem?.name?.let {
                InputSection(
                    modifier = Modifier.weight(0.1f),
                    uiState = inputSectionUiState,
                    onCountChange = onCountChange,
                )
            }
            GridSection(
                modifier = Modifier.weight(0.8f),
                uiState = itemUiStateList,
                onItemSelected = onItemSelected,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MatsCounterTheme {
        MainScreen(
            uiState = MainUiState(
                descriptionUiState = mockDescriptionSectionUiState(),
                inputSectionUiState = mockInputSectionUiState(),
                itemUiStateList = mockItemUiStateList(),
            ),
            onItemSelected = { _, _ -> },
            onCountChange = {}
        )
    }
}
