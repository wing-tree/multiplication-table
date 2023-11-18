package wing.tree.multiplication.table.theme

import android.app.Activity
import android.view.View
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

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
        typography = typography,
        content = content
    )
}

private val View.isNotInEditMode: Boolean get() = isInEditMode.not()
