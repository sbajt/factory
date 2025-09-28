package com.sbajt.matscounter.ui

import com.sbajt.matscounter.ui.models.appBars.AppBarState
import kotlinx.coroutines.flow.MutableStateFlow

val appBarSubject: MutableStateFlow<AppBarState> = MutableStateFlow(AppBarState.Empty)
