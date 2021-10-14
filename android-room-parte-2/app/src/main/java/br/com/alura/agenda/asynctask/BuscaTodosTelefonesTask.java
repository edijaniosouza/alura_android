package br.com.alura.agenda.asynctask;

import android.os.AsyncTask;

import java.util.List;

import br.com.alura.agenda.database.dao.TelefoneDAO;
import br.com.alura.agenda.model.Telefone;

public class BuscaTodosTelefonesTask extends AsyncTask<Void,Void, List<Telefone>> {

    private final TelefoneDAO telefoneDAO;
    private final int alunoId;
    private final TelefonesDoAlunoEncontradosListener listener;

    public BuscaTodosTelefonesTask(TelefoneDAO telefoneDAO, int alunoId, TelefonesDoAlunoEncontradosListener listener) {
        this.telefoneDAO = telefoneDAO;
        this.alunoId = alunoId;
        this.listener = listener;
    }

    @Override
    protected List<Telefone> doInBackground(Void... voids) {
        return telefoneDAO.buscaTodosTelefones(alunoId);
    }

    @Override
    protected void onPostExecute(List<Telefone> telefones) {
        super.onPostExecute(telefones);
        listener.quandoEncontrados(telefones);
    }

    public interface TelefonesDoAlunoEncontradosListener{
        void quandoEncontrados(List<Telefone> telefones);
    }
}
