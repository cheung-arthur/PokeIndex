package com.example.randompet

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PokemonAdapter
    private var pokemonList = mutableListOf<Pokemon>()

    private var maxPokemonId = 807

    private fun getRandomPokemonId(): Int {
        return (1..maxPokemonId).random()
    }

    private fun getPokemonURL(pokemonId: Int) {
        val client = AsyncHttpClient()
        client["https://pokeapi.co/api/v2/pokemon/$pokemonId", object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                val imageUrl = json.jsonObject.getJSONObject("sprites").getString("front_default")
                val name = json.jsonObject.getString("name")
                val typesArray = json.jsonObject.getJSONArray("types")
                val typesList = (0 until typesArray.length()).map {
                    typesArray.getJSONObject(it).getJSONObject("type").getString("name")
                }
                val types = typesList.joinToString(", ")
                val pokemon = Pokemon(imageUrl, name, types)

                pokemonList.add(pokemon)
                adapter.notifyDataSetChanged()
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("Pokemon Error", errorResponse)
            }
        }]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        adapter = PokemonAdapter(pokemonList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        for (i in 1..20) {
            getNextPokemon()
        }

        val btnGenerate: Button = findViewById(R.id.button)
        btnGenerate.setOnClickListener {
            regeneratePokemonList()
        }
    }

    private fun getNextPokemon() {
        val randomPokemonId = getRandomPokemonId()
        getPokemonURL(randomPokemonId)
    }

    private fun regeneratePokemonList() {
        pokemonList.clear()
        for (i in 1..20) {
            getNextPokemon()
        }
    }
}
