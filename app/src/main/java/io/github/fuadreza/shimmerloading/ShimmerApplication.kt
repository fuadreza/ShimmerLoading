package io.github.fuadreza.shimmerloading

import android.app.Application
import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import io.github.fuadreza.shimmerloading.di.AppComponent
import io.github.fuadreza.shimmerloading.di.DaggerAppComponent

/**
 * Dibuat dengan kerjakerasbagaiquda oleh Shifu pada tanggal 03/08/2020.
 *
 */

open class ShimmerApplication: Application(){

    companion object{
        @Volatile
        private var INSTANCE: ShimmerApplication? = null
        /*fun getInstance(context: Context) =
            INSTANCE ?: ShimmerApplication().also {
                INSTANCE = it
            }*/

        fun getInstance(context: Context) : ShimmerApplication {
            return INSTANCE ?: ShimmerApplication().also {
                INSTANCE = it
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }

    open fun initializeComponent(): AppComponent {
        return DaggerAppComponent.factory().create(applicationContext)
    }

    val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(applicationContext)
    }

    fun <T> addToRequestQueue(req: Request<T>){
        requestQueue.add(req)
    }
}