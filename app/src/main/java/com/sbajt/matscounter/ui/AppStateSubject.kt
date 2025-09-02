package com.sbajt.matscounter.ui

import com.sbajt.matscounter.ui.models.AppState
import kotlinx.coroutines.flow.MutableStateFlow

val stateSubject = MutableStateFlow(AppState())
