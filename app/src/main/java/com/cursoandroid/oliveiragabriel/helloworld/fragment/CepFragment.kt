package com.cursoandroid.oliveiragabriel.helloworld.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.cursoandroid.oliveiragabriel.helloworld.R
import com.cursoandroid.oliveiragabriel.helloworld.model.CepModel
import com.cursoandroid.oliveiragabriel.helloworld.model.Erro
import com.cursoandroid.oliveiragabriel.helloworld.service.CallCep
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_cep.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * A simple [Fragment] subclass.
 */
class CepFragment : Fragment() {
    var txt_rua: TextView? = null
    var txt_bairo: TextView? = null
    var txt_cidade: TextView? = null
    var txt_estado: TextView? = null
    var txt_ibge: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_cep, container, false)
        val btn = view.findViewById<Button>(R.id.btnCep)
        txt_rua = view.findViewById(R.id.txt_rua)
        txt_bairo = view.findViewById(R.id.txt_bairro)
        txt_cidade = view.findViewById(R.id.txt_cidade)
        txt_estado = view.findViewById(R.id.txt_estado)
        txt_ibge = view.findViewById(R.id.txt_ibge)


        val editText = view.findViewById<EditText>(R.id.cepEdit)
        val fab = view.findViewById<FloatingActionButton>(R.id.floatingActionButton)
        btn.setOnClickListener(View.OnClickListener {

            val cep = editText.text.toString()

            if (cep == "") {
                Toast.makeText(this@CepFragment.context, "Digite um CEP", Toast.LENGTH_LONG).show()
            } else {
                buscaCep(cep)


            }
        })

        fab.setOnClickListener(View.OnClickListener {
            this.txt_rua?.text = ""
            this.txt_bairo?.text = ""
            this.txt_cidade?.text = ""
            this.txt_estado?.text = ""
            this.txt_ibge?.text = ""
            this.cepEdit?.setText("")
        })

        return view
    }


    fun buscaCep(cep: String) {

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://viacep.com.br/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val callCep = retrofit.create(CallCep::class.java)
        val call: Call<CepModel> = callCep.buscarPorCepApi(cep)

        call.enqueue(object : Callback<CepModel> {
            override fun onFailure(call: Call<CepModel>, t: Throwable) {
                Toast.makeText(this@CepFragment.context, t.message, Toast.LENGTH_SHORT).show()
            }


            override fun onResponse(call: Call<CepModel>, response: Response<CepModel>) {
                val body = response.body()

                if (erro(cep)) {
                    erro(cep)
                } else {
                    txt_rua?.text = "Logradouro: ${body?.logradouro}"
                    txt_bairo?.text = "Bairro: ${body?.bairro}"
                    txt_cidade?.text = "Cidade: ${body?.localidade}"
                    txt_estado?.text = "Estado: ${body?.uf}"
                    txt_ibge?.text = "IBGE: ${body?.ibge}"
                }
            }


        }
        )
    }

    fun erro(cep: String): Boolean {

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://viacep.com.br/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var boolean: Boolean = false

        val callErro = retrofit.create(CallCep::class.java)
        val call: Call<Erro> = callErro.erro(cep)
        call.enqueue(object : Callback<Erro> {
            override fun onFailure(call: Call<Erro>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call<Erro>, response: Response<Erro>) {
                val erro: Erro? = response.body()
                if (erro?.erro == true) {
                    boolean = true
                    txt_rua?.text = ""
                    txt_bairo?.text = ""
                    txt_cidade?.text = ""
                    txt_estado?.text = ""
                    txt_ibge?.text = ""
                    Toast.makeText(
                        this@CepFragment.context,
                        "CEP não encontrado",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })

        return boolean
    }


}
