package br.edu.femass.Model.Compra;

import lombok.Data;
import java.util.List;
import  br.edu.femass.Model.Cadastro.Usuario;

@Data
public class Pedido {

    private Long id;
    private int id_usuario;
    private float subtotal;
    private String nome_usuario;
    private String data;

    public String toString(){
        return this.id + "-" + this.nome_usuario + " Total: R$" + this.subtotal;
    }

}
