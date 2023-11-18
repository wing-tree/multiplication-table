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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.core.view.WindowCompat
import androidx.window.layout.WindowInfoTracker
import timber.log.Timber
import wing.tree.multiplication.table.R
import wing.tree.multiplication.table.ad.composable.Banner
import wing.tree.multiplication.table.composable.noOperations
import wing.tree.multiplication.table.extension.extraSmall
import wing.tree.multiplication.table.extension.full
import wing.tree.multiplication.table.extension.isNavigationRailNotVisible
import wing.tree.multiplication.table.extension.isNavigationRailVisible
import wing.tree.multiplication.table.extension.launchGooglePlay
import wing.tree.multiplication.table.extension.launchReviewFlow
import wing.tree.multiplication.table.extension.marginValues
import wing.tree.multiplication.table.extension.shareApp
import wing.tree.multiplication.table.extension.two
import wing.tree.multiplication.table.main.view.composable.BottomBar
import wing.tree.multiplication.table.main.view.composable.NavigationRail
import wing.tree.multiplication.table.main.view.composable.PageContent
import wing.tree.multiplication.table.model.Action
import wing.tree.multiplication.table.quiz.view.QuizActivity
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

        val windowInfoTracker = WindowInfoTracker.getOrCreate(this)

        setContent {
            MultiplicationTableTheme {
                val state = rememberPagerState {
                    Int.two
                }

                val windowLayoutInfo by windowInfoTracker
                    .windowLayoutInfo(this)
                    .collectAsState(initial = null)

                val windowSizeClass = calculateWindowSizeClass(this)
                val widthSizeClass = windowSizeClass.widthSizeClass

                Scaffold(
                    bottomBar = {
                        if (widthSizeClass.isNavigationRailNotVisible(windowLayoutInfo)) {
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
                        if (widthSizeClass.isNavigationRailVisible(windowLayoutInfo)) {
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
}
