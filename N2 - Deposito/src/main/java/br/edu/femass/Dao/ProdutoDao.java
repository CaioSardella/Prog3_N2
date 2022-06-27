package br.edu.femass.Dao;
import br.edu.femass.Model.Cadastro.Produto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDao extends DaoPostgres implements Dao<Produto> {
    @Override
    public List<Produto> listar() throws Exception {
        String sql = "select * from produto order by nome";
        PreparedStatement ps = getPreparedStatement(sql, false);
        ResultSet rs = ps.executeQuery();

        List<Produto> produtos = new ArrayList<Produto>();
        while (rs.next()) {
            Produto produto = new Produto();
            produto.setNome(rs.getString("nome"));
            produto.setId(rs.getLong("id"));
            produto.setPrecoComp(rs.getFloat("precocomp"));
            produto.setPrecoVend(rs.getFloat("precovend"));
            produto.setTamanho(rs.getInt("tamanho"));
            produto.setQtdProduto(rs.getInt("qtdproduto"));

            produtos.add(produto);
        }

        return produtos;
    }

    @Override
    public void gravar(Produto value) throws Exception {
        String sql = "INSERT INTO produto (nome, precocomp, precovend, tamanho, qtdproduto) VALUES (?,?,?,?,?)";
        PreparedStatement ps = getPreparedStatement(sql, true);
        ps.setString(1, value.getNome());
        ps.setFloat(2, value.getPrecoComp());
        ps.setFloat(3, value.getPrecoVend());
        ps.setInt(4, value.getTamanho());
        ps.setInt(5, value.getQtdProduto());


        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        value.setId(rs.getLong(1));

    }

    @Override
    public void alterar(Produto value) throws Exception {
        String sql = "update produto set nome = ?, precocomp = ?, precovend = ?, tamanho = ?, qtdproduto = ?  where id = ?";
        PreparedStatement ps = getPreparedStatement(sql, false);
        ps.setString(1, value.getNome());
        ps.setFloat(2, value.getPrecoComp());
        ps.setFloat(3, value.getPrecoVend());
        ps.setInt(4, value.getTamanho());
        ps.setInt(5, value.getQtdProduto());
        ps.setLong(6, value.getId());
        ps.executeUpdate();

    }

    @Override
    public void excluir(Produto value) throws Exception {
        String sql = "delete from produto where id = ?";
        PreparedStatement ps = getPreparedStatement(sql, false);
        ps.setLong(1, value.getId());
        ps.executeUpdate();
    }
}
