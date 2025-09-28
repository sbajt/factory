package com.sbajt.matscounter.ui.models.appBars

sealed class AppBarState {

    data object Empty: AppBarState(), BaseAppBarState {
        override val title: String?
            get() = null
        override val actionList: List<ActionBarActions>
            get() = emptyList()
    }

    data class ItemList(
        override val title: String,
        override val actionList: List<ActionBarActions>,
    ) : AppBarState(), BaseAppBarState

    data class ItemDetails(
        override val title: String,
        override val actionList: List<ActionBarActions>
    ) : AppBarState(), BaseAppBarState

    data class ItemBuildPath(
        override val title: String,
        override val actionList: List<ActionBarActions>
    ) : AppBarState(), BaseAppBarState
}
