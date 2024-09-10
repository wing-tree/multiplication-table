package wing.tree.multiplication.table.theme

import android.app.Activity
import android.view.View
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import wing.tree.multiplication.table.composition.local.localActivity
import wing.tree.multiplication.table.composition.local.localWindowWidthSizeClass
import wing.tree.multiplication.table.extension.function.second
import wing.tree.multiplication.table.extension.function.third

private val lightColorScheme = lightColorScheme(
    primary = palette.first(),
    secondary = palette.second(),
    tertiary = palette.third(),
    background = snow
)

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun MultiplicationTableTheme(
    activity: Activity,
    content: @Composable () -> Unit
) {
    val view = LocalView.current

    if (view.isNotInEditMode) {
        SideEffect {
            val window = activity.window
            val insetsController = WindowCompat.getInsetsController(
                window,
                view
            )

            insetsController.isAppearanceLightStatusBars = true
        }
    }

    CompositionLocalProvider(
        localActivity provides activity,
        localWindowWidthSizeClass provides calculateWindowSizeClass(activity = activity).widthSizeClass
    ) {
        MaterialTheme(
            colorScheme = lightColorScheme,
            typography = typography,
            content = content
        )
    }
}

private val View.isNotInEditMode: Boolean get() = isInEditMode.not()
