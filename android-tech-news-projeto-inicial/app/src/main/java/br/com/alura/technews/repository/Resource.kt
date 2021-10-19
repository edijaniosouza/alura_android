package br.com.alura.technews.repository

data class Resource<T>(
    val dado : T?,
    val erro : String? = null
)

fun <T> criaResourceDeFalha(
    liveDataAtual: Resource<T?>?,
    erro: String?
): Resource<T?> {
    if (liveDataAtual != null) {
        return Resource(dado = liveDataAtual.dado, erro = erro)
    }
    return Resource(dado = null, erro = erro)
}