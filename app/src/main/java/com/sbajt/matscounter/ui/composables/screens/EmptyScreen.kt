package com.sbajt.matscounter.ui.composables.screens

import android.R
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.sbajt.matscounter.ui.theme.FactoryTheme

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
                painter = painterResource(id = R.drawable.ic_delete),
                contentDescription = "empty_screen_illustration",
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 8.dp)
                    .size(128.dp)
            )
            val title by remember { mutableStateOf("Empty Screen") }
            Text(
                modifier = Modifier.padding(bottom = FactoryTheme.dimensions.medium),
                style = FactoryTheme.typography.titleTextNormal,
                color = FactoryTheme.colors.primary,
                text = title
            )
            val subTitle by remember { mutableStateOf("Error loading content data") }
            Text(
                style = FactoryTheme.typography.subtitleTextLarge,
                color = FactoryTheme.colors.secondary,
                text = subTitle
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun EmptyScreenPreview() {
    FactoryTheme {
        EmptyScreen(
            modifier = Modifier.background(FactoryTheme.colors.background)
        )
    }
}
