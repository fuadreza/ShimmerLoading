package io.github.fuadreza.shimmerloading.di

import android.content.Context
import dagger.Component
import dagger.BindsInstance
import io.github.fuadreza.shimmerloading.ui.MainComponent
import javax.inject.Singleton

/**
 * Dibuat dengan kerjakerasbagaiquda oleh Shifu pada tanggal 03/08/2020.
 *
 */
@Singleton
@Component(modules = [AppSubcomponents::class])
interface AppComponent {

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance context: Context) : AppComponent
    }

    fun mainComponent() : MainComponent.Factory
}