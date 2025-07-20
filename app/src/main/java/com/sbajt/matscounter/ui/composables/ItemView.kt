package com.sbajt.matscounter.ui.composables

import android.R
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.sbajt.matscounter.ui.mappers.mapToName
import com.sbajt.matscounter.ui.models.BuildingMaterialUiState
import com.sbajt.matscounter.ui.models.ItemGroupType
import com.sbajt.matscounter.ui.models.ItemUiState
import com.sbajt.matscounter.ui.theme.MatsCounterTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList

@Composable
fun ItemView(
    uiState: ItemUiState?,
    onItemSelected: (String?, ItemGroupType?) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clickable(
                onClick = { onItemSelected(uiState?.name, uiState?.groupType) }
            )
            .padding(bottom = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        AsyncImage(
            modifier = Modifier
                .padding(4.dp)
                .clip(shape = RoundedCornerShape(16.dp))
                .size(80.dp),
            model = "file:///android_asset/images/${uiState?.imageName}.jpg",
            contentDescription = uiState?.name ?: "Item Icon",
            placeholder = painterResource(id = R.drawable.ic_menu_gallery),
            error = painterResource(id = R.drawable.ic_dialog_alert),
            contentScale = ContentScale.FillHeight,
        )
        Text(
            textAlign = TextAlign.Center,
            fontFamily = FontFamily.SansSerif,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 12.sp,
            text = remember(uiState?.name ?: "itemNameKey") {
                uiState?.name ?: ""
            }
        )
        Text(
            textAlign = TextAlign.Center,
            fontFamily = FontFamily.SansSerif,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 10.sp,
            text = remember(uiState?.groupType?.name ?: "itemDescriptionKey") {
                uiState?.groupType.mapToName()
            }
        )
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

fun mockEmptyItemUiState() = ItemUiState()

fun mockItemUiState() = ItemUiState(
    name = "Basic Material",
    imageName = "ic_drone",
    groupType = ItemGroupType.entries[0],
    buildingMaterials = mockBuildingMaterials(3)
)

fun mockItemUiStateList(
    count: Int = 10,
): ImmutableList<ItemUiState> = List(count) { index ->
    ItemUiState(
        name = "Item $index",
        imageName = "ic_item_$index",
        groupType = ItemGroupType.entries[index % ItemGroupType.entries.size],
        buildingMaterials = mockBuildingMaterials(3)
    )
}.toPersistentList()

fun mockBuildingMaterials(
    count: Int,
): ImmutableList<BuildingMaterialUiState> = List(count) {
    BuildingMaterialUiState(
        name = "Material $it",
        count = 1,
    )
}.toPersistentList()
