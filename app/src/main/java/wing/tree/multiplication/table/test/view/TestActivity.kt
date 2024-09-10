package wing.tree.multiplication.table.test.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay
import wing.tree.multiplication.table.R
import wing.tree.multiplication.table.ad.InterstitialAdLoader
import wing.tree.multiplication.table.ad.composable.ProgressDialog
import wing.tree.multiplication.table.dialog.intent.DialogState
import wing.tree.multiplication.table.dialog.model.Dialog
import wing.tree.multiplication.table.extension.property.`7`
import wing.tree.multiplication.table.extension.property.hundreds
import wing.tree.multiplication.table.extension.property.isNotFinishing
import wing.tree.multiplication.table.extension.property.twice
import wing.tree.multiplication.table.test.intent.TestSideEffect
import wing.tree.multiplication.table.test.view.composable.Test
import wing.tree.multiplication.table.test.view.composable.TopBar
import wing.tree.multiplication.table.test.view.model.TestViewModel
import wing.tree.multiplication.table.theme.MultiplicationTableTheme

class TestActivity : ComponentActivity() {
    private val viewModel by viewModels<TestViewModel>()

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        InterstitialAdLoader.load(this)

        setContent {
            MultiplicationTableTheme(activity = this) {
                val state by viewModel.state.collectAsState()

                var dialogState by remember {
                    mutableStateOf<DialogState<Dialog.Progress>>(DialogState.Dismissed)
                }

                viewModel.collectSideEffect { sideEffect ->
                    when (sideEffect) {
                        TestSideEffect.Navigate.ToHome -> if (isNotFinishing) {
                            finish()
                        }

                        TestSideEffect.Show.InterstitialAd -> {
                            dialogState = DialogState.Showing(
                                Dialog.Progress(getString(R.string.ad_loading))
                            )

                            delay(timeMillis = Long.`7`.hundreds.twice)

                            InterstitialAdLoader.show(activity = this@TestActivity)

                            dialogState = DialogState.Dismissed
                        }
                    }
                }

                Scaffold(
                    topBar = {
                        TopBar(
                            navigationOnClick = {
                                if (isNotFinishing) {
                                    finish()
                                }
                            }
                        )
                    }
                ) {
                    val widthSizeClass = calculateWindowSizeClass(this).widthSizeClass

                    Test(
                        state = state,
                        widthSizeClass = widthSizeClass,
                        onEvent = viewModel::onEvent,
                        modifier = Modifier.padding(it)
                    )
                }

                ProgressDialog(state = dialogState) {
                    dialogState = DialogState.Dismissed
                }
            }
        }
    }
}
