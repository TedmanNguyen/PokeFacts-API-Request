package com.example.pokefacts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    var pokeImageURL = ""
    var pokeName = ""
    var pokeWeight = ""




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getPokemonImage()
        val pokeButton = findViewById<Button>(R.id.pokeButton)
        val pokeImageView = findViewById<ImageView>(R.id.pokeImage)
        val pokeTextName = findViewById<TextView>(R.id.pokeName)
        val pokeTextWeight = findViewById<TextView>(R.id.pokeWeight)


        getNextPokeImage(pokeButton, pokeImageView, pokeTextName, pokeTextWeight)

    }

    private fun getPokemonImage()
    {
        val poke = Random.nextInt(150)
        val client = AsyncHttpClient()
        client["https://pokeapi.co/api/v2/pokemon/$poke", object : JsonHttpResponseHandler()
        {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON)
            {
                pokeImageURL = json.jsonObject.getJSONObject("sprites").getString("front_default")
                pokeName = json.jsonObject.getString("name")
                pokeWeight = json.jsonObject.getString("weight")
                Log.d("Poke", "response successful$json")
                Log.d("pokeImageURL", "poke Image URL Set$json")
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
    private fun getNextPokeImage(button: Button, imageView: ImageView, textView: TextView, textView2: TextView)
    {
        button.setOnClickListener{
            getPokemonImage()
            textView.text = pokeName
            textView2.text = pokeWeight
            Glide.with(this)
                . load(pokeImageURL)
                .fitCenter()
                .into(imageView)
        }
    }
}

