package io.github.fuadreza.shimmerloading.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import io.github.fuadreza.shimmerloading.R
import io.github.fuadreza.shimmerloading.model.Recipe


/**
 * Dibuat dengan kerjakerasbagaiquda oleh Shifu pada tanggal 03/08/2020.
 *
 */

class RecipeListAdapter(private val context: Context, private val cartList: List<Recipe>): RecyclerView.Adapter<RecipeListAdapter.MyViewHolder>() {


    class MyViewHolder(view: View) : ViewHolder(view) {
        var name: TextView
        var description: TextView
        var price: TextView
        var chef: TextView
        var timestamp: TextView
        var thumbnail: ImageView

        init {
            name = view.findViewById(R.id.name)
            chef = view.findViewById(R.id.chef)
            description = view.findViewById(R.id.description)
            price = view.findViewById(R.id.price)
            thumbnail = view.findViewById(R.id.thumbnail)
            timestamp = view.findViewById(R.id.timestamp)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.recipe_list_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val recipe = cartList[position]!!
        holder.name.text = recipe.name
        holder.chef.text = "By " + recipe.chef
        holder.description.text = recipe.description
        holder.price.text = "Price: ₹" + recipe.price
        holder.timestamp.text = recipe.timestamp
        Glide.with(context)
            .load(recipe.thumbnail)
            .into(holder.thumbnail)
    }

    // recipe
    override fun getItemCount(): Int {
        return cartList.size
    }
}