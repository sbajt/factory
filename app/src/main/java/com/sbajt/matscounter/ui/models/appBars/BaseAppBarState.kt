package com.sbajt.matscounter.ui.models.appBars

sealed interface BaseAppBarState {
    val title: String?
    val actionList: List<ActionBarActions>
}