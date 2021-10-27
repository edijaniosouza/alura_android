package br.com.alura.technews.repository

data class Resource<T>(
    val dado : T?,
    val erro : String? = null
)
