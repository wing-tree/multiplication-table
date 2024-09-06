package wing.tree.multiplication.table.main.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.window.Dialog
import androidx.core.view.WindowCompat
import timber.log.Timber
import wing.tree.multiplication.table.R
import wing.tree.multiplication.table.composable.noOperations
import wing.tree.multiplication.table.extension.extraSmall
import wing.tree.multiplication.table.extension.function.launchGooglePlay
import wing.tree.multiplication.table.extension.function.launchReviewFlow
import wing.tree.multiplication.table.extension.function.shareApp
import wing.tree.multiplication.table.extension.property.`1`
import wing.tree.multiplication.table.extension.property.`2`
import wing.tree.multiplication.table.extension.property.`4`
import wing.tree.multiplication.table.extension.property.isCompact
import wing.tree.multiplication.table.extension.property.isNotCompact
import wing.tree.multiplication.table.extension.property.marginValues
import wing.tree.multiplication.table.main.action.MainAction
import wing.tree.multiplication.table.main.state.DialogState
import wing.tree.multiplication.table.main.view.composable.BottomBar
import wing.tree.multiplication.table.main.view.composable.NavigationRail
import wing.tree.multiplication.table.main.view.composable.PageContent
import wing.tree.multiplication.table.model.Key
import wing.tree.multiplication.table.quiz.view.QuizActivity
import wing.tree.multiplication.table.speed.quiz.view.SpeedQuizActivity
import wing.tree.multiplication.table.theme.MultiplicationTableTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Timber.plant(Timber.DebugTree())
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            MultiplicationTableTheme(activity = this) {
                var dialogState by remember {
                    mutableStateOf<DialogState>(DialogState.Dismissed)
                }

                val onAction: (MainAction) -> Unit = {
                    when (it) {
                        MainAction.Navigate.ToSpeedQuiz -> startActivity(
                            Intent(this, SpeedQuizActivity::class.java).apply {
                                putExtra(Key.END_INCLUSIVE(), 3)
                                putExtra(Key.START(), 2)
                            }
                        )

                        MainAction.Navigate.ToTest -> startActivity(Intent(this, QuizActivity::class.java))
                        MainAction.Quiz -> dialogState = DialogState.Showing
                        MainAction.RateReview -> launchReviewFlow(
                            onSuccess = {
                                Toast.makeText(
                                    this,
                                    R.string.thank_you_for_your_valuable_review,
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            },
                            onFailure = { exception ->
                                Timber.e(exception)

                                launchGooglePlay()
                            }
                        )

                        MainAction.Share -> shareApp()
                    }
                }

                val state = rememberPagerState {
                    Int.`4`
                }

                val windowSizeClass = calculateWindowSizeClass(this)
                val widthSizeClass = windowSizeClass.widthSizeClass

                Scaffold(
                    bottomBar = {
                        if (widthSizeClass.isCompact) {
                            BottomBar(
                                currentPage = state.currentPage,
                                onAction = onAction
                            )
                        }
                    }
                ) {
                    val layoutDirection = LocalLayoutDirection.current
                    val contentPadding = widthSizeClass
                        .marginValues
                        .copy(top = it.calculateTopPadding())

                    Row(modifier = Modifier.fillMaxSize()) {
                        if (widthSizeClass.isNotCompact) {
                            NavigationRail(
                                currentPage = state.currentPage,
                                onAction = onAction
                            )
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(
                                    start = it.calculateStartPadding(layoutDirection),
                                    end = it.calculateEndPadding(layoutDirection),
                                    bottom = it.calculateBottomPadding()
                                ),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            VerticalPager(
                                state = state,
                                modifier = Modifier.weight(Float.`1`),
                                contentPadding = contentPadding,
                                beyondBoundsPageCount = Int.`2`
                            ) { page ->
                                PageContent(
                                    page = page,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(vertical = Dp.extraSmall)
                                )
                            }
                        }
                    }
                }

                Dialog(
                    state = dialogState,
                    onDismissRequest = {
                        dialogState = DialogState.Dismissed
                    },
                    onAction = onAction
                )
            }
        }
    }
}

@Composable
private fun Dialog(
    state: DialogState,
    onDismissRequest: () -> Unit,
    onAction: (MainAction) -> Unit
) {
    when (state) {
        DialogState.Dismissed -> noOperations()
        else -> Dialog(onDismissRequest = onDismissRequest) {
            Surface {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ElevatedButton(
                        onClick = {
                            onAction(MainAction.Navigate.ToSpeedQuiz)
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.speed_quiz)
                        )
                    }

                    ElevatedButton(
                        onClick = {
                            onAction(MainAction.Navigate.ToTest)
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.test)
                        )
                    }
                }
            }
        }
    }
}
