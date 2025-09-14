package com.sbajt.matscounter.ui.composables.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.sbajt.matscounter.ui.composables.previewProviders.ItemDetailsUiStateProvider
import com.sbajt.matscounter.ui.composables.views.BuildMaterialView
import com.sbajt.matscounter.ui.composables.views.InputSection
import com.sbajt.matscounter.ui.composables.views.ItemView
import com.sbajt.matscounter.ui.models.ItemGroupType
import com.sbajt.matscounter.ui.models.screens.ItemDetailsScreenUiState
import com.sbajt.matscounter.ui.models.views.InputSectionUiState
import com.sbajt.matscounter.ui.theme.FactoryTheme

@Composable
fun ItemDetailsScreen(
    uiState: ItemDetailsScreenUiState,
    onCountChange: OnCountChange,
    onNavigate: OnNavigate,
    modifier: Modifier = Modifier,
) {
    when (uiState) {
        ItemDetailsScreenUiState.Empty -> EmptyScreen()
        ItemDetailsScreenUiState.Loading -> LoadingScreen()
        is ItemDetailsScreenUiState.Content -> ContentScreen(
            modifier = modifier,
            onCountChange = onCountChange,
            onNavigate = onNavigate,
            uiState = uiState,
        )

    }
}

@Composable
private fun ContentScreen(
    uiState: ItemDetailsScreenUiState.Content,
    onCountChange: OnCountChange,
    onNavigate: OnNavigate,
    modifier: Modifier
) = Column(
    modifier = modifier
        .fillMaxSize()
        .background(FactoryTheme.colors.background)
        .padding(FactoryTheme.dimensions.contentPadding)
) {
    Row(modifier = Modifier.padding(FactoryTheme.dimensions.medium)) {
        ItemView(
            modifier = Modifier.padding(FactoryTheme.dimensions.medium),
            uiState = uiState.selectedItem,
            onItemSelected = { _, _ -> },
        )
        if (uiState.selectedItem != null && uiState.selectedItemAmount > 0) {
            InputSection(
                modifier = Modifier.padding(FactoryTheme.dimensions.medium),
                uiState = InputSectionUiState(
                    selectedItem = uiState.selectedItem,
                    itemCount = uiState.selectedItemAmount,
                ),
                onCountChange = onCountChange
            )
        }
    }
    if (uiState.selectedItem?.groupType != ItemGroupType.BASIC_MATERIAL) {
        with(uiState.selectedItemBuildMaterialListWrapper) {
            Text(
                modifier = Modifier.padding(bottom = FactoryTheme.dimensions.small),
                style = FactoryTheme.typography.titleTextNormal,
                color = FactoryTheme.colors.primary,
                text = remember { mutableStateOf(this?.titleText ?: "") }.value,
            )
            if (this?.buildMaterialsList?.isNotEmpty() == true) {
                buildMaterialsList.forEach {
                    BuildMaterialView(
                        modifier = Modifier.padding(start = FactoryTheme.dimensions.medium),
                        uiState = it
                    )
                }
            }
        }
        if (uiState.selectedItem != null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = FactoryTheme.dimensions.large)
            ) {
                Button(
                    modifier = Modifier.padding(FactoryTheme.dimensions.contentPadding),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = FactoryTheme.colors.secondary
                    ),
                    shape = FactoryTheme.shape.button,
                    onClick = {
                        onNavigate.invoke()
                    }
                ) {
                    Text(
                        style = FactoryTheme.typography.subtitleTextNormal,
                        color = FactoryTheme.colors.primary,
                        text = "Show build path"
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
fun DescriptionSectionPreview(@PreviewParameter(ItemDetailsUiStateProvider::class) uiState: ItemDetailsScreenUiState) {
    FactoryTheme {
        ItemDetailsScreen(
            uiState = uiState,
            onCountChange = {},
            onNavigate = {},
        )
    }
}
