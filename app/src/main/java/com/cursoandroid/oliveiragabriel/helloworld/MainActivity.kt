package com.cursoandroid.oliveiragabriel.helloworld

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {
    var txt_rua : TextView? = null;
    var txt_bairo : TextView? = null
    var txt_cidade : TextView? = null
    var txt_estado : TextView? = null
    var txt_ibge : TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn = findViewById<Button>(R.id.btnCep)
        txt_rua = findViewById(R.id.txt_rua)
        txt_bairo = findViewById(R.id.txt_bairro)
        txt_cidade = findViewById(R.id.txt_cidade)
        txt_estado = findViewById(R.id.txt_estado)
        txt_ibge = findViewById(R.id.txt_ibge)

        val editText = findViewById<EditText>(R.id.cep)
        val fab = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        btn.setOnClickListener(View.OnClickListener {

            val cep = editText.text.toString()

            if (cep.equals("")) {
                Toast.makeText(this@MainActivity, "Digite um CEP", Toast.LENGTH_LONG).show()
            } else {
                buscaCep(cep)
            }
        })

        fab.setOnClickListener(View.OnClickListener {
            this.txt_rua?.setText("Logradouro:")
            this.txt_bairo?.setText("Bairro:")
            this.txt_cidade?.setText("Cidade:")
            this.txt_estado?.setText("Estado:")
            this.txt_ibge?.setText("IBGE:")
            editText.setText("")

        })


    }

    fun buscaCep(cep: String) {

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://viacep.com.br/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val callCep = retrofit.create(CallCep::class.java)
        val call: Call<CepModel> = callCep.cep(cep)

        call.enqueue(object : Callback<CepModel> {
            override fun onFailure(call: Call<CepModel>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<CepModel>, response: Response<CepModel>) {
                val body = response.body()
                txt_rua?.setText("Logradouro: ${body?.logradouro}")
                txt_bairo?.setText("Bairro: ${body?.bairro}")
                txt_cidade?.setText("Cidade: ${body?.localidade}")
                txt_estado?.setText("Estado: ${body?.uf}")
                txt_ibge?.setText("IBGE: ${body?.ibge}")
            }


        }
        )
    }
}
