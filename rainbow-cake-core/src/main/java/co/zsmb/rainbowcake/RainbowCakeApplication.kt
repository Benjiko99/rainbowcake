package co.zsmb.rainbowcake

import android.app.Application
import android.support.annotation.CallSuper
import co.zsmb.rainbowcake.base.InjectedActivity
import co.zsmb.rainbowcake.base.InjectedFragment
import co.zsmb.rainbowcake.di.RainbowCakeComponent

/**
 * Base class for applications built on this architecture, primarily
 * used for DI integration
 */
abstract class RainbowCakeApplication : Application() {

    /**
     * The injector used by [InjectedFragment] and [InjectedActivity]
     */
    abstract val injector: RainbowCakeComponent

    @CallSuper
    override fun onCreate() {
        super.onCreate()
        setupInjector()
    }

    /**
     * Creates a Dagger injector and sets the [injector] property
     */
    protected abstract fun setupInjector()

}