package br.edu.femass.Dao;


import br.edu.femass.Model.Compra.Item;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ItemDao extends DaoPostgres {

    public List<Item> listar(int id) throws Exception {
        String sql = "select * from item where id_pedido = ? order by nome_produto";
        PreparedStatement ps = getPreparedStatement(sql, false);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        List<Item> items = new ArrayList<Item>();
        while (rs.next()) {
            Item item = new Item();
            item.setId(rs.getLong("id"));
            item.setNome_produto(rs.getString("nome_produto"));
            item.setId_pedido(rs.getInt("id_pedido"));
            item.setId_produto(rs.getInt("id_produto"));
            item.setQtd_item(rs.getInt("qtd_item"));
            item.setPrecototal(rs.getFloat("precototal"));

            items.add(item);
        }

        return items;
    }


    public void gravar(Item value) throws Exception {
        String sql = "INSERT INTO item (id_pedido, id_produto, nome_produto, qtd_item, precototal) VALUES (?,?,?,?,?)";
        PreparedStatement ps = getPreparedStatement(sql, true);
        ps.setInt(1, value.getId_pedido());
        ps.setInt(2, value.getId_produto());
        ps.setString(3, value.getNome_produto());
        ps.setInt(4, value.getQtd_item());
        ps.setFloat(5, value.getPrecototal());

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        value.setId(rs.getLong(1));


    }


    public void excluir(Item value) throws Exception {
        String sql = "delete from item where id = ?";
        PreparedStatement ps = getPreparedStatement(sql, false);
        ps.setLong(1, value.getId());
        ps.executeUpdate();
    }
}
