package com.sbajt.matscounter.ui.composables.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.sbajt.matscounter.R
import com.sbajt.matscounter.ui.theme.MatsCounterTheme

@Composable
fun Arrow(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(MatsCounterTheme.colors.background)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .padding(MatsCounterTheme.dimensions.small)
                .size(MatsCounterTheme.dimensions.iconSmall),
            painter = painterResource(id = R.drawable.ic_arrow),
            contentDescription = "Arrow icon",
        )
    }
}
