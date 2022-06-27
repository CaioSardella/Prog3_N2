package br.edu.femass.Model.Compra;

import lombok.Data;

@Data
public class Item {
    private Long id;
    private int id_pedido;
    private int id_produto;
    private String nome_produto;
    private int qtd_item;
    private float precototal;

    public String toString(){
        return this.id_pedido + "-" + this.nome_produto + ": R$" + this.precototal;
    }


}
