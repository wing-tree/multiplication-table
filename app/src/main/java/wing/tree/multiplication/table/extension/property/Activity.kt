package wing.tree.multiplication.table.extension.property

import android.app.Activity

val Activity.isNotFinishing: Boolean get() = isFinishing.not()
