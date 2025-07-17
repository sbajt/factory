package com.sbajt.matscounter.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.sbajt.matscounter.ui.models.ItemGroupType
import com.sbajt.matscounter.ui.models.ItemUiState
import com.sbajt.matscounter.ui.theme.MatsCounterTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList

@Composable
fun ItemView(
    uiState: ItemUiState,
    onItemSelected: (String?, ItemGroupType?) -> Unit,
    modifier: Modifier = Modifier.Companion
) {
    Column(
        modifier = modifier
            .clickable(
                onClick = { onItemSelected(uiState.name, uiState.groupType) }
            )
            .clip(
                shape = RoundedCornerShape(2.dp)
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
    ) {
        AsyncImage(
            model = "file:///android_asset/images/${uiState.imageName}.jpg",
            contentDescription = uiState.name ?: "Item Icon",
            modifier = Modifier.size(60.dp),
            placeholder = painterResource(id = android.R.drawable.ic_menu_gallery),
            error = painterResource(id = android.R.drawable.ic_dialog_alert),
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
): ImmutableList<ItemUiState> {
    return List(count) { index ->
        ItemUiState(
            name = "Item $index",
            imageName = "ic_item_$index",
            groupType = ItemGroupType.entries[index % ItemGroupType.entries.size],
        )
    }.toPersistentList()
}
