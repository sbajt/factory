package com.sbajt.matscounter.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.sbajt.matscounter.ui.theme.MatsCounterTheme

@Composable
fun EmptyScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = modifier.wrapContentWidth(align = Alignment.CenterHorizontally),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = android.R.drawable.ic_delete),
                contentDescription = "empty_screen_illustration",
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 8.dp)
                    .size(128.dp)
            )
            Text(
                modifier = Modifier.padding(bottom = MatsCounterTheme.size.paddingSmall),
                style = MatsCounterTheme.typography.titleTextNormal,
                color = MatsCounterTheme.colors.primary,
                text = remember { "Empty Screen" }
            )
            Text(
                style = MatsCounterTheme.typography.subtitleTextLarge,
                color = MatsCounterTheme.colors.secondary,
                text = remember { "Error loading content data" }
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun EmptyScreenPreview() {
    MatsCounterTheme {
        EmptyScreen(
            modifier = Modifier.background(MatsCounterTheme.colors.background)
        )
    }
}
