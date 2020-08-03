package io.github.fuadreza.shimmerloading.ui

import dagger.Subcomponent
import io.github.fuadreza.shimmerloading.di.ActivityScope

/**
 * Dibuat dengan kerjakerasbagaiquda oleh Shifu pada tanggal 03/08/2020.
 *
 */

@ActivityScope
@Subcomponent
interface MainComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): MainComponent
    }

    fun inject(activity : MainActivity)
}