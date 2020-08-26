@file:Suppress("NOTHING_TO_INLINE")

package co.zsmb.rainbowcake.navigation.extensions

import android.os.Bundle
import androidx.fragment.app.Fragment

/**
 * Adds an arguments Bundle to the receiver Fragment, performing [argSetup] on the Bundle.
 *
 * @return the Fragment instance
 */
public inline fun <T : Fragment> T.applyArgs(argSetup: Bundle.() -> Unit): T = apply {
    val bundle = Bundle()
    bundle.argSetup()
    arguments = bundle
}
