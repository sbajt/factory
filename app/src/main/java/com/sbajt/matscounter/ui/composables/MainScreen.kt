package com.sbajt.matscounter.ui.composables

import android.content.res.Resources
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
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
                uiState = descriptionUiState
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

@Composable
fun painterName(resImageName: String?, resources: Resources): Painter {
    if (resImageName == null) {
        return painterResource(id = android.R.drawable.ic_menu_help)
    }
    val resId: Int = resources.getIdentifier(resImageName, "drawable", null)
    return if (resId == 0) {
        painterResource(id = android.R.drawable.ic_menu_help)
    } else {
        painterResource(id = 0)
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
