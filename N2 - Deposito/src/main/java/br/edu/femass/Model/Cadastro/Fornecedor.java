package br.edu.femass.Model.Cadastro;

import br.edu.femass.Model.Cadastro.Usuario;
import lombok.Data;

@Data
public class Fornecedor extends Usuario {
    private String cnpj;
    protected String telefone;
    protected String endereco;
    protected String numCasa;

    public Fornecedor(){
        this.transacao = false;
    }

    public String toString(){
        return "(Fornecedor) "+ this.id +". "+this.nome;
    }
}
