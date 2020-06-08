package com.cursoandroid.oliveiragabriel.helloworld.service

import com.cursoandroid.oliveiragabriel.helloworld.model.CepModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ClassApi {
    var cep: String = ""


    val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://viacep.com.br/")
        .build()


    fun buscarPorCep(cep: String): CepModel? {
        val callCep: CallCep = retrofit.create(CallCep::class.java)
        val call: Call<CepModel> = callCep.buscarPorCepApi(cep)
        var cepModel: CepModel? = CepModel()
        call.enqueue(object : Callback<CepModel> {
            override fun onFailure(call: Call<CepModel>, t: Throwable) {

            }

            override fun onResponse(call: Call<CepModel>, response: Response<CepModel>) {

                cepModel = response.body()


            }
        })

        return cepModel


    }
}