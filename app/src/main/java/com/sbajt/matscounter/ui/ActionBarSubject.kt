package com.sbajt.matscounter.ui

import com.sbajt.matscounter.ui.models.ActionBarActions
import kotlinx.coroutines.flow.MutableStateFlow

val actionBarSubject = MutableStateFlow(listOf<ActionBarActions>())
