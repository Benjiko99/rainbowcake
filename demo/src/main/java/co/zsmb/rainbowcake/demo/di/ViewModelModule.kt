package co.zsmb.rainbowcake.demo.di

import androidx.lifecycle.ViewModel
import co.zsmb.rainbowcake.dagger.ViewModelKey
import co.zsmb.rainbowcake.demo.ui.bar.BarViewModel
import co.zsmb.rainbowcake.demo.ui.example.ExampleViewModel
import co.zsmb.rainbowcake.demo.ui.foo.FooViewModel
import co.zsmb.rainbowcake.demo.ui.sharedvmpager.SharedVMPagerViewModel
import co.zsmb.rainbowcake.demo.ui.sharedvmpager.pages.ScreenViewModel
import co.zsmb.rainbowcake.demo.ui.viewbinding.ViewBindingSampleViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ExampleViewModel::class)
    abstract fun bindExampleViewModel(exampleViewModel: ExampleViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FooViewModel::class)
    abstract fun bindFooViewModel(fooViewModel: FooViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BarViewModel::class)
    abstract fun bindBarViewModel(barViewModel: BarViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SharedVMPagerViewModel::class)
    abstract fun bindSharedVMPagerViewModel(sharedVMPagerViewModel: SharedVMPagerViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ScreenViewModel::class)
    abstract fun bindScreenViewModel(screenViewModel: ScreenViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ViewBindingSampleViewModel::class)
    abstract fun bindViewBindingSampleViewModel(viewBindingSampleViewModel: ViewBindingSampleViewModel): ViewModel

}
