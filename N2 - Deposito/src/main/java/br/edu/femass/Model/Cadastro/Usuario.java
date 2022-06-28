package br.edu.femass.Model.Cadastro;

import lombok.Data;

@Data
public abstract class Usuario {
    public Long id;
    public String nome;


    public String toString() {
        return this.nome;
    }
}
