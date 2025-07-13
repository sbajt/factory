package com.sbajt.matscounter.ui.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sbajt.matscounter.data.models.ItemGroupType
import com.sbajt.matscounter.ui.models.ItemUiState
import com.sbajt.matscounter.ui.theme.MatsCounterTheme

@Composable
fun ItemView(
    uiState: ItemUiState,
    onItemSelected: (String?, ItemGroupType?) -> Unit,
    modifier: Modifier = Modifier.Companion
) {
    Column(
        modifier = modifier.clickable(
            onClick = { onItemSelected(uiState.name, uiState.groupType) }
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            modifier = Modifier.border(
                width = 2.dp,
                color = Color.DarkGray,
                shape = RoundedCornerShape(size = 4.dp)
            ),
            painter = painterFromAssets(assetFileName = uiState.imageName),
            contentDescription = uiState.name ?: "Item Icon",
        )
        Text(text = remember { uiState.name ?: "" })
    }
}

@Preview(showBackground = true)
@Composable
fun ItemViewPreview() {
    MatsCounterTheme {
        ItemView(
            uiState = mockItemUiState(),
            onItemSelected = { _, _ -> },
        )
    }
}

fun mockEmptyItemUiState() = ItemUiState(
    name = null,
    imageName = null,
    groupType = null,
)

fun mockItemUiState() = ItemUiState(
    name = "Basic Material",
    imageName = "ic_item_0",
    groupType = ItemGroupType.entries[0],
)

fun mockItemUiStateList(
    count: Int = 10,
): List<ItemUiState> {
    return List(count) { index ->
        ItemUiState(
            name = "Item $index",
            imageName = "ic_item_$index",
            groupType = ItemGroupType.entries[index % ItemGroupType.entries.size],
        )
    }
}
