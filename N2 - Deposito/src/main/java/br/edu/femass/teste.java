package br.edu.femass;

import br.edu.femass.Dao.ItemDao;
import br.edu.femass.Dao.PedidoDao;
import br.edu.femass.Dao.UsuarioDao;
import br.edu.femass.Model.Cadastro.Cliente;
import br.edu.femass.Model.Cadastro.Fornecedor;
import br.edu.femass.Model.Compra.Item;
import br.edu.femass.Model.Compra.Pedido;
import br.edu.femass.Model.Cadastro.Usuario;

import java.util.List;

public class teste {

    public static void main (String[] args){

    /*    Fornecedor f = new Fornecedor();
        f.setNome("Ambev");
        f.setCnpj("123456789");
        f.setTelefone("222521-0000");
        f.setEndereco("Rua dos Bobos");
        f.setNumCasa("0");

        try {
            new UsuarioDao().gravar(f);
        } catch (Exception e) {
            e.printStackTrace();
        }


        Cliente c = new Cliente();
        c.setNome("General Caju");
        c.setCpf("987654321");


        try {
            new UsuarioDao().gravar(c);
        } catch (Exception e) {
            e.printStackTrace();
        }


        List<Usuario> usuarios = null;
        try{
            usuarios = new UsuarioDao().listar();
            for(Usuario u: usuarios){
                System.out.println(u.getId());
                System.out.println(u.getNome());
                System.out.println(u.isTransacao());

            }
        }catch (Exception e){
            e.printStackTrace();
        }


        Pedido p = new Pedido();
        p.setId_usuario(2);


        try {
            new PedidoDao().gravar(p);
            System.out.println(p.getId());
            System.out.println(p.getId_usuario());
        } catch (Exception e) {
            e.printStackTrace();
        }


        Item i = new Item();
        i.setId_produto(3);
        i.setId_pedido(1);
        i.setQtd_item(2);
        i.setPrecototal(i.getQtd_item()*0F);

        try {
            new ItemDao().gravar(i);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Pedido p = new Pedido();
        p.setId_usuario(2);
        p.setData("25/06/2022");



        try {
            new PedidoDao().gravar(p);
            System.out.println(p.getId());
            System.out.println(p.getId_usuario());
        } catch (Exception e) {
            e.printStackTrace();
        }


        Pedido p = new Pedido();
        p.setId_usuario(2);
        p.setData("25/06/2022");
        p.setSubtotal(10);



        try {
            new PedidoDao().gravar(p);
            System.out.println(p.getId());
            System.out.println(p.getId_usuario());
        } catch (Exception e) {
            e.printStackTrace();
        }

        */

    }
}
