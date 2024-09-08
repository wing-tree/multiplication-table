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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.core.view.WindowCompat
import timber.log.Timber
import wing.tree.multiplication.table.R
import wing.tree.multiplication.table.extension.function.copy
import wing.tree.multiplication.table.extension.function.launchGooglePlay
import wing.tree.multiplication.table.extension.function.launchReviewFlow
import wing.tree.multiplication.table.extension.function.shareApp
import wing.tree.multiplication.table.extension.property.`1`
import wing.tree.multiplication.table.extension.property.dec
import wing.tree.multiplication.table.extension.property.isCompact
import wing.tree.multiplication.table.extension.property.isNotCompact
import wing.tree.multiplication.table.extension.property.paddingValues
import wing.tree.multiplication.table.main.action.MainAction
import wing.tree.multiplication.table.main.state.DialogState
import wing.tree.multiplication.table.main.view.composable.BottomBar
import wing.tree.multiplication.table.main.view.composable.Dialog
import wing.tree.multiplication.table.main.view.composable.NavigationRail
import wing.tree.multiplication.table.main.view.composable.PageContent
import wing.tree.multiplication.table.model.Key
import wing.tree.multiplication.table.speed.quiz.view.SpeedQuizActivity
import wing.tree.multiplication.table.test.view.TestActivity
import wing.tree.multiplication.table.theme.MultiplicationTableTheme
import wing.tree.multiplication.table.token.Padding
import wing.tree.multiplication.table.top.level.property.MAXIMUM_TIMES_TABLE
import wing.tree.multiplication.table.top.level.property.MINIMUM_TIMES_TABLE
import wing.tree.multiplication.table.top.level.property.MULTIPLICATION_TABLES_PER_PAGE
import wing.tree.multiplication.table.top.level.property.fillMaxSize

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
                                putExtra(Key.START(), MINIMUM_TIMES_TABLE)
                            }
                        )

                        MainAction.Navigate.ToTest -> startActivity(
                            Intent(this, TestActivity::class.java).apply {
                                putExtra(Key.END_INCLUSIVE(), 17)
                                putExtra(Key.START(), MINIMUM_TIMES_TABLE)
                            }
                        )

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
                    MAXIMUM_TIMES_TABLE.dec.div(MULTIPLICATION_TABLES_PER_PAGE)
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
                        .paddingValues
                        .copy(top = it.calculateTopPadding())

                    Row(modifier = fillMaxSize) {
                        if (widthSizeClass.isNotCompact) {
                            NavigationRail(
                                currentPage = state.currentPage,
                                onAction = onAction
                            )
                        }

                        Column(
                            modifier = fillMaxSize.padding(
                                start = it.calculateStartPadding(layoutDirection),
                                end = it.calculateEndPadding(layoutDirection),
                                bottom = it.calculateBottomPadding()
                            ),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            VerticalPager(
                                state = state,
                                modifier = Modifier.weight(weight = Float.`1`),
                                contentPadding = contentPadding,
                                beyondBoundsPageCount = Int.`1`
                            ) { page ->
                                PageContent(
                                    page = page,
                                    modifier = fillMaxSize.padding(vertical = Padding.small)
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
                    onAction = {
                        dialogState = DialogState.Dismissed

                        onAction(it)
                    }
                )
            }
        }
    }
}
