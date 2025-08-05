package com.sbajt.matscounter.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sbajt.matscounter.ui.models.InputSectionUiState
import com.sbajt.matscounter.ui.models.ItemDetailsScreenUiState
import com.sbajt.matscounter.ui.models.ItemGroupType
import com.sbajt.matscounter.ui.navigation.ItemBuildComponents
import com.sbajt.matscounter.ui.theme.MatsCounterTheme

@Composable
fun ItemDetailsScreen(
    uiState: ItemDetailsScreenUiState,
    navController: NavHostController,
    onCountChange: OnCountChange,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(modifier = Modifier.padding(start = 8.dp, top = 8.dp)) {
            ItemView(
                modifier = Modifier.padding(start = 8.dp, top = 8.dp),
                uiState = uiState.selectedItem,
                onItemSelected = { _, _ -> },
            )
            if (uiState.selectedItem != null && uiState.selectedItemAmount > 0) {
                InputSection(
                    modifier = Modifier.padding(start = 8.dp, top = 8.dp),
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
                    modifier = Modifier.padding(bottom = 8.dp),
                    fontFamily = FontFamily.SansSerif,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 14.sp,
                    text = remember { this?.titleText ?: "" },
                )
                if (this?.buildingMaterialsList?.isNotEmpty() == true) {
                    buildingMaterialsList.forEachIndexed { index, state ->
                        BuildMaterialView(
                            uiState = buildingMaterialsList[index],
                            selectedItemAmount = uiState.selectedItemAmount,
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
            ) {
                Button(
                    modifier = Modifier.padding(16.dp),
                    onClick = {
                        navController.navigate(ItemBuildComponents)
                    }
                ) {
                    Text(text = "Show build path")
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
fun DescriptionSectionPreview(@PreviewParameter(ItemDetailsUiStateProvider::class) uiState: ItemDetailsScreenUiState) {
    MatsCounterTheme {
        ItemDetailsScreen(
            uiState = uiState,
            navController = rememberNavController(),
            onCountChange = {}
        )
    }
}
