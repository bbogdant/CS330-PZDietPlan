package com.metropolitan.cs330_project.ui.model


import androidx.compose.runtime.compositionLocalOf
import com.metropolitan.cs330_project.ui.model.model.ScreenUi

val AmbientSharedViewModel = compositionLocalOf<SharedViewModel> {
    throw IllegalStateException("SharedViewModel is not initialized")
}

val AmbientScreenState = compositionLocalOf<ScreenUi> {
    throw IllegalStateException("SharedViewModel is not initialized")
}