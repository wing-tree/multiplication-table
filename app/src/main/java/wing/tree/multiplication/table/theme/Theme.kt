package wing.tree.multiplication.table.theme

import android.app.Activity
import android.view.View
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val lightColorScheme = lightColorScheme(
    primary = purple40,
    secondary = purpleGrey40,
    tertiary = pink40
)

@Composable
fun MultiplicationTableTheme(
    content: @Composable () -> Unit
) {
    val view = LocalView.current

    if (view.isNotInEditMode) {
        SideEffect {
            val context = view.context

            if (context is Activity) {
                val insetsController = WindowCompat.getInsetsController(
                    context.window,
                    view
                )

                insetsController.isAppearanceLightStatusBars = true
            }
        }
    }

    MaterialTheme(
        colorScheme = lightColorScheme,
        typography = typography,
        content = content
    )
}

private val View.isNotInEditMode: Boolean get() = isInEditMode.not()
