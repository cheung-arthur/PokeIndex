package com.example.randompet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PokemonAdapter(private var pokemonList: List<Pokemon>) :
    RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {

    var onBottomReached: (() -> Unit)? = null

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.pokemonImage)
        val name: TextView = view.findViewById(R.id.pokemonName)
        val types: TextView = view.findViewById(R.id.pokemonTypes)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pokemon, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pokemon = pokemonList[position]
        Glide.with(holder.image.context)
            .load(pokemon.imageUrl)
            .into(holder.image)
        holder.name.text = "Name: " + pokemon.name.capitalize()
        holder.types.text = "Types: " + pokemon.types.capitalize()

        if (position == pokemonList.size - 1) {
            onBottomReached?.invoke()
        }

        if (pokemon.types.contains("fire", ignoreCase = true)) {
            applyStyle(holder.types, R.style.PokemonFireType)
        } else if (pokemon.types.contains("water", ignoreCase = true)){
            applyStyle(holder.types, R.style.PokemonWaterType)

        }
        else if (pokemon.types.contains("grass", ignoreCase = true)){
            applyStyle(holder.types, R.style.PokemonGrassType)

        }
        else if (pokemon.types.contains("electric", ignoreCase = true)){
            applyStyle(holder.types, R.style.PokemonElectricType)

        }
        else if (pokemon.types.contains("ground", ignoreCase = true)){
            applyStyle(holder.types, R.style.PokemonGroundType)

        }
        else if (pokemon.types.contains("poison", ignoreCase = true)){
            applyStyle(holder.types, R.style.PokemonPoisonType)

        }
        else if (pokemon.types.contains("fairy", ignoreCase = true)){
            applyStyle(holder.types, R.style.PokemonFairyType)

        }
        else if (pokemon.types.contains("psychic", ignoreCase = true)){
            applyStyle(holder.types, R.style.PokemonPsychicType)

        }
        else if (pokemon.types.contains("ghost", ignoreCase = true)){
            applyStyle(holder.types, R.style.PokemonGhostType)

        }
        else if (pokemon.types.contains("dark", ignoreCase = true)){
            applyStyle(holder.types, R.style.PokemonDarkType)

        }
        else if (pokemon.types.contains("fighting", ignoreCase = true)){
            applyStyle(holder.types, R.style.PokemonFightingType)

        }
        else if (pokemon.types.contains("ice", ignoreCase = true)){
            applyStyle(holder.types, R.style.PokemonIceType)

        }

    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    private fun applyStyle(textView: TextView, styleResId: Int) {
        val attributes = intArrayOf(android.R.attr.textColor)
        val typedArray = textView.context.obtainStyledAttributes(styleResId, attributes)

        try {
            val color = typedArray.getColor(0, -1)
            if (color != -1) {
                textView.setTextColor(color)
            }
            // If you have other attributes, retrieve and set them here
        } finally {
            typedArray.recycle()
        }
    }
}