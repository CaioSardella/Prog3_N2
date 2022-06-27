package br.edu.femass.Dao;

import br.edu.femass.Model.Cadastro.Cliente;
import br.edu.femass.Model.Cadastro.Fornecedor;
import br.edu.femass.Model.Cadastro.Usuario;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDao extends DaoPostgres implements  Dao<Usuario>{
    @Override
    public List<Usuario> listar() throws Exception {
        String sql = "select " +
                "usuario.id as id, " +
                "usuario.nome as nome, " +
                "usuario.transacao as transacao, " +
                "cliente.cpf as cpf " +
                "from usuario inner join cliente on usuario.id = cliente.id_usuario " +
                "order by usuario.nome asc";
        PreparedStatement ps = getPreparedStatement(sql, false);
        ResultSet rs = ps.executeQuery();

        List<Usuario> usuarios = new ArrayList<Usuario>();

        while (rs.next()) {
            Cliente cliente = new Cliente();
            cliente.setId(rs.getLong("id"));
            cliente.setNome(rs.getString("nome"));
            cliente.setTransacao(rs.getBoolean("transacao"));
            cliente.setCpf(rs.getString("cpf"));
            usuarios.add(cliente);
        }

        sql = "select " +
                "usuario.id as id, " +
                "usuario.nome as nome, " +
                "usuario.transacao as transacao, " +
                "fornecedor.cnpj as cnpj,  " +
                "fornecedor.telefone as telefone, " +
                "fornecedor.endereco as endereco, " +
                "fornecedor.endereco as numcasa " +
                "from usuario inner join fornecedor on usuario.id = fornecedor.id_usuario  " +
                "order by usuario.nome";
        ps = getPreparedStatement(sql, false);
        rs = ps.executeQuery();

        while (rs.next()) {
            Fornecedor fornecedor = new Fornecedor();
            fornecedor.setId(rs.getLong("id"));
            fornecedor.setNome(rs.getString("nome"));
            fornecedor.setTransacao(rs.getBoolean("transacao"));
            fornecedor.setCnpj(rs.getString("cnpj"));
            fornecedor.setTelefone(rs.getString("telefone"));
            fornecedor.setEndereco(rs.getString("endereco"));
            fornecedor.setNumCasa(rs.getString("endereco"));
            usuarios.add(fornecedor);
        }

        return usuarios;
    }

    @Override
    public void gravar(Usuario value) throws Exception {
        String sql = "INSERT INTO usuario (nome, transacao) VALUES (?,?)";
        PreparedStatement ps = getPreparedStatement(sql, true);
        ps.setString(1, value.getNome());
        ps.setBoolean(2, value.isTransacao());

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        value.setId(rs.getLong(1));

        if (value instanceof  Fornecedor) {
            sql = "INSERT INTO fornecedor (id_usuario, cnpj, telefone, endereco, numcasa) VALUES (?,?,?,?,?)";
            ps = getPreparedStatement(sql, true);

            ps.setString(2, ((Fornecedor) value).getCnpj());
            ps.setString(3, ((Fornecedor) value).getTelefone());
            ps.setString(4, ((Fornecedor) value).getEndereco());
            ps.setString(5, ((Fornecedor) value).getNumCasa());
        } else {
            sql = "INSERT INTO cliente (id_usuario , cpf) VALUES (?,?)";
            ps = getPreparedStatement(sql, true);

            ps.setString(2, ((Cliente) value).getCpf());

        }
        ps.setLong(1, value.getId());
        ps.executeUpdate();

    }

    @Override
    public void alterar(Usuario value) throws Exception {
        String sql = "update usuario set nome = ?, transacao = ? where id = ?";
        PreparedStatement ps = getPreparedStatement(sql, false);
        ps.setString(1, value.getNome());
        ps.setBoolean(2, value.isTransacao());
        ps.setLong(3, value.getId());
        ps.executeUpdate();

        if (value instanceof Fornecedor) {
            sql = "update fornecedor set cnpj = ?, telefone = ?, endereco = ?, numcasa = ? where id_usuario = ?";
            ps = getPreparedStatement(sql, true);
            ps.setString(1, ((Fornecedor) value).getCnpj());
            ps.setString(2, ((Fornecedor) value).getTelefone());
            ps.setString(3, ((Fornecedor) value).getEndereco());
            ps.setString(4, ((Fornecedor) value).getNumCasa());
        } else {
            sql = "update cliente set cpf = ? where id_usuario = ?";
            ps = getPreparedStatement(sql, true);
            ps.setString(1, ((Cliente) value).getCpf());
        }
        ps.setLong(1, value.getId());
        ps.executeUpdate();

    }

    @Override
    public void excluir(Usuario value) throws Exception {
        Connection conexao = getConexao();
        conexao.setAutoCommit(false);
        try {
            String sql = "";
            if (value instanceof Fornecedor) {
                sql = "delete from fornecedor where id_usuario = ?";
            } else {
                sql = "delete from cliente where id_usuario = ?";
            }
            PreparedStatement ps1 = conexao.prepareStatement(sql);
            ps1.setLong(1, value.getId());
            ps1.executeUpdate();

            sql = "delete from usuario where id = ?";
            PreparedStatement ps2 = conexao.prepareStatement(sql);

            ps2.setLong(1, value.getId());
            ps2.executeUpdate();
            conexao.commit();
        } catch (SQLException exception) {
            conexao.rollback();
            throw exception;
        }
    }
}
