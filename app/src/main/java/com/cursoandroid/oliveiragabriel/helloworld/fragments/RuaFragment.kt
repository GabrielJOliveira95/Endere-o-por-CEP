package com.cursoandroid.oliveiragabriel.helloworld.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cursoandroid.oliveiragabriel.helloworld.R
import com.cursoandroid.oliveiragabriel.helloworld.adpter.AdapterCep
import com.cursoandroid.oliveiragabriel.helloworld.model.EnderecoApiModel
import com.cursoandroid.oliveiragabriel.helloworld.service.CallCep
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_rua.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * A simple [Fragment] subclass.
 */
class RuaFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_rua, container, false)

        //Components
        val spinner = view.findViewById<Spinner>(R.id.spinner)
        val estados = arrayListOf(
            "AC",
            "AL",
            "AP",
            "AM",
            "BA",
            "CE",
            "DS",
            "ES",
            "GO",
            "MA",
            "MT",
            "MS",
            "MG",
            "PA",
            "PB",
            "PR",
            "PE",
            "PI",
            "RJ",
            "RN",
            "RS",
            "RO",
            "RR",
            "SC",
            "SP",
            "SE",
            "TO"
        )
        val btn = view.findViewById<Button>(R.id.btn_pesquisar_cep)
        val editTextRua = view.findViewById<EditText>(R.id.editTextRua)
        val editTextCidade = view.findViewById<EditText>(R.id.editTextCidade)
        val textInputLayoutCidade = view.findViewById<TextInputLayout>(R.id.textInputLayoutCidade)
        val textInputLayoutRua = view.findViewById<TextInputLayout>(R.id.textInputLayoutRua)

        //Spinner configuration
        val adapter =
            ArrayAdapter(this@RuaFragment.context, android.R.layout.simple_spinner_item, estados)
        spinner.adapter = adapter

        btn.setOnClickListener {

            validarDados()

        }

        return view
    }

    fun validarDados() {
        val uf: String = spinner.selectedItem.toString()
        val rua: String = editTextRua.text.toString()
        val cidade: String = editTextCidade.text.toString()

        when {
            editTextRua.text.toString().equals("") -> {
                textInputLayoutRua?.isErrorEnabled
                textInputLayoutRua?.error = "Digite o endereço"
                textInputLayoutCidade?.isErrorEnabled = false


            }
            editTextCidade.text.toString().equals("") -> {
                textInputLayoutRua?.isErrorEnabled = false
                textInputLayoutCidade?.isErrorEnabled
                textInputLayoutCidade?.error = "Digite a cidade"

            }
            else -> {
                textInputLayoutRua?.isErrorEnabled = false
                textInputLayoutCidade?.isErrorEnabled = false
                val url = "$uf/$cidade/$rua"
                buscarEndereco(url)
            }
        }


    }


    fun buscarEndereco(url: String) {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://viacep.com.br/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val callCep: CallCep = retrofit.create(CallCep::class.java)
        val callEndereco = callCep.buscarCepApi(url)
        callEndereco.enqueue(object : Callback<List<EnderecoApiModel>> {
            override fun onResponse(
                call: Call<List<EnderecoApiModel>>,
                response: Response<List<EnderecoApiModel>>
            ) {
                if (response.isSuccessful) {
                    val enderecoApiList: List<EnderecoApiModel>? = response.body()

                    if (enderecoApiList.isNullOrEmpty()) {
                        Toast.makeText(
                            this@RuaFragment.context,
                            "Endereço não localizado",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    val recyclerViewCep = view?.findViewById<RecyclerView>(R.id.recyclerview)
                    recyclerViewCep!!.visibility = View.VISIBLE
                    recyclerViewCep.adapter = AdapterCep(enderecoApiList)
                    recyclerViewCep.layoutManager = LinearLayoutManager(this@RuaFragment.context)
                    recyclerViewCep.hasFixedSize()


                }
            }

            override fun onFailure(call: Call<List<EnderecoApiModel>>, t: Throwable) {

            }
        })

    }


}
