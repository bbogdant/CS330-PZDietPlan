package com.metropolitan.cs330_project.ui.model

import androidx.annotation.OpenForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.metropolitan.cs330_project.ui.model.state.ScreenState
import java.util.ArrayDeque

@OpenForTesting
class ScreenBackStackImpl : ScreenBackStack {

    private val currentScreenState = MutableLiveData<ScreenState>()
    private val backStack = ArrayDeque<ScreenState>()

    init {
        pushBackStack(ScreenState.CurrentDietList)
    }

    override fun getCurrentScreen(): LiveData<ScreenState> {
        return currentScreenState
    }

    override fun popBackStack(): ScreenState? {
        if (backStack.size == 1) return null
        backStack.pop()
        return backStack.peekFirst()?.also { currentScreenState.value = it }
    }

    override fun pushBackStack(screenState: ScreenState) {
        if (screenState != currentScreenState.value) {
            backStack.push(screenState)
            currentScreenState.value = screenState
        }
    }
}

interface ScreenBackStack {
    fun getCurrentScreen(): LiveData<ScreenState>
    fun popBackStack(): ScreenState?
    fun pushBackStack(screenState: ScreenState)

}