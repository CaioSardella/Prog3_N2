package br.edu.femass.Model.Cadastro;

import br.edu.femass.Model.Cadastro.Fornecedor;
import lombok.Data;

@Data
public class Produto {
    private Long id;
    private String nome;
    private float precoComp;
    private float precoVend;
    private int tamanho;
    private int qtdProduto;
    private Fornecedor fornecedor;

    public String toString(){
        return this.id +". "+this.nome + " x" + this.getQtdProduto();
    }

}
