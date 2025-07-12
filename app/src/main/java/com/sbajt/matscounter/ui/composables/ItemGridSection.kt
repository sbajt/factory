package com.sbajt.matscounter.ui.composables

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sbajt.matscounter.ui.models.ItemUiState
import com.sbajt.matscounter.ui.theme.MatsCounterTheme

@Composable
internal fun GridSection(
    uiState: List<ItemUiState>,
    onItemSelected: OnItemSelected,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Adaptive(minSize = 100.dp),
    ) {
        items(count = uiState.size) { index ->
            ItemView(
                uiState = uiState[index],
                onItemSelected = onItemSelected,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GridSectionPreview() {
    MatsCounterTheme {
        GridSection(
            uiState = mockItemUiStateList(),
            onItemSelected = { _, _ -> },
        )
    }
}


