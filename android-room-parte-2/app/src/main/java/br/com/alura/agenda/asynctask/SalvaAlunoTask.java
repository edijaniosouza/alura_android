package br.com.alura.agenda.asynctask;

import android.os.AsyncTask;

import br.com.alura.agenda.database.dao.AlunoDAO;
import br.com.alura.agenda.database.dao.TelefoneDAO;
import br.com.alura.agenda.model.Aluno;
import br.com.alura.agenda.model.Telefone;

public class SalvaAlunoTask extends BaseAlunoComTelefoneTask {
    private final AlunoDAO alunoDAO;
    private final Aluno aluno;
    private final TelefoneDAO telefoneDAO;
    private Telefone numeroFixo;
    private Telefone numeroCelular;

    public SalvaAlunoTask(AlunoDAO alunoDAO, Aluno aluno, TelefoneDAO telefoneDAO, Telefone numeroFixo, Telefone numeroCelular, FinalizadaListener listener) {
        super(listener);
        this.alunoDAO = alunoDAO;
        this.aluno = aluno;
        this.telefoneDAO = telefoneDAO;
        this.numeroFixo = numeroFixo;
        this.numeroCelular = numeroCelular;
    }


    @Override
    protected Void doInBackground(Void... voids) {
        int alunoId = alunoDAO.salva(aluno).intValue();
        vinculaAlunoComTelefone(alunoId, numeroFixo, numeroCelular);
        telefoneDAO.salvar(numeroFixo, numeroCelular);
        return null;
    }


}
