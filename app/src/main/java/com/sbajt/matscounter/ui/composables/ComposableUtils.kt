package com.sbajt.matscounter.ui.composables

import androidx.compose.foundation.gestures.Orientation
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
): Modifier {
    val colorList = listOf(Color.Transparent, color)
    return drawWithContent {
        drawContent()
        if (length > 0.dp) {
            when (orientation) {
                Orientation.Horizontal -> {
                    drawLeftFadingEdge(colorList = colorList, length = length)
                    drawRightFadingEdge(colorList = colorList, length = length)
                }

                Orientation.Vertical -> {
                    drawTopFadingEdge(colorList = colorList, length = length)
                    drawBottomFadingEdge(colorList = colorList, length = length)
                }
            }
        }
    }
}

private fun ContentDrawScope.drawTopFadingEdge(
    colorList: List<Color>,
    length: Dp,
) {
    drawRect(
        size = size.copy(height = length.toPx()),
        brush = Brush.verticalGradient(
            colors = colorList,
            startY = 0f,
            endY = length.toPx(),
        ),
        blendMode = BlendMode.DstIn
    )
}

private fun ContentDrawScope.drawBottomFadingEdge(
    colorList: List<Color>,
    length: Dp,
) {
    drawRect(
        topLeft = Offset(0f, size.height - length.toPx()),
        size = size.copy(height = length.toPx()),
        brush = Brush.verticalGradient(
            colors = colorList,
            startY = size.height,
            endY = size.height - length.toPx(),
        ),
        blendMode = BlendMode.DstIn
    )
}

private fun ContentDrawScope.drawLeftFadingEdge(
    colorList: List<Color>,
    length: Dp,
) {
    drawRect(
        size = size.copy(width = length.toPx()),
        brush = Brush.horizontalGradient(
            colors = colorList,
            startX = 0f,
            endX = length.toPx(),
        ),
        blendMode = BlendMode.DstIn
    )
}

private fun ContentDrawScope.drawRightFadingEdge(
    colorList: List<Color>,
    length: Dp,
) {
    drawRect(
        topLeft = Offset(size.width - length.toPx(), 0f),
        size = size.copy(width = length.toPx()),
        brush = Brush.horizontalGradient(
            colors = colorList,
            startX = size.width,
            endX = size.width - length.toPx(),
        ),
        blendMode = BlendMode.DstIn
    )
}

