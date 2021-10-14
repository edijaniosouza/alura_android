package br.com.alura.agenda.asynctask;

import java.util.List;

import br.com.alura.agenda.database.dao.AlunoDAO;
import br.com.alura.agenda.database.dao.TelefoneDAO;
import br.com.alura.agenda.model.Aluno;
import br.com.alura.agenda.model.Telefone;
import br.com.alura.agenda.model.TipoTelefone;

public class EditaAlunoTask extends BaseAlunoComTelefoneTask{
    private final AlunoDAO alunoDAO;
    private final TelefoneDAO telefoneDAO;
    private final Aluno aluno;
    private final Telefone numeroFixo;
    private final Telefone numeroCelular;
    private final List<Telefone> telefonesAluno;

    public EditaAlunoTask(AlunoDAO alunoDAO, TelefoneDAO telefoneDAO,
                          Aluno aluno,
                          Telefone numeroFixo,
                          Telefone numeroCelular,
                          List<Telefone> telefonesAluno, FinalizadaListener listener) {
        super(listener);
        this.alunoDAO = alunoDAO;
        this.telefoneDAO = telefoneDAO;
        this.aluno = aluno;
        this.numeroFixo = numeroFixo;
        this.numeroCelular = numeroCelular;
        this.telefonesAluno = telefonesAluno;

    }


    @Override
    protected Void doInBackground(Void... voids) {
        alunoDAO.edita(aluno);
        vinculaAlunoComTelefone(aluno.getId() ,numeroFixo, numeroCelular);
        atualizaIdsDosTelefones(numeroFixo, numeroCelular);
        telefoneDAO.atualiza(numeroFixo, numeroCelular);
        return null;
    }



    private void atualizaIdsDosTelefones(Telefone telefoneFixo, Telefone telefoneCelular) {
        for (Telefone telefone :
                telefonesAluno) {
            if (telefone.getTipo() == TipoTelefone.FIXO){
                telefoneFixo.setId(telefone.getId());
            } else {
                telefoneCelular.setId(telefone.getId());
            }
        }
    }

}
