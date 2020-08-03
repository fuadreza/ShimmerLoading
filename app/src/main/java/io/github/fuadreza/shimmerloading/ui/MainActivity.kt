package io.github.fuadreza.shimmerloading.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.fuadreza.shimmerloading.*
import io.github.fuadreza.shimmerloading.adapter.RecipeListAdapter
import io.github.fuadreza.shimmerloading.model.Recipe
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : AppCompatActivity()  {

    //private val TAG = MainActivity::class.java.simpleName

    private val cartList: ArrayList<Recipe> = arrayListOf()
    private var mAdapter: RecipeListAdapter? = null

    //private val URL = "https://api.androidhive.info/json/shimmer/menu.php"

    @Inject
    lateinit var recipeViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        (this.application as ShimmerApplication).appComponent.mainComponent().create().inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //mAdapter = RecipeListAdapter(this, cartList)

        setupViews()

        recipeViewModel.getRecipes(applicationContext)

        observeState()
    }

    fun setupViews() {
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.itemAnimator = DefaultItemAnimator()
        recycler_view.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

    }

    fun observeState() {
        recipeViewModel.recipeState.observe(this, Observer { state ->
            when (state) {
                is RecipeViewState.OnloadRecipeState -> onLoadRecipes(state.recipesList)
                is RecipeViewState.OnLoadError -> onLoadError(state.msg)
                is RecipeViewState.LoadRecipeSuccess -> onLoadSuccess()
            }
        })
    }

    private fun onLoadSuccess() {
        shimmer_view_container.stopShimmer()
        shimmer_view_container.visibility = View.GONE
    }

    private fun onLoadRecipes(recipesList: ArrayList<Recipe>) {

        /*recipesList.forEach {
            Log.d("ISI", "ISI RECIPE : " + it.name)
        }*/

        Log.d("ISI", "SIZE RECIPE : " + recipesList.size)

        mAdapter = RecipeListAdapter(this, recipesList)
        mAdapter?.notifyDataSetChanged()

        recycler_view.adapter = mAdapter

    }



    private fun onLoadError(msg: String?) {
        Toast.makeText(this, "Error nich: " + msg, Toast.LENGTH_LONG).show()
    }

    private fun getRecipes() {
        /*val request = JsonArrayRequest(URL,
            object : Response.Listener<JSONArray?> {
                override fun onResponse(response: JSONArray?) {
                    if (response == null){
                        Toast.makeText(
                            applicationContext,
                            "Couldn't fetch the menu! Pleas try again.",
                            Toast.LENGTH_LONG
                        ).show()
                        return
                    }

                    val recipes = Gson().fromJson<List<Recipe>>(
                        response.toString(),
                        object : TypeToken<List<Recipe?>?>() {}.type
                    )

                    // adding recipes to cart list
                    cartList.clear()
                    cartList.addAll(recipes)

                    // refreshing recycler view
                    mAdapter!!.notifyDataSetChanged()

                    // stop animating Shimmer and hide the layout
                    shimmer_view_container.stopShimmer()
                    shimmer_view_container.visibility = View.GONE
                }
            }, Response.ErrorListener { error -> // error in getting json
                Log.e(TAG, "Error nich: " + error.message)
                Toast.makeText(
                    applicationContext,
                    "Error nich: " + error.message,
                    Toast.LENGTH_SHORT
                ).show()
            })

        Log.e(TAG, "sampai sini nich: ")

        MyApplication.getInstance(this).addToRequestQueue(request)*/
    }

    override fun onResume() {
        super.onResume()
        shimmer_view_container.startShimmer()
    }

    override fun onPause() {
        shimmer_view_container.stopShimmer()
        super.onPause()
    }
}