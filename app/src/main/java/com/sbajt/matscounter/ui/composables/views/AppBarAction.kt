package com.sbajt.matscounter.ui.composables.views

import android.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.sbajt.matscounter.ui.models.appBars.AppBarActionType

@Composable
fun AppBarAction(
    action: AppBarActionType,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AppBarActionType.entries.firstOrNull { it == action }?.apply {
        Image(
            modifier = modifier.clickable(enabled = true, onClick = onClick),
            painter = painterResource(id = R.drawable.ic_menu_directions),
            contentDescription = "",
        )
    }
}