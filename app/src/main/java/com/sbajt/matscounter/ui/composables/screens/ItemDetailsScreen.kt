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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sbajt.matscounter.ui.composables.previewProviders.ItemDetailsUiStateProvider
import com.sbajt.matscounter.ui.composables.views.BuildMaterialView
import com.sbajt.matscounter.ui.composables.views.InputSection
import com.sbajt.matscounter.ui.composables.views.ItemView
import com.sbajt.matscounter.ui.models.ItemGroupType
import com.sbajt.matscounter.ui.models.appBars.AppBarState
import com.sbajt.matscounter.ui.models.screens.ItemDetailsScreenUiState
import com.sbajt.matscounter.ui.models.views.InputSectionUiState
import com.sbajt.matscounter.ui.theme.FactoryTheme

typealias OnCountChange = (Int) -> Unit

@Composable
fun ItemDetailsScreen(
    uiState: ItemDetailsScreenUiState,
    onCountChange: OnCountChange,
    navController: NavHostController,
    onNavigate: OnNavigate,
    modifier: Modifier = Modifier,
) {
    ContentScreen(
        modifier = modifier,
        onCountChange = onCountChange,
        onNavigate = onNavigate,
        navController = navController,
        uiState = uiState,
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ContentScreen(
    uiState: ItemDetailsScreenUiState,
    onCountChange: OnCountChange,
    onNavigate: OnNavigate,
    navController: NavHostController,
    modifier: Modifier
) {
    ScaffoldLayout(
        modifier = modifier,
        appBarState = uiState.appBarState ?: AppBarState.Empty,
        navController = navController,
    ) { paddingValues ->
        Content(
            modifier = Modifier.padding(paddingValues),
            uiState = uiState,
            onCountChange = onCountChange,
            onNavigate = onNavigate,
        )
    }
}

@Composable
private fun Content(
    uiState: ItemDetailsScreenUiState,
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
                modifier = Modifier.padding(bottom = FactoryTheme.dimensions.medium),
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
                    onClick = { onNavigate.invoke() }
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
            navController = rememberNavController()
        )
    }
}
