package br.com.fellipe.pokeagenda

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import br.com.fellipe.pokeagenda.api.PokemonAPI
import br.com.fellipe.pokeagenda.model.Pokemon
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnPesquisar.setOnClickListener({
            pesquisarPokemon()
        })
    }

    fun pesquisarPokemon() {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://pokeapi.co")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val api = retrofit.create(PokemonAPI::class.java)
        api.buscarPokemon(edPesquisarPokemon.text.toString().toInt()).enqueue(object : Callback<Pokemon> {
            override fun onResponse(call: Call<Pokemon>?, response: Response<Pokemon>?) {
                tvNomePokemon.setText(response?.body()?.name.toString())
                Picasso.with(applicationContext)
                        .load(response?.body()?.sprites?.frontDefault)
                        .into(ivPokemon);
            }

            override fun onFailure(call: Call<Pokemon>?, t: Throwable?) {
                tvNomePokemon.setText("ERRO")
            }
        })
    }
}
