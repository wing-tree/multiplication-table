package wing.tree.multiplication.table.ad

import android.app.Activity
import android.content.Context
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import wing.tree.multiplication.table.BuildConfig
import wing.tree.multiplication.table.R
import wing.tree.multiplication.table.extension.function.isNotNull
import wing.tree.multiplication.table.extension.function.isNull
import wing.tree.multiplication.table.extension.property.oneHourInMilliseconds
import java.util.Timer
import java.util.TimerTask
import kotlin.concurrent.timerTask

object InterstitialAdLoader :  Timer(true) {
    val isAdLoaded: Boolean get() = interstitialAd.isNotNull()

    private val `this` = this

    private var interstitialAd: InterstitialAd? = null
    private var timerTask: TimerTask? = null

    fun load(
        context: Context,
        loadCallback: InterstitialAdLoadCallback? = null,
    ) {
        if (interstitialAd.isNull()) {
            timerTask?.cancel()

            val adRequest = AdRequest.Builder().build()
            val resId = when {
                BuildConfig.DEBUG -> R.string.sample_interstitial_ad_unit_id
                else -> R.string.interstitial_ad_unit_id
            }

            InterstitialAd.load(
                context,
                context.getString(resId),
                adRequest,
                object : InterstitialAdLoadCallback() {
                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        loadCallback?.onAdFailedToLoad(adError)
                    }

                    override fun onAdLoaded(interstitialAd: InterstitialAd) {
                        `this`.interstitialAd = interstitialAd

                        loadCallback?.onAdLoaded(interstitialAd)

                        timerTask = timerTask {
                            `this`.interstitialAd = null

                            cancel()
                        }

                        schedule(
                            timerTask,
                            Long.oneHourInMilliseconds,
                            Long.oneHourInMilliseconds
                        )
                    }
                }
            )
        }
    }

    fun show(
        activity: Activity,
        fullScreenContentCallback: FullScreenContentCallback? = null
    ): Unit? {
        return interstitialAd?.let {
            it.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    fullScreenContentCallback?.onAdDismissedFullScreenContent()
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    fullScreenContentCallback?.onAdFailedToShowFullScreenContent(adError)
                }

                override fun onAdImpression() {
                    fullScreenContentCallback?.onAdImpression()
                }

                override fun onAdShowedFullScreenContent() {
                    fullScreenContentCallback?.onAdShowedFullScreenContent()
                }
            }

            it.show(activity).also { _ ->
                interstitialAd = null
            }
        }
    }
}
