package com.example.randompet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

class MainActivity : AppCompatActivity() {
    var petImageURL = ""

    var pokemonName = ""

    var pokemonTypes = ""

    private var maxPokemonId = 807  // Set the maximum Pokemon ID based on the available Pokemon (adjust this as needed).

    private fun getRandomPokemonId(): Int {
        // Generate a random Pokemon ID within the available range (1 to maxPokemonId).
        return (1..maxPokemonId).random()
    }

    private fun getPokemonURL(pokemonId: Int) {
        val client = AsyncHttpClient()
        client["https://pokeapi.co/api/v2/pokemon/$pokemonId", object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                Log.d("Dog", "response successful$json")

                petImageURL = json.jsonObject
                    .getJSONObject("sprites")
                    .getString("front_default")

                pokemonName = json.jsonObject
                    .getString("name")

                val types = json.jsonObject.getJSONArray("types")
                val typeStringBuilder = StringBuilder()
                for (i in 0 until types.length()) {
                    val typeName = types.getJSONObject(i).getJSONObject("type").getString("name")
                    typeStringBuilder.append(typeName)
                    if (i < types.length() - 1) {
                        typeStringBuilder.append(", ")
                    }
                }
                pokemonTypes = typeStringBuilder.toString()

            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("Dog Error", errorResponse)
            }
        }]

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        getPokemonURL(1)
        Log.d("petImageURL", "pet image URL set")

        val button = findViewById<Button>(R.id.petButton)
        val image = findViewById<ImageView>(R.id.petImage)
        val name = findViewById<TextView>(R.id.pokemonNameView)
        val typeInit = findViewById<TextView>(R.id.typeView)

        getNextPokemon(button,image, name, typeInit)
    }

    private fun getNextPokemon(button: Button, imageView: ImageView, name: TextView, type: TextView) {
        button.setOnClickListener {
            getPokemonURL(getRandomPokemonId())

            Glide.with(this)
                .load(petImageURL)
                .fitCenter()
                .into(imageView)

            name.text = "Name: "+ pokemonName
            type.text = "Types: "+ pokemonTypes
        }

    }
}