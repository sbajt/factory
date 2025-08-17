package com.sbajt.matscounter.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.sbajt.matscounter.ui.theme.MatsCounterTheme

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(MatsCounterTheme.size.icon),
            trackColor = MatsCounterTheme.colors.secondary,
        )
    }
}

@PreviewLightDark
@Composable
private fun LoadingScreenPreview() {
    MatsCounterTheme {
        LoadingScreen(modifier = Modifier.background(MatsCounterTheme.colors.background))
    }
}


