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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.core.view.WindowCompat
import timber.log.Timber
import wing.tree.multiplication.table.R
import wing.tree.multiplication.table.ad.composable.Banner
import wing.tree.multiplication.table.composable.HorizontalSpacer
import wing.tree.multiplication.table.composable.Icon
import wing.tree.multiplication.table.composable.MultiplicationTable
import wing.tree.multiplication.table.composable.VerticalSpacer
import wing.tree.multiplication.table.composable.noOperations
import wing.tree.multiplication.table.constant.MINIMUM_TIMES_TABLE
import wing.tree.multiplication.table.extension.extraSmall
import wing.tree.multiplication.table.extension.full
import wing.tree.multiplication.table.extension.incrementByTwo
import wing.tree.multiplication.table.extension.launchGooglePlay
import wing.tree.multiplication.table.extension.launchReviewFlow
import wing.tree.multiplication.table.extension.marginValues
import wing.tree.multiplication.table.extension.quadrupled
import wing.tree.multiplication.table.extension.shareApp
import wing.tree.multiplication.table.extension.two
import wing.tree.multiplication.table.model.Action
import wing.tree.multiplication.table.quiz.view.QuizActivity
import wing.tree.multiplication.table.quiz.view.composable.FloatingActionButton
import wing.tree.multiplication.table.theme.MultiplicationTableTheme

class MainActivity : ComponentActivity() {
    private val onAction: (Action) -> Unit = {
        when (it) {
            Action.Quiz -> startActivity(Intent(this, QuizActivity::class.java))
            Action.RateReview -> launchReviewFlow(
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
            Action.Share -> shareApp()
            else -> noOperations
        }
    }

    @OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Timber.plant(Timber.DebugTree())
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            MultiplicationTableTheme {
                val state = rememberPagerState {
                    Int.two
                }

                Scaffold(
                    bottomBar = {
                        BottomBar(
                            currentPage = state.currentPage,
                            onAction = onAction
                        )
                    }
                ) {
                    val layoutDirection = LocalLayoutDirection.current
                    val widthSizeClass = calculateWindowSizeClass(this).widthSizeClass
                    val contentPadding = widthSizeClass
                        .marginValues
                        .copy(top = it.calculateTopPadding())

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
                            modifier = Modifier.weight(Float.full),
                            contentPadding = contentPadding
                        ) { page ->
                            PageContent(
                                page = page,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(vertical = Dp.extraSmall)
                            )
                        }

                        Banner(widthSizeClass = widthSizeClass)
                    }
                }
            }
        }
    }
}

@Composable
private fun BottomBar(
    currentPage: Int,
    onAction: (Action) -> Unit,
    modifier: Modifier = Modifier
) {
    BottomAppBar(
        actions = {
            IconButton(
                onClick = {
                    onAction(Action.RateReview)
                }
            ) {
                Icon(id = R.drawable.round_rate_review_24)
            }

            IconButton(
                onClick = {
                    onAction(Action.Share)
                }
            ) {
                Icon(id = R.drawable.round_share_24)
            }

            IconButton(
                onClick = {
                    onAction(Action.Store)
                }
            ) {
                Icon(id = R.drawable.round_store_24)
            }
        },
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(
                currentPage = currentPage,
                onClick = {
                    onAction(Action.Quiz)
                }
            )
        }
    )
}

@Composable
private fun PageContent(
    page: Int,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        val timesTable = MINIMUM_TIMES_TABLE.plus(page.quadrupled())

        MultiplicationTableRow(
            timesTable = timesTable,
            modifier = Modifier.weight(Float.full)
        )

        VerticalSpacer(height = Dp.extraSmall)

        MultiplicationTableRow(
            timesTable = timesTable.incrementByTwo(),
            modifier = Modifier.weight(Float.full)
        )
    }
}

@Composable
private fun MultiplicationTableRow(
    timesTable: Int,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        MultiplicationTable(
            timesTable = timesTable,
            modifier = Modifier
                .weight(Float.full)
                .fillMaxHeight()
        )

        HorizontalSpacer(width = Dp.extraSmall)

        MultiplicationTable(
            timesTable = timesTable.inc(),
            modifier = Modifier
                .weight(Float.full)
                .fillMaxHeight()
        )
    }
}
