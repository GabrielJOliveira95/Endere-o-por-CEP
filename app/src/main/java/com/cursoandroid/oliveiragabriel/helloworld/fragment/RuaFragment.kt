package com.cursoandroid.oliveiragabriel.helloworld.fragment


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
import com.cursoandroid.oliveiragabriel.helloworld.model.CepModel
import com.cursoandroid.oliveiragabriel.helloworld.service.CallCep
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


        //Spinner configuration
        val adapter =
            ArrayAdapter(this@RuaFragment. context, android.R.layout.simple_spinner_item, estados)
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


        val url = "$uf/$cidade/$rua"
        buscarEndereco(url)

    }


    fun buscarEndereco(url: String) {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://viacep.com.br/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val callCep: CallCep = retrofit.create(CallCep::class.java)
        val callEndereco = callCep.buscarCepApi(url)
        callEndereco.enqueue(object : Callback<List<CepModel>> {
            override fun onResponse(
                call: Call<List<CepModel>>,
                response: Response<List<CepModel>>
            ) {
                if (response.isSuccessful) {
                    val cepList: List<CepModel>? = response.body()

                    if (cepList.isNullOrEmpty()){
                        Toast.makeText(this@RuaFragment.context, "Endereço não localizado", Toast.LENGTH_LONG).show()
                    }

                    val recyclerViewCep = view?.findViewById<RecyclerView>(R.id.recyclerview)
                    recyclerViewCep!!.visibility = View.VISIBLE
                    recyclerViewCep.adapter = AdapterCep(cepList)
                    recyclerViewCep.layoutManager = LinearLayoutManager(this@RuaFragment.context)
                    recyclerViewCep.hasFixedSize()


                }
            }

            override fun onFailure(call: Call<List<CepModel>>, t: Throwable) {

            }
        })

    }








}
