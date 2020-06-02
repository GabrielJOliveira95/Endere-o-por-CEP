package com.cursoandroid.oliveiragabriel.helloworld.service

import com.cursoandroid.oliveiragabriel.helloworld.model.CepModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CallCep {

    @GET("ws/{cep}/json/")

    fun cep(@Path("cep") cep: String): Call<CepModel>



}