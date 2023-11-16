package wing.tree.multiplication.table.ad.composable

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.height
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass.Companion.Expanded
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass.Companion.Medium
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import wing.tree.multiplication.table.BuildConfig
import wing.tree.multiplication.table.R
import wing.tree.multiplication.table.extension.empty
import wing.tree.multiplication.table.extension.fullyOpaque
import wing.tree.multiplication.table.extension.fullyTransparent
import wing.tree.multiplication.table.extension.zero

@Composable
fun Banner(
    modifier: Modifier = Modifier,
    widthSizeClass: WindowWidthSizeClass? = null
) {
    if (LocalInspectionMode.current.not()) {
        val configuration = LocalConfiguration.current
        val context = LocalContext.current
        val adSize = AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(
            context,
            configuration.screenWidthDp
        )
            .ifINVALID {
                when (widthSizeClass) {
                    Medium, Expanded -> AdSize.FULL_BANNER
                    else -> AdSize.BANNER
                }
            }

        var adLoaded by remember {
            mutableStateOf(false)
        }

        val transition = updateTransition(targetState = adLoaded, label = null)
        val alpha by transition.animateFloat(label = String.empty) {
            when (it) {
                true -> Float.fullyOpaque
                false -> Float.fullyTransparent
            }
        }

        val height by transition.animateDp(label = String.empty) {
            when (it) {
                true -> adSize.height.coerceAtLeast(AdSize.BANNER.height).dp
                false -> Dp.zero
            }
        }

        AndroidView(
            modifier = modifier
                .height(height)
                .alpha(alpha),
            factory = {
                AdView(it).apply {
                    setAdSize(adSize)
                    AdSize.FULL_WIDTH

                    adListener = object : AdListener() {
                        override fun onAdFailedToLoad(adError: LoadAdError) {
                            super.onAdFailedToLoad(adError)

                            adLoaded = false
                        }

                        override fun onAdLoaded() {
                            super.onAdLoaded()

                            adLoaded = true
                        }
                    }

                    adUnitId = context.getString(
                        if (BuildConfig.DEBUG) {
                            R.string.sample_banner_ad_unit_id
                        } else {
                            R.string.banner_ad_unit_id
                        }
                    )

                    loadAd(AdRequest.Builder().build())
                }
            }
        )
    }
}

private fun AdSize.ifINVALID(defaultValue: () -> AdSize) = when (this) {
    AdSize.INVALID -> defaultValue()
    else -> this
}
