package br.edu.femass.Model.Cadastro;

import lombok.Data;
import br.edu.femass.Model.Cadastro.Usuario;

@Data
public class Cliente extends Usuario {
    private String cpf;


    public Cliente(){
        this.transacao = true;
    }

    public String toString(){
        return "(Cliente) "+ this.id +". "+this.nome;
    }

}
