package com.sbajt.matscounter.ui.composables

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.ui.Modifier
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
    scrollableState: ScrollableState,
) = then(
    Modifier.drawWithContent {
        drawContent()

        if (length > 0.dp) {
            when (orientation) {
                Orientation.Horizontal -> {
                    drawLeftFadingEdge(color = color, length = length)
                    drawRightFadingEdge(color = color, length = length)
                }

                Orientation.Vertical -> {
                    drawTopFadingEdge(color = color, length = length)
                    drawBottomFadingEdge(color = color, length = length)
                }
            }
        }
    }
)

private fun ContentDrawScope.drawTopFadingEdge(
    color: Color,
    length: Dp,
) {
    drawRect(
        size = size.copy(height = length.toPx()),
        brush = Brush.verticalGradient(
            colors = listOf(Color.Transparent, color),
            startY = 0f,
            endY = length.toPx()
        ),
        blendMode = BlendMode.DstIn
    )
}

private fun ContentDrawScope.drawBottomFadingEdge(
    color: Color,
    length: Dp,
) {
    drawRect(
        topLeft = Offset(0f, size.height - length.toPx()),
        size = size.copy(height = length.toPx()),
        brush = Brush.verticalGradient(
            colors = listOf(Color.Transparent, color).reversed(),
            startY = size.height - length.toPx(),
            endY = size.height,
        ),
        blendMode = BlendMode.DstIn
    )
}

private fun ContentDrawScope.drawLeftFadingEdge(
    color: Color,
    length: Dp,
) {
    drawRect(
        size = size.copy(width = length.toPx()),
        brush = Brush.horizontalGradient(
            colors = listOf(Color.Transparent, color),
            startX = 0f,
            endX = length.toPx(),
        ),
        blendMode = BlendMode.DstIn
    )
}

private fun ContentDrawScope.drawRightFadingEdge(
    color: Color,
    length: Dp,
) {
    drawRect(
        topLeft = Offset( size.width - length.toPx(), 0f),
        size = size.copy(width =  length.toPx()),
        brush = Brush.horizontalGradient(
            colors = listOf(Color.Transparent, color).reversed(),
            startX = size.width - length.toPx(),
            endX = size.width,
        ),
        blendMode = BlendMode.DstIn
    )
}
