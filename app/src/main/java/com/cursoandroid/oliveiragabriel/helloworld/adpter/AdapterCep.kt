package com.cursoandroid.oliveiragabriel.helloworld.adpter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cursoandroid.oliveiragabriel.helloworld.R
import com.cursoandroid.oliveiragabriel.helloworld.model.EnderecoApiModel


class AdapterCep(val enderecoApiModel: List<EnderecoApiModel>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {



    var list: List<EnderecoApiModel>? = listOf()

    init {
        this.list = enderecoApiModel
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater: View = LayoutInflater.from(parent.context).inflate(R.layout.recyclerviewlayout, parent, false)

        return CepViewHolder(layoutInflater)
    }

    override fun getItemCount(): Int {

        return list?.size!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var resultados = list?.get(position)

        if (holder is CepViewHolder){
            holder.layout_rua.text = "Logradouro: " + resultados!!.logradouro
            holder.layout_bairro.text = "Bairro: " + resultados.bairro
            holder.layout_cidade.text = "Cidade: " + resultados.localidade
            holder.layout_estado.text = "Estado: " + resultados.uf
            holder.layout_ibge.text = "IBGE: " + resultados.ibge
            holder.layout_cep.text = "CEP: " + resultados.cep
        }

    }

}

class CepViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var layout_rua = itemView.findViewById<TextView>(R.id.layout_rua)
    var layout_bairro = itemView.findViewById<TextView>(R.id.layout_bairro)
    var layout_cidade = itemView.findViewById<TextView>(R.id.layout_cidade)
    var layout_estado = itemView.findViewById<TextView>(R.id.layout_estado)
    var layout_ibge = itemView.findViewById<TextView>(R.id.layout_ibge)
    var layout_cep = itemView.findViewById<TextView>(R.id.layout_cep)
}