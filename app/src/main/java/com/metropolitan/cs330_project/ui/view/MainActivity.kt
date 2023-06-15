package com.metropolitan.cs330_project.ui.view


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.ComposeView
import com.metropolitan.cs330_project.ui.model.AmbientSharedViewModel
import com.metropolitan.cs330_project.ui.model.ScreenBackStack
import com.metropolitan.cs330_project.ui.model.SharedViewModel
import com.metropolitan.cs330_project.ui.view.layout.ShoppingListApp
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val sharedViewModel: SharedViewModel by viewModel()
    private val backStack: ScreenBackStack by inject()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val composeView = ComposeView(this)
        composeView.setContent {
            CompositionLocalProvider(
                AmbientSharedViewModel provides sharedViewModel
            ) { ShoppingListApp() }
        }
        setContentView(composeView)

    }

    override fun onBackPressed() {
        backStack.popBackStack() ?: super.onBackPressed()
    }
}
