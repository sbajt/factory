package com.sbajt.matscounter.ui.composables.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.sbajt.matscounter.ui.composables.previewProviders.ItemListUiStateProvider
import com.sbajt.matscounter.ui.composables.views.ItemView
import com.sbajt.matscounter.ui.mappers.getName
import com.sbajt.matscounter.ui.models.ItemGroupType
import com.sbajt.matscounter.ui.models.screens.ItemListScreenUiState
import com.sbajt.matscounter.ui.theme.MatsCounterTheme
import kotlinx.coroutines.launch

@Composable
fun ItemListScreen(
    uiState: ItemListScreenUiState,

    onItemSelected: OnItemSelected,
    modifier: Modifier = Modifier
) {
    when (uiState) {
        ItemListScreenUiState.Empty -> EmptyScreen()
        ItemListScreenUiState.Loading -> LoadingScreen()
        is ItemListScreenUiState.Content -> ContentScreen(
            modifier = modifier,
            uiState = uiState,
            onItemSelected = onItemSelected,
        )

    }
}

@Composable
private fun ContentScreen(
    uiState: ItemListScreenUiState.Content,
    onItemSelected: OnItemSelected,
    modifier: Modifier = Modifier,
) {
    val groupTypeList = uiState.itemUiStateList.distinctBy { it.groupType }.map { it.groupType } + ItemGroupType.ALL
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { groupTypeList.size })
    Column(
        modifier = modifier
            .background(MatsCounterTheme.colors.background)
            .padding(MatsCounterTheme.size.contentPadding),
    ) {
        ScrollableTabRow(
            modifier = Modifier.fillMaxWidth(),
            containerColor = MatsCounterTheme.colors.background,
            selectedTabIndex = pagerState.currentPage,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                    height = MatsCounterTheme.size.tab,
                    color = MatsCounterTheme.colors.accent
                )
            }
        ) {
            (groupTypeList).forEachIndexed { index, itemGroupType ->
                val text = itemGroupType.getName()
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = { scope.launch { pagerState.animateScrollToPage(index) } },
                    text = {
                        Text(
                            style = MatsCounterTheme.typography.bodyTextNormal,
                            color = MatsCounterTheme.colors.primary,
                            maxLines = 1,
                            text = text,
                        )
                    }
                )
            }
        }
        HorizontalPager(
            verticalAlignment = Alignment.Top,
            state = pagerState
        ) { page ->
            val groupType = groupTypeList[page]
            val itemUiStatePage by remember {
                mutableStateOf(uiState.itemUiStateList.filter { it.groupType == groupType || groupType == ItemGroupType.ALL })
            }
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                columns = GridCells.Adaptive(minSize = 100.dp),
                contentPadding = PaddingValues(vertical = 18.dp),
                verticalArrangement = Arrangement.spacedBy(MatsCounterTheme.size.medium),
            ) {
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
fun GridSectionPreview(@PreviewParameter(ItemListUiStateProvider::class) uiState: ItemListScreenUiState) {
    MatsCounterTheme {
        ItemListScreen(
            modifier = Modifier.background(MatsCounterTheme.colors.background),
            uiState = uiState,
            onItemSelected = { _, _ -> },
        )
    }
}


