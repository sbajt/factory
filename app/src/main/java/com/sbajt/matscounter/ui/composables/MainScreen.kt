package com.sbajt.matscounter.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.sbajt.matscounter.ui.models.DescriptionSectionUiState
import com.sbajt.matscounter.ui.models.InputSectionUiState
import com.sbajt.matscounter.ui.models.ItemGroupType
import com.sbajt.matscounter.ui.models.ItemUiState
import com.sbajt.matscounter.ui.models.MainScreenUiState
import com.sbajt.matscounter.ui.theme.MatsCounterTheme
import kotlinx.collections.immutable.toImmutableList

typealias OnItemSelected = (String?, ItemGroupType?) -> Unit
typealias OnCountChange = (Int) -> Unit

@Composable
fun MainScreen(
    uiState: MainScreenUiState,
    onItemSelected: OnItemSelected,
    onCountChange: OnCountChange,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxSize(),
    ) {
        when (uiState) {
            is MainScreenUiState.Loading -> LoadingScreen()
            is MainScreenUiState.Empty -> EmptyScreen()
            is MainScreenUiState.Content -> ContentScreen(
                descriptionUiState = uiState.descriptionUiState,
                inputSectionUiState = uiState.inputSectionUiState,
                itemUiStateList = uiState.itemUiStateList,
                onItemSelected = onItemSelected,
                onCountChange = onCountChange,
            )
        }
    }
}

@Composable
fun ContentScreen(
    descriptionUiState: DescriptionSectionUiState,
    inputSectionUiState: InputSectionUiState,
    itemUiStateList: List<ItemUiState>,
    onItemSelected: OnItemSelected,
    onCountChange: OnCountChange,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
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
            uiState = itemUiStateList.toImmutableList(),
            onItemSelected = onItemSelected,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview(@PreviewParameter(MainScreenPreviewProvider::class) uiState: MainScreenUiState) {
    MatsCounterTheme {
        MainScreen(
            uiState = uiState,
            onItemSelected = { _, _ -> },
            onCountChange = {}
        )
    }
}
