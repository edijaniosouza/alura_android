package br.com.alura.agenda.model;

import static androidx.room.ForeignKey.*;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity
public class Telefone {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public String numero;
    public TipoTelefone tipo;

    @ForeignKey(
            entity = Aluno.class,
            parentColumns = "id",
            childColumns = "alunoId",
            onUpdate = CASCADE,
            onDelete = CASCADE
    )
    public int alunoId;

    public Telefone(String numero, TipoTelefone tipo) {
        this.numero = numero;
        this.tipo = tipo;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public TipoTelefone getTipo() {
        return tipo;
    }

    public void setTipo(TipoTelefone tipo) {
        this.tipo = tipo;
    }

    public int getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(int alunoId) {
        this.alunoId = alunoId;
    }
}
