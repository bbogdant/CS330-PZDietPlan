package com.metropolitan.cs330_project.ui.model.state

sealed class DietListState {
    object CURRENT : DietListState()
    object ARCHIVED : DietListState()
}