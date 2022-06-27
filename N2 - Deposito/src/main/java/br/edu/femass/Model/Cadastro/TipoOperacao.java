package br.edu.femass.Model.Cadastro;

public enum TipoOperacao {
    Consulta ("Consulta"),
    Cadastro("Cadastro");

    private String nome;
    TipoOperacao (String nome){
        this.nome=nome;
    }
}
