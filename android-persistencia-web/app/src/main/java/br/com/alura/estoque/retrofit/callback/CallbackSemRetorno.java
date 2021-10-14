package br.com.alura.estoque.retrofit.callback;

import static br.com.alura.estoque.retrofit.callback.RespostasCallback.FALHA_NA_COMUNICACAO;
import static br.com.alura.estoque.retrofit.callback.RespostasCallback.RESPOSTA_NAO_SUCEDIDA;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallbackSemRetorno implements Callback<Void> {


    private final RespostaCallback callback;

    public CallbackSemRetorno(RespostaCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onResponse(Call<Void> call, Response<Void> response) {
        if(response.isSuccessful()){
            callback.quandoSucesso();
        }else{
            callback.quandoFalha(RESPOSTA_NAO_SUCEDIDA);
        }
    }

    @Override
    public void onFailure(Call<Void> call, Throwable t) {
        callback.quandoFalha(FALHA_NA_COMUNICACAO + t.getMessage());
    }

    public interface RespostaCallback{
        void quandoSucesso();
        void quandoFalha(String erro);
    }
}
