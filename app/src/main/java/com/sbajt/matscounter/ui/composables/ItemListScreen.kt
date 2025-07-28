package com.sbajt.matscounter.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.sbajt.matscounter.ui.mappers.getName
import com.sbajt.matscounter.ui.models.ItemGroupType
import com.sbajt.matscounter.ui.models.ItemUiState
import com.sbajt.matscounter.ui.theme.MatsCounterTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.launch

@Composable
fun ItemListScreen(
    uiState: ImmutableList<ItemUiState>,
    onItemSelected: OnItemSelected,
    modifier: Modifier = Modifier
) {
    val groupTypeList = uiState.distinctBy { it.groupType }.map { it.groupType } + ItemGroupType.ALL
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { groupTypeList.size })
    Column(modifier = modifier) {
        ScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
        ) {
            (groupTypeList).forEachIndexed { index, itemGroupType ->
                val text = itemGroupType.getName()
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = { scope.launch { pagerState.animateScrollToPage(index) } },
                    text = {
                        Text(
                            maxLines = 1,
                            text = text,
                        )
                    }
                )
            }
        }
        HorizontalPager(state = pagerState) { page ->
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 100.dp),
                contentPadding = PaddingValues(vertical = 18.dp)
            ) {
                val groupType = groupTypeList[page]
                val itemUiStatePage = uiState.filter {
                    it.groupType == groupType
                        || groupType == ItemGroupType.ALL
                }
                items(count = itemUiStatePage.size, key = { index -> index }) { index ->
                    ItemView(
                        uiState = itemUiStatePage[index],
                        onItemSelected = onItemSelected,
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
fun GridSectionPreview(@PreviewParameter(ItemListUiStateProvider::class) uiState: ImmutableList<ItemUiState>) {
    MatsCounterTheme {
        ItemListScreen(
            uiState = uiState,
            onItemSelected = { _, _ -> },
        )
    }
}


