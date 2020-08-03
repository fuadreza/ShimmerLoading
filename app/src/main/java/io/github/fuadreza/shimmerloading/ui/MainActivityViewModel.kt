package io.github.fuadreza.shimmerloading.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.github.fuadreza.shimmerloading.ShimmerApplication
import io.github.fuadreza.shimmerloading.model.Recipe
import org.json.JSONArray
import javax.inject.Inject

/**
 * Dibuat dengan kerjakerasbagaiquda oleh Shifu pada tanggal 03/08/2020.
 *
 */
class MainActivityViewModel @Inject constructor() : ViewModel() {

    private val URL = "https://api.androidhive.info/json/shimmer/menu.php"

    private var allRecipes: MutableLiveData<ArrayList<Recipe>> = MutableLiveData()

    val recipeState = MutableLiveData<RecipeViewState>()

    var cartList: ArrayList<Recipe> = arrayListOf()

    fun getRecipes(context: Context) {

        val request = JsonArrayRequest(URL, object : Response.Listener<JSONArray?> {
            override fun onResponse(response: JSONArray?) {
                if (response == null) {
                    val error = "Couldn't load request"
                    recipeState.value =
                        RecipeViewState.LoadRecipeError
                    recipeState.value =
                        RecipeViewState.OnLoadError(
                            error
                        )
                    return
                }

                val recipes = Gson().fromJson<List<Recipe>>(
                    response.toString(),
                    object : TypeToken<List<Recipe?>?>() {}.type
                )

                // adding recipes to cart list
                cartList.clear()
                cartList.addAll(recipes)

                allRecipes.value = cartList

                recipeState.value = RecipeViewState.LoadRecipeSuccess

                recipeState.value = RecipeViewState.OnloadRecipeState(cartList)
            }
        }, Response.ErrorListener { error -> // error in getting json
            recipeState.value =
                RecipeViewState.LoadRecipeError
            recipeState.value =
                RecipeViewState.OnLoadError(
                    " Error nich: " + error.message
                )
        }
        )

        /*val request = JsonArrayRequest(URL,
            object : Response.Listener<JSONArray?> {
                override fun onResponse(response: JSONArray?) {
                    if (response == null) {
                        val error = "Couldn't load request"
                        recipeState.value =
                            RecipeViewState.LoadRecipeError
                        recipeState.value =
                            RecipeViewState.OnLoadError(
                                error
                            )
                        return
                    }

                    val recipes = Gson().fromJson<List<Recipe>>(
                        response.toString(),
                        object : TypeToken<List<Recipe?>?>() {}.type
                    )

                    // adding recipes to cart list
                    cartList.clear()
                    cartList.addAll(recipes)

                    allRecipes.value = cartList

                    recipeState.value = RecipeViewState.LoadRecipeSuccess

                    recipeState.value = RecipeViewState.OnloadRecipeState(cartList)

                }
            }, Response.ErrorListener { error -> // error in getting json
                recipeState.value =
                    RecipeViewState.LoadRecipeError
                recipeState.value =
                    RecipeViewState.OnLoadError(
                        " Error nich: " + error.message
                    )
            }
        )*/

        /*val myApp = ShimmerApplication.getInstance(context)
        myApp.addToRequestQueue(request)*/

        try {
            ShimmerApplication.getInstance(context).addToRequestQueue(request)
        } catch (e: NullPointerException) {
            Log.d("KOSONG", "Shimmer Kosong ::" + request.body)
        }

    }
}

sealed class RecipeViewState {
    object LoadRecipeError : RecipeViewState()
    object LoadRecipeSuccess : RecipeViewState()

    data class OnloadRecipeState(val recipesList: ArrayList<Recipe>) : RecipeViewState()
    data class OnLoadError(val msg: String?) : RecipeViewState()
}