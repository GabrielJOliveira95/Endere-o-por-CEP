package com.cursoandroid.oliveiragabriel.helloworld

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CallCep {

    @GET("ws/{cep}/json/")

    fun cep(@Path("cep") cep: String): Call<CepModel>


}