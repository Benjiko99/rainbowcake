package co.zsmb.rainbowcake.navigation

import androidx.annotation.AnimRes
import androidx.annotation.AnimatorRes
import androidx.fragment.app.Fragment
import kotlin.reflect.KClass


/**
 * A basic set of Fragment-based navigation actions.
 */
public interface Navigator {

    /**
     * Adds [fragment] to the top of the current Fragment stack.
     */
    public fun add(fragment: Fragment)

    /**
     * Adds [fragment] to the top of the current Fragment stack. All four
     * animation parameters must be provided, with a 0 value indicating if
     * no animation should take place.
     *
     * @param fragment The new Fragment that will replace the _current_ one.
     * @param enterAnim The enter animation used for the view of the [fragment]
     *                  that's being added.
     * @param exitAnim The exit animation used for the view of the _current_
     *                 Fragment that's being replaced (covered).
     * @param popEnterAnim The enter animation for the view of the _current_
     *                     Fragment when it reappears due to [fragment] being
     *                     popped.
     * @param popExitAnim The exit animation for the view of [fragment] when
     *                    it's eventually popped from the stack.
     */
    public fun add(
            fragment: Fragment,
            @AnimatorRes @AnimRes enterAnim: Int,
            @AnimatorRes @AnimRes exitAnim: Int,
            @AnimatorRes @AnimRes popEnterAnim: Int,
            @AnimatorRes @AnimRes popExitAnim: Int
    )

    /**
     * Replaces the top Fragment on the stack with [fragment].
     */
    public fun replace(
            fragment: Fragment
    )

    /**
     * Replaces the top Fragment on the stack with [fragment], with
     * customized animations. All four animation parameters must be provided,
     * with a 0 value indicating if no animation should take place.
     *
     * @param fragment The new Fragment that will replace the _current_ one.
     * @param enterAnim The enter animation used for the view of the [fragment]
     *                  that's being added.
     * @param exitAnim The exit animation used for the view of the _current_
     *                 Fragment that's being removed.
     * @param popEnterAnim The enter animation for the view of the Fragment
     *                     that is revealed when [fragment] is popped.
     * @param popExitAnim The exit animation for the view of [fragment] when
     *                    it's eventually popped from the stack.
     */
    public fun replace(
            fragment: Fragment,
            @AnimatorRes @AnimRes enterAnim: Int,
            @AnimatorRes @AnimRes exitAnim: Int,
            @AnimatorRes @AnimRes popEnterAnim: Int,
            @AnimatorRes @AnimRes popExitAnim: Int
    )

    /**
     * Removes the top Fragment from the stack.
     *
     * @return true if something was popped.
     */
    public fun pop(): Boolean

    /**
     * Removes Fragments from the top of the stack until the type of Fragment specified,
     * not including said Fragment. If the Fragment is not found, no changes are made.
     *
     * @return true if something was popped.
     */
    public fun popUntil(fragmentKClass: KClass<out Fragment>): Boolean

    /**
     * Clears the entire Fragment stack, and adds [fragments] to it, in the order they're
     * passed to this function (i.e. the last one will be placed on top of the stack).
     */
    public fun setStack(vararg fragments: Fragment)

    /**
     * Clears the entire Fragment stack, and adds [fragments] to it, in the order they're
     * passed to this function (i.e. the last one will be placed on top of the stack).
     */
    public fun setStack(fragments: Iterable<Fragment>)

    /**
     * Finishes the current Activity.
     */
    public fun closeApplication()

    /**
     * Execute all currently pending navigation actions together.
     */
    public fun executePending()

}

/**
 * Convenience function for [Navigator.popUntil].
 */
public inline fun <reified T : Fragment> Navigator.popUntil(): Boolean {
    return popUntil(T::class)
}

/**
 * Fetches the [Navigator] instance from the Activity containing the Fragment.
 *
 * @throws IllegalStateException if the Fragment is in an Activity that doesn't have a [Navigator]
 */
public val Fragment.navigator: Navigator?
    get() {
        val activity = activity ?: return null
        return (activity as? NavActivity<*, *>)
                ?.navigator
                ?: throw IllegalStateException("Fragment is not in an Activity that extends NavActivity")
    }

/**
 * The fully qualified name of the Fragment instance, used as both the name of the
 * transition that places the Fragment on the back stack and as the Fragment's
 * tag in the FragmentManager.
 */
internal val Fragment.navTag: String
    get() {
        return this::class.qualifiedName!!
    }

/**
 * The fully qualified name of the Fragment class, used as both the name of the
 * transition that places the Fragment on the back stack and as the Fragment's
 * tag in the FragmentManager.
 */
internal val KClass<out Fragment>.navTag: String
    get() {
        return this.qualifiedName!!
    }
