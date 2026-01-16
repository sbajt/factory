package com.sbajt.matscounter.ui.composables

import android.R
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.fadingEdge(
    color: Color,
    length: Dp,
    orientation: Orientation,
) = composed {
    val colorList = listOf(Color.Transparent, color)
    val brush = remember(color, length) { Brush.verticalGradient(
        colors = colorList,
    ) }
    drawWithContent {
        drawContent()
        if (length > 0.dp) {
            when (orientation) {
                Orientation.Horizontal -> {
                    drawLeftFadingEdge(brush = brush, length = length)
                    drawRightFadingEdge(brush = brush, length = length)
                }

                Orientation.Vertical -> {
                    drawTopFadingEdge(brush = brush, length = length)
                    drawBottomFadingEdge(brush = brush, length = length)
                }
            }
        }
    }
}

private fun ContentDrawScope.drawTopFadingEdge(
    brush: Brush,
    length: Dp,
) {
    drawRect(
        size = size.copy(height = length.toPx()),
        brush = brush,
        blendMode = BlendMode.DstIn
    )
}

private fun ContentDrawScope.drawBottomFadingEdge(
    brush: Brush,
    length: Dp,
) {
    drawRect(
        topLeft = Offset(0f, size.height - length.toPx()),
        size = size.copy(height = length.toPx()),
        brush = brush,
        blendMode = BlendMode.DstIn
    )
}

private fun ContentDrawScope.drawLeftFadingEdge(
    brush: Brush,
    length: Dp,
) {
    drawRect(
        size = size.copy(width = length.toPx()),
        brush = brush,
        blendMode = BlendMode.DstIn
    )
}

private fun ContentDrawScope.drawRightFadingEdge(
    brush: Brush,
    length: Dp,
) {
    drawRect(
        topLeft = Offset(size.width - length.toPx(), 0f),
        size = size.copy(width = length.toPx()),
        brush = brush,
        blendMode = BlendMode.DstIn
    )
}

