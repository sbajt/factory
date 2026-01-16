package com.sbajt.matscounter.ui.composables.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.sbajt.matscounter.ui.composables.fadingEdge
import com.sbajt.matscounter.ui.composables.previewProviders.ItemBuildPathUiStateProvider
import com.sbajt.matscounter.ui.composables.views.Arrow
import com.sbajt.matscounter.ui.composables.views.BuildMaterialView
import com.sbajt.matscounter.ui.composables.views.ItemView
import com.sbajt.matscounter.ui.models.appBars.AppBarState
import com.sbajt.matscounter.ui.models.screens.ItemBuildPathScreenUiState
import com.sbajt.matscounter.ui.theme.FactoryTheme

@Composable
fun ItemBuildPathScreen(
    uiState: ItemBuildPathScreenUiState,
    modifier: Modifier = Modifier,
) {
    ContentScreen(
        modifier = modifier,
        uiState = uiState,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ContentScreen(
    uiState: ItemBuildPathScreenUiState,
    modifier: Modifier
) {
    ScaffoldLayout(
        modifier = modifier,
        appBarState = uiState.appBarState ?: AppBarState.Empty,
    ) { paddingValues ->
        Box(
            modifier = Modifier.padding(top = paddingValues.calculateTopPadding())
        ) {
            Content(
                modifier = Modifier,
                uiState = uiState,
            )
        }
    }
}

@Composable
private fun Content(
    uiState: ItemBuildPathScreenUiState,
    modifier: Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(FactoryTheme.colors.background)
            .fadingEdge(
                color = FactoryTheme.colors.fadingEdge,
                length = FactoryTheme.dimensions.fadingEdge,
                orientation = Orientation.Vertical,
            )
    ) {
        val lazyListState = rememberLazyListState()
        LazyColumn(
            state = lazyListState,
        ) {
            item {
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(
                        FactoryTheme.dimensions.medium, Alignment.CenterHorizontally
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ItemView(
                        modifier = Modifier.padding(vertical = FactoryTheme.dimensions.medium),
                        uiState = uiState.selectedItem,
                        onItemSelected = { _, _ -> },
                    )
                    Text(
                        style = FactoryTheme.typography.subtitleTextNormal,
                        color = FactoryTheme.colors.primary,
                        text = "x${uiState.selectedItemAmount}",
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
                            modifier = Modifier.padding(
                                bottom = FactoryTheme.dimensions.medium,
                                start = FactoryTheme.dimensions.contentPadding,
                                end = FactoryTheme.dimensions.contentPadding
                            ),
                            style = FactoryTheme.typography.titleTextNormal,
                            color = FactoryTheme.colors.primary,
                            text = buildMaterialListWrapper.titleText ?: ""
                        )
                    }
                    buildMaterialListWrapper.buildMaterialsList.forEach { buildMaterial ->
                        item("group_${index}_item_${buildMaterial.name}") {
                            BuildMaterialView(
                                modifier = Modifier.padding(horizontal = FactoryTheme.dimensions.contentPadding),
                                uiState = buildMaterial,
                            )
                        }
                    }
                }
            }
            item(key = "bottom_space") {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(FactoryTheme.dimensions.large)
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
fun MainScreenPreview(
    @PreviewParameter(ItemBuildPathUiStateProvider::class) uiState: ItemBuildPathScreenUiState
) {
    FactoryTheme {
        ItemBuildPathScreen(
            uiState = uiState,
        )
    }
}
