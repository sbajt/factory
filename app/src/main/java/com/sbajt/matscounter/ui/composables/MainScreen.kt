package com.sbajt.matscounter.ui.composables

import android.graphics.BitmapFactory
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.sbajt.matscounter.data.models.ItemGroupType
import com.sbajt.matscounter.ui.models.MainUiState
import com.sbajt.matscounter.ui.theme.MatsCounterTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.InputStream

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
fun assetImagePainter(assetImageName: String?): Painter {
    val context = LocalContext.current
    val placeholderPainter = painterResource(id = android.R.drawable.ic_menu_close_clear_cancel)
    var imageBitmap: ImageBitmap? by remember(assetImageName) { mutableStateOf(null) }
    var painter: Painter by remember(assetImageName) { mutableStateOf(placeholderPainter) }

    if (assetImageName == null) {
        return placeholderPainter
    }

    // LaunchedEffect to load the image asynchronously
    // It will re-launch if assetImageName or context changes
    LaunchedEffect(assetImageName, context) {
        imageBitmap = try {
            withContext(Dispatchers.IO) {
                val inputStream: InputStream = context.assets.open("images/$assetImageName.jpg")
                BitmapFactory.decodeStream(inputStream)?.asImageBitmap()
            }
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }

        painter = imageBitmap?.let { BitmapPainter(it) } ?: placeholderPainter
    }

    return painter
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
