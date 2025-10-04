package com.sbajt.matscounter.ui.models.appBars

sealed class AppBarState {

    data object Empty: AppBarState(), BaseAppBarState {
        override val title: String?
            get() = null
        override val actionList: List<AppBarActionType>
            get() = emptyList()
    }

    data class ItemListAppBar(
        override val title: String,
        override val actionList: List<AppBarActionType>,
    ) : AppBarState(), BaseAppBarState

    data class ItemDetailsAppBar(
        override val title: String,
        override val actionList: List<AppBarActionType>
    ) : AppBarState(), BaseAppBarState

    data class ItemBuildPathAppBar(
        override val title: String,
        override val actionList: List<AppBarActionType>
    ) : AppBarState(), BaseAppBarState
}
