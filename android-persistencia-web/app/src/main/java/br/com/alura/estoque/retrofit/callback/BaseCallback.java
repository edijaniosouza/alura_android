package br.com.alura.estoque.retrofit.callback;

import static br.com.alura.estoque.retrofit.callback.RespostasCallback.FALHA_NA_COMUNICACAO;
import static br.com.alura.estoque.retrofit.callback.RespostasCallback.RESPOSTA_NAO_SUCEDIDA;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class BaseCallback<T> implements Callback<T> {

    private final RespostaCallback<T> callback;

    public BaseCallback(RespostaCallback<T> callback) {
        this.callback = callback;
    }

    @Override
    @EverythingIsNonNull
    public void onResponse(Call<T> call, Response<T> response) {

        if(response.isSuccessful()){
            T resultado = response.body();

            if(resultado != null){
                callback.quandoSucesso(resultado);
            }
        } else{
            callback.quandoFalha(RESPOSTA_NAO_SUCEDIDA);
        }
    }

    @Override
    @EverythingIsNonNull
    public void onFailure(Call<T> call, Throwable t) {
        callback.quandoFalha(FALHA_NA_COMUNICACAO + t.getMessage());
    }

    public interface RespostaCallback<T>{
        void quandoSucesso(T resultado);
        void quandoFalha(String erro);
    }


}
