package com.sbajt.matscounter.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sbajt.matscounter.data.models.ItemGroupType
import com.sbajt.matscounter.ui.models.MainUiState
import com.sbajt.matscounter.ui.theme.MatsCounterTheme

typealias OnItemSelected = (String?, ItemGroupType?) -> Unit

@Composable
internal fun MainScreen(
    uiState: MainUiState,
    onItemSelected: OnItemSelected,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        with(uiState) {
            DescriptionSection(
                modifier = Modifier.weight(1f),
                uiState = descriptionUiState,
            )
            InputSection(
                modifier = Modifier.weight(1f),
                uiState = inputSectionUiState
            )
            GridSection(
                modifier = Modifier.weight(1f),
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
            onItemSelected = { _, _ -> }
        )
    }
}
