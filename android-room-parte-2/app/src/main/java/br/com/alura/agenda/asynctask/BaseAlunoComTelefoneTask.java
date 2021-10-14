package br.com.alura.agenda.asynctask;

import android.os.AsyncTask;

import br.com.alura.agenda.model.Telefone;

public abstract class BaseAlunoComTelefoneTask extends AsyncTask<Void, Void, Void> {

    protected final FinalizadaListener listener;

    protected BaseAlunoComTelefoneTask(FinalizadaListener listener) {
        this.listener = listener;
    }

    protected void vinculaAlunoComTelefone(int alunoId, Telefone... telefones) {
        for (Telefone telefone :
                telefones) {
            telefone.setAlunoId(alunoId);
        }
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        listener.finalizada();
    }

    public interface FinalizadaListener{
        void finalizada();
    }
}
