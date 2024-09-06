package wing.tree.multiplication.table.composition.local

import android.app.Activity
import androidx.compose.runtime.staticCompositionLocalOf

val localActivity = staticCompositionLocalOf<Activity> {
    error(Activity::class)
}
