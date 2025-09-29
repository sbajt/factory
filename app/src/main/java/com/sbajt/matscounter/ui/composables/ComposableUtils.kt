package com.sbajt.matscounter.ui.composables

import android.content.res.Resources
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sbajt.matscounter.ui.theme.FactoryTheme

fun Modifier.fadingEdge(
    color: Color,
    length: Dp,
    orientation: Orientation,
    scrollableState: ScrollableState,
): Modifier {
    val colorList = listOf(Color.Transparent, color)
    return then(
        Modifier
            .alpha(0.99f)
            .drawWithContent {
                drawIntoCanvas {
                    drawContent()
                    if (length > 0.dp) {
                        Log.d("FadingEdge", "$colorList")
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
    )
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
    Log.d("FadingEdge", "$colorList")
    drawRect(
        topLeft = Offset(0f, size.height - length.toPx()),
        size = size.copy(height = length.toPx()),
        brush = Brush.verticalGradient(
            colors = colorList.reversed(),
            startY = size.height - length.toPx(),
            endY = size.height,
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
            colors = colorList.reversed(),
            startX = size.width - length.toPx(),
            endX = size.width,
        ),
        blendMode = BlendMode.DstIn
    )
}

@Composable
fun Modifier.verticalScrollbar(
    state: LazyListState,
    color: Color = FactoryTheme.colors.onSurface,
    width: Dp = FactoryTheme.dimensions.medium,
    listBottomPadding: Dp = 0.dp,
): Modifier {
    val targetAlpha = if (state.isScrollInProgress) 1f else 0f
    val duration = if (state.isScrollInProgress) 150 else 500

    val alpha by animateFloatAsState(
        targetValue = targetAlpha,
        animationSpec = tween(durationMillis = duration)
    )

    return drawWithContent {
        drawContent()

        val firstVisibleElementIndex = state.layoutInfo.visibleItemsInfo.firstOrNull()?.index
        val needDrawScrollbar = state.isScrollInProgress || alpha > 0.0f

        if (needDrawScrollbar && firstVisibleElementIndex != null) {
            val listItemHeight = if(!state.canScrollForward) {
                state.layoutInfo.viewportSize.height.toFloat() /
                    (state.layoutInfo.totalItemsCount.toFloat())
            } else {
                (state.layoutInfo.viewportSize.height.toFloat() - listBottomPadding.toPx())  /
                    (state.layoutInfo.totalItemsCount.toFloat())
            }
            val scrollbarOffsetY = firstVisibleElementIndex * listItemHeight
            val scrollbarHeight = state.layoutInfo.visibleItemsInfo.size * listItemHeight

            drawRect(
                color = color,
                topLeft = Offset(this.size.width - width.toPx(), scrollbarOffsetY),
                size = Size(width.toPx(), scrollbarHeight),
                alpha = alpha
            )
        }
    }
}
