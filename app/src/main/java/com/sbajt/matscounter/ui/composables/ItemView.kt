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
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.sbajt.matscounter.ui.mappers.getName
import com.sbajt.matscounter.ui.models.ItemGroupType
import com.sbajt.matscounter.ui.models.ItemUiState
import com.sbajt.matscounter.ui.theme.MatsCounterTheme

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
            contentDescription = remember { uiState?.name ?: "Item unknown" },
            placeholder = painterResource(id = R.drawable.ic_menu_gallery),
            error = painterResource(id = R.drawable.ic_dialog_alert),
            contentScale = ContentScale.FillHeight,
        )
        Text(
            textAlign = TextAlign.Center,
            fontFamily = FontFamily.SansSerif,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 12.sp,
            text = remember(key1 = uiState?.name.toString()) {
                uiState?.name ?: ""
            }
        )
        Text(
            textAlign = TextAlign.Center,
            fontFamily = FontFamily.SansSerif,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 10.sp,
            text = remember(key1 = uiState?.groupType.getName()) {
                uiState?.groupType?.getName() ?: ""
            }
        )
    }
}

@PreviewLightDark
@Composable
fun ItemViewPreview(@PreviewParameter(ItemUiStateProvider::class) uiState: ItemUiState) {
    MatsCounterTheme {
        ItemView(
            uiState = uiState,
            onItemSelected = { _, _ -> },
        )
    }
}
