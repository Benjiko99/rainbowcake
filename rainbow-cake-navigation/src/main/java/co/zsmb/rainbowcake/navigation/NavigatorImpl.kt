package co.zsmb.rainbowcake.navigation

import androidx.annotation.AnimRes
import androidx.annotation.AnimatorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.internal.InternalRainbowCakeApi
import kotlin.reflect.KClass


/**
 * A default implementation of [Navigator] with mostly straightforward
 * [FragmentManager] actions and default transition animations.
 */
internal class NavigatorImpl(
        private val activity: AppCompatActivity,
        @AnimatorRes @AnimRes private val defaultEnterAnim: Int,
        @AnimatorRes @AnimRes private val defaultExitAnim: Int,
        @AnimatorRes @AnimRes private val defaultPopEnterAnim: Int,
        @AnimatorRes @AnimRes private val defaultPopExitAnim: Int
) : ExtendedNavigator {

    private val supportFragmentManager = activity.supportFragmentManager

    override fun add(fragment: Fragment) {
        add(fragment, defaultEnterAnim, defaultExitAnim, defaultPopEnterAnim, defaultPopExitAnim)
    }

    override fun add(fragment: Fragment,
                     @AnimatorRes @AnimRes enterAnim: Int,
                     @AnimatorRes @AnimRes exitAnim: Int,
                     @AnimatorRes @AnimRes popEnterAnim: Int,
                     @AnimatorRes @AnimRes popExitAnim: Int) {
        if (supportFragmentManager.isStateSaved) {
            return
        }

        supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim)
                .replace(R.id.contentFrame, fragment, fragment.navTag)
                .addToBackStack(fragment.navTag)
                .commit()
    }

    override fun replace(fragment: Fragment) {
        replace(fragment, defaultEnterAnim, defaultExitAnim, defaultPopEnterAnim, defaultPopExitAnim)
    }

    override fun replace(
            fragment: Fragment,
            @AnimatorRes @AnimRes enterAnim: Int,
            @AnimatorRes @AnimRes exitAnim: Int,
            @AnimatorRes @AnimRes popEnterAnim: Int,
            @AnimatorRes @AnimRes popExitAnim: Int
    ) {
        if (supportFragmentManager.isStateSaved) {
            return
        }

        (getTopFragment() as? RainbowCakeFragment<*, *>)?.let { rcFragment ->
            @OptIn(InternalRainbowCakeApi::class)
            rcFragment.overrideAnimation = exitAnim
        }
        supportFragmentManager.popBackStackImmediate()

        supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim)
                .replace(R.id.contentFrame, fragment, fragment.navTag)
                .addToBackStack(fragment.navTag)
                .commit()

        supportFragmentManager.executePendingTransactions()
    }

    override fun pop(): Boolean {
        if (supportFragmentManager.isStateSaved) {
            return false
        }

        return supportFragmentManager.popBackStackImmediate()
    }

    override fun popUntil(fragmentKClass: KClass<out Fragment>): Boolean {
        if (supportFragmentManager.isStateSaved) {
            return false
        }

        return supportFragmentManager.popBackStackImmediate(fragmentKClass.navTag, 0)
    }

    override fun setStack(vararg fragments: Fragment) {
        if (supportFragmentManager.isStateSaved) {
            return
        }

        supportFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        fragments.forEach(this::add)
    }

    override fun setStack(fragments: Iterable<Fragment>) {
        if (supportFragmentManager.isStateSaved) {
            return
        }

        supportFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        fragments.forEach(this::add)
    }

    override fun closeApplication() {
        activity.finish()
    }

    override fun onBackPressed() {
        val fragment = getTopFragment()
        val handledBackPress = fragment is BackPressAware && fragment.onBackPressed()
        if (!handledBackPress) {
            if (supportFragmentManager.backStackEntryCount > 1) {
                pop()
            } else {
                activity.finish()
            }
        }
    }

    override fun getTopFragment(): Fragment? {
        return supportFragmentManager.findFragmentById(R.id.contentFrame)
    }

    override fun executePending() {
        supportFragmentManager.executePendingTransactions()
    }

}
