package co.zsmb.rainbowcake.navigation

import co.zsmb.rainbowcake.base.RainbowCakeViewModel


/**
 * Dummy ViewState
 */
public object SimpleNavViewState

/**
 * Dummy ViewModel
 */
public object SimpleNavViewModel : RainbowCakeViewModel<SimpleNavViewState>(SimpleNavViewState)

/**
 * An implementation of a [NavActivity] for use cases where the application's actual
 * Activity class doesn't need access to a ViewModel and doesn't render view state
 * itself, but merely navigates between Fragments.
 */
public abstract class SimpleNavActivity : NavActivity<SimpleNavViewState, SimpleNavViewModel>() {

    final override fun provideViewModel(): SimpleNavViewModel = SimpleNavViewModel

    final override fun render(viewState: SimpleNavViewState): Unit = Unit

}
