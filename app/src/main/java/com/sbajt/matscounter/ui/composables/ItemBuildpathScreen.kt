package com.sbajt.matscounter.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import com.sbajt.matscounter.R
import com.sbajt.matscounter.ui.models.ItemBuildPathUiState
import com.sbajt.matscounter.ui.theme.MatsCounterTheme

@Composable
fun ItemBuildPathScreen(
    uiState: ItemBuildPathUiState,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier.padding(MatsCounterTheme.size.contentPadding),) {
        item {
            Row(
                modifier = Modifier
                    .background(MatsCounterTheme.colors.background)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(MatsCounterTheme.size.paddingMedium, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ItemView(
                    modifier = Modifier.padding(vertical = MatsCounterTheme.size.paddingMedium),
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

        if (uiState.buildPathList.isNotEmpty()) {
            uiState.buildPathList.fastForEachIndexed { index, buildMaterialList ->
                item("arrow_${index}_${buildMaterialList.groupType}") {
                    Arrow()
                }
                item("group_${index}_${buildMaterialList.groupType}_name") {
                    Text(
                        modifier = Modifier.padding(
                            start = MatsCounterTheme.size.paddingMedium,
                            bottom = MatsCounterTheme.size.paddingSmall
                        ),
                        style = MatsCounterTheme.typography.subtitleTextNormal,
                        color = MatsCounterTheme.colors.primary,
                        text = buildMaterialList.titleText?.let {
                            remember { it }
                        } ?: "",
                    )
                }
                buildMaterialList.buildMaterialsList.forEach { buildMaterial ->
                    item("group_${index}_item_${buildMaterial.name}_${buildMaterialList.groupType}") {
                        BuildMaterialView(
                            uiState = buildMaterial,
                            selectedItemAmount = uiState.selectedItemAmount,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Arrow(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(MatsCounterTheme.colors.background)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .padding(MatsCounterTheme.size.paddingSmall)
                .size(MatsCounterTheme.size.iconSmall),
            painter = painterResource(id = R.drawable.ic_arrow),
            contentDescription = "Arrow icon",
        )
    }
}

@PreviewLightDark
@Composable
fun MainScreenPreview(
    @PreviewParameter(ItemBuildPathUiStateProvider::class) uiState: ItemBuildPathUiState
) {
    MatsCounterTheme {
        ItemBuildPathScreen(
            modifier = Modifier.background(MatsCounterTheme.colors.background),
            uiState = uiState,
        )
    }
}
