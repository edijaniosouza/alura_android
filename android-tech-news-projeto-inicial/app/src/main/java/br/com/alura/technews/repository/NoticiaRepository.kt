package br.com.alura.technews.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.alura.technews.asynctask.BaseAsyncTask
import br.com.alura.technews.database.dao.NoticiaDAO
import br.com.alura.technews.model.Noticia
import br.com.alura.technews.retrofit.webclient.NoticiaWebClient

class NoticiaRepository(
    private val dao: NoticiaDAO,
    private val webclient: NoticiaWebClient = NoticiaWebClient()
) {

    private val liveData = MutableLiveData<Resource<List<Noticia>?>>()

    fun buscaTodos(): LiveData<Resource<List<Noticia>?>> {

        val atualizaNoticia: (List<Noticia>) -> Unit = {
            liveData.value = Resource(it)
        }
        buscaInterno(quandoSucesso = atualizaNoticia)
        buscaNaApi(quandoSucesso = atualizaNoticia, quandoFalha = {
            val liveDataAtual = liveData.value
            val resource = criaResourceDeFalha<List<Noticia>?>(liveDataAtual, it)
            liveData.value = resource
        })
        return liveData
    }


    fun salva(
        noticia: Noticia
    ): LiveData<Resource<Void?>> {
        val liveDataSave = MutableLiveData<Resource<Void?>>()
        salvaNaApi(noticia, quandoSucesso = {
            liveDataSave.value = Resource(null)
        }, quandoFalha = {
            liveDataSave.value = Resource(null, it)
        })
        return liveDataSave
    }

    fun remove(
        noticia: Noticia
    ): LiveData<Resource<Void?>> {
        val liveDataRemove = MutableLiveData<Resource<Void?>>()
        removeNaApi(noticia, quandoSucesso = {
            liveDataRemove.value = Resource(null)
        }, quandoFalha = {
            liveDataRemove.value = Resource(null, it)
        })
        return liveDataRemove
    }

    fun edita(
        noticia: Noticia
    ): LiveData<Resource<Void?>> {
        val liveDataEdita = MutableLiveData<Resource<Void?>>()
        editaNaApi(noticia, quandoSucesso = {
            liveDataEdita.value = Resource(null)
        }, quandoFalha = {
            liveDataEdita.value = Resource(null, it)
        })
        return liveDataEdita
    }

    fun buscaPorId(
        noticiaId: Long
    ): LiveData<Noticia?> = dao.buscaPorId(noticiaId)


    private fun buscaNaApi(
        quandoFalha: (erro: String?) -> Unit
    ) {
        webclient.buscaTodas(
            quandoSucesso = { noticiasNovas ->
                noticiasNovas?.let {
                    salvaInterno(noticiasNovas)
                }
            }, quandoFalha = quandoFalha
        )
    }

    private fun buscaInterno() : LiveData<List<Noticia>> {
            return dao.buscaTodos()
    }


    private fun salvaNaApi(
        noticia: Noticia,
        quandoSucesso: () -> Unit,
        quandoFalha: (erro: String?) -> Unit
    ) {
        webclient.salva(
            noticia,
            quandoSucesso = {
                it?.let { noticiaSalva ->
                    salvaInterno(noticiaSalva, quandoSucesso)
                }
            }, quandoFalha = quandoFalha
        )
    }

    private fun salvaInterno(
        noticias: List<Noticia>
    ) {
        BaseAsyncTask(
            quandoExecuta = {
                dao.salva(noticias)
            }, quandoFinaliza = { }
        ).execute()
    }

    private fun salvaInterno(
        noticia: Noticia,
        quandoSucesso: () -> Unit
    ) {
        BaseAsyncTask(quandoExecuta = {
            dao.salva(noticia)
        }, quandoFinaliza = {
                quandoSucesso()
        }).execute()
    }

    private fun removeNaApi(
        noticia: Noticia,
        quandoSucesso: () -> Unit,
        quandoFalha: (erro: String?) -> Unit
    ) {
        webclient.remove(
            noticia.id,
            quandoSucesso = {
                removeInterno(noticia, quandoSucesso)
            },
            quandoFalha = quandoFalha
        )
    }


    private fun removeInterno(
        noticia: Noticia,
        quandoSucesso: () -> Unit
    ) {
        BaseAsyncTask(quandoExecuta = {
            dao.remove(noticia)
        }, quandoFinaliza = {
            quandoSucesso()
        }).execute()
    }

    private fun editaNaApi(
        noticia: Noticia,
        quandoSucesso: () -> Unit,
        quandoFalha: (erro: String?) -> Unit
    ) {
        webclient.edita(
            noticia.id, noticia,
            quandoSucesso = { noticiaEditada ->
                noticiaEditada?.let {
                    salvaInterno(noticiaEditada, quandoSucesso)
                }
            }, quandoFalha = quandoFalha
        )
    }

}
