package br.edu.femass.Dao;

import br.edu.femass.Model.Cadastro.Produto;
import br.edu.femass.Model.Compra.Pedido;

import javax.xml.crypto.Data;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PedidoDao extends DaoPostgres {

    public List<Pedido> listar() throws Exception {
        String sql = "select * from pedido order by id";
        PreparedStatement ps = getPreparedStatement(sql, false);
        ResultSet rs = ps.executeQuery();

        List<Pedido> pedidos = new ArrayList<Pedido>();
        while (rs.next()) {
            Pedido pedido = new Pedido();
            pedido.setId(rs.getLong("id"));
            pedido.setId_usuario(rs.getInt("id_usuario"));
            pedido.setSubtotal(rs.getFloat("subtotal"));
            pedido.setNome_usuario(rs.getString("nome_usuario"));
            pedido.setData(rs.getString("data"));

            pedidos.add(pedido);
        }

        return pedidos;
    }

    public List <Pedido> filtrarData(String data) throws Exception{
        String sql = "select * from pedido where data = ? order by id";
        PreparedStatement ps = getPreparedStatement(sql, false);
        ps.setString(1, data);
        ResultSet rs = ps.executeQuery();

        List<Pedido> pedidos = new ArrayList<Pedido>();
        while (rs.next()) {
            Pedido pedido = new Pedido();
            pedido.setId(rs.getLong("id"));
            pedido.setId_usuario(rs.getInt("id_usuario"));
            pedido.setSubtotal(rs.getFloat("subtotal"));
            pedido.setNome_usuario(rs.getString("nome_usuario"));
            pedido.setData(rs.getString("data"));

            pedidos.add(pedido);
        }
        return pedidos;
    }

    public void gravar(Pedido value) throws Exception {
        String sql = "INSERT INTO pedido (id_usuario, subtotal, nome_usuario, data) VALUES (?,?,?,?)";
        PreparedStatement ps = getPreparedStatement(sql, true);
        ps.setInt(1, value.getId_usuario());
        ps.setFloat(2, value.getSubtotal());
        ps.setString(3, value.getNome_usuario());
        ps.setString(4, value.getData());

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        value.setId(rs.getLong(1));

    }

    public void atualizartotal (Pedido value) throws Exception {
        String sql = "update pedido set subtotal = ? where id = ?";
        PreparedStatement ps = getPreparedStatement(sql, false);
        ps.setFloat(1, value.getSubtotal());
        ps.setLong(2, value.getId());
        ps.executeUpdate();
    }


    public void excluir(Pedido value) throws Exception {
        String sql = "delete from pedido where id = ?";
        PreparedStatement ps = getPreparedStatement(sql, false);
        ps.setLong(1, value.getId());
        ps.executeUpdate();
    }
}
