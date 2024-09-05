package wing.tree.multiplication.table.extension.function

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ClipDescription.MIMETYPE_TEXT_PLAIN
import android.content.Intent
import android.net.Uri
import com.google.android.play.core.review.ReviewManagerFactory
import wing.tree.multiplication.table.R

fun Activity.launchGooglePlay() {
    try {
        startActivity(
            Intent (
                Intent.ACTION_VIEW,
                Uri.parse("market://details?id=$packageName"))
        )
    } catch (activityNotFoundException: ActivityNotFoundException) {
        startActivity(
            Intent (
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
            )
        )
    }
}

fun Activity.launchReviewFlow(
    onSuccess: () -> Unit,
    onFailure: (Exception?) -> Unit,
) {
    val reviewManager = ReviewManagerFactory.create(this)
    val task = reviewManager.requestReviewFlow()

    task.addOnCompleteListener {
        if (it.isSuccessful) {
            val reviewInfo = it.result

            reviewInfo.describeContents()
            reviewManager
                .launchReviewFlow(this, reviewInfo)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        onSuccess()
                    } else {
                        onFailure(task.exception)
                    }
                }
        } else {
            onFailure(it.exception)
        }
    }
}

fun Activity.shareApp() {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = MIMETYPE_TEXT_PLAIN

        putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=${packageName}")
    }

    Intent.createChooser(intent, getString(R.string.share_app)).apply {
        addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
    }
        .let(::startActivity)
}
