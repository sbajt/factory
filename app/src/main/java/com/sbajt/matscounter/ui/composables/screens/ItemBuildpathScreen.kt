package com.sbajt.matscounter.ui.composables.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.sbajt.matscounter.ui.composables.previewProviders.ItemBuildPathUiStateProvider
import com.sbajt.matscounter.ui.composables.views.Arrow
import com.sbajt.matscounter.ui.composables.views.BuildMaterialView
import com.sbajt.matscounter.ui.composables.views.ItemView
import com.sbajt.matscounter.ui.models.screens.ItemBuildPathScreenUiState
import com.sbajt.matscounter.ui.theme.MatsCounterTheme

@Composable
fun ItemBuildPathScreen(
    uiState: ItemBuildPathScreenUiState,
    modifier: Modifier = Modifier,
) {
    when (uiState) {
        ItemBuildPathScreenUiState.Empty -> EmptyScreen()
        ItemBuildPathScreenUiState.Loading -> LoadingScreen()
        is ItemBuildPathScreenUiState.Content -> ContentScreen(
            modifier = modifier,
            uiState = uiState,
        )

    }
}

@Composable
fun ContentScreen(
    uiState: ItemBuildPathScreenUiState.Content,
    modifier: Modifier,
) {
    val lazyListState = rememberLazyListState()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MatsCounterTheme.colors.background)
            .padding(MatsCounterTheme.dimensions.contentPadding)
            .fadingEdge(
                color = MatsCounterTheme.colors.fadingEdge,
                length = MatsCounterTheme.dimensions.fadingEdge,
                scrollableState = lazyListState,
            ),
        state = lazyListState
    ) {
        item {
            Row(
                modifier = modifier
                    .background(MatsCounterTheme.colors.background)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(
                    MatsCounterTheme.dimensions.medium,
                    Alignment.CenterHorizontally
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ItemView(
                    modifier = Modifier.padding(vertical = MatsCounterTheme.dimensions.medium),
                    uiState = uiState.selectedItem,
                    onItemSelected = { _, _ -> },
                )
                Text(
                    style = MatsCounterTheme.typography.subtitleTextNormal,
                    color = MatsCounterTheme.colors.primary,
                    text = remember { "x${uiState.selectedItemAmount}" },
                )
            }
        }

        if (uiState.selectedItemBuildMaterialListWrapperList.isNotEmpty()) {
            uiState.selectedItemBuildMaterialListWrapperList.forEachIndexed { index, buildMaterialListWrapper ->
                item {
                    Arrow()
                }
                item("group_$index") {
                    Text(
                        modifier = Modifier.padding(bottom = MatsCounterTheme.dimensions.small),
                        style = MatsCounterTheme.typography.titleTextNormal,
                        color = MatsCounterTheme.colors.primary,
                        text = buildMaterialListWrapper.titleText ?: ""
                    )
                }
                buildMaterialListWrapper.buildMaterialsList.forEach { buildMaterial ->
                    item("group_${index}_item_${buildMaterial.name}") {
                        BuildMaterialView(
                            modifier = Modifier.padding(start = MatsCounterTheme.dimensions.medium),
                            uiState = buildMaterial,
                        )
                    }
                }
            }
        }
        item(key = "bottom_space") {
            Spacer(
                modifier = Modifier
                    .height(MatsCounterTheme.dimensions.large)
            )
        }
    }
}

@PreviewLightDark
@Composable
fun MainScreenPreview(
    @PreviewParameter(ItemBuildPathUiStateProvider::class) uiState: ItemBuildPathScreenUiState
) {
    MatsCounterTheme {
        ItemBuildPathScreen(uiState = uiState)
    }
}
