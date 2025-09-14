package com.sbajt.matscounter.ui.composables.screens

import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.fadingEdge(
    color: Color,
    length: Dp,
    scrollableState: ScrollableState,
) = then(
    Modifier
        .drawWithContent {
            drawContent()

            if (
                length > 0.dp
                && (scrollableState.isScrollInProgress
                        || !scrollableState.canScrollForward
                        || !scrollableState.canScrollBackward)
            ) {
                // draw top fading edge
                drawRect(
                    size = size.copy(height = length.toPx()),
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, color),
                        startY = 0f,
                        endY = length.toPx()
                    ),
                    blendMode = BlendMode.DstIn
                )
                //draw bottom fading edge
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
        }
)

