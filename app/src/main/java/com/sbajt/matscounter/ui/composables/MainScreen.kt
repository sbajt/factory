package com.sbajt.matscounter.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sbajt.matscounter.ui.models.DescriptionSectionUiState
import com.sbajt.matscounter.ui.models.InputSectionUiState
import com.sbajt.matscounter.ui.models.ItemGroupType
import com.sbajt.matscounter.ui.models.MainUiState
import com.sbajt.matscounter.ui.theme.MatsCounterTheme
import kotlinx.collections.immutable.persistentListOf

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
                modifier = Modifier.weight(0.2f),
                uiState = descriptionUiState,
            )
            InputSection(
                modifier = Modifier.weight(0.2f),
                uiState = inputSectionUiState,
                onCountChange = onCountChange,
            )
            GridSection(
                modifier = Modifier.weight(0.6f),
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
                descriptionUiState = DescriptionSectionUiState(
                    selectedItem = null,
                ),
                inputSectionUiState = InputSectionUiState(
                    itemCount = 0
                ),
                itemUiStateList = persistentListOf()
            ),
            onItemSelected = { _, _ -> },
            onCountChange = {}
        )
    }
}
