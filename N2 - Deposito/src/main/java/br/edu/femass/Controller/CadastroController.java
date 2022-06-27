package br.edu.femass.Controller;

import br.edu.femass.Dao.ProdutoDao;
import br.edu.femass.Dao.UsuarioDao;
import br.edu.femass.Model.*;
import br.edu.femass.Model.Cadastro.Cliente;
import br.edu.femass.Model.Cadastro.Fornecedor;
import br.edu.femass.Model.Cadastro.Produto;
import br.edu.femass.Model.Cadastro.TipoOperacao;
import br.edu.femass.Model.Cadastro.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CadastroController implements Initializable {

    private final ProdutoDao produtoDao = new ProdutoDao();
    private final UsuarioDao usuarioDao = new UsuarioDao();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<TipoOperacao> tipooperacao = FXCollections.observableArrayList(TipoOperacao.values());
        CboTipoOperacao.setItems(tipooperacao);

    }


    //Fornecedor
    @FXML
    private RadioButton Radio_Fornecedor;
    @FXML
    private VBox Cad_Fornecedor;
    @FXML
    private TextField TxtFornecedorId;
    @FXML
    private TextField TxtFornecedorNome;
    @FXML
    private TextField TxtFornecedorCNPJ;
    @FXML
    private TextField TxtFornecedorTelefone;
    @FXML
    private TextField TxtFornecedorEndereco;
    @FXML
    private TextField TxtFornecedorNumero;
    @FXML
    private ListView<Usuario> LstUsuario;
    @FXML
    private HBox HBLstUsuario;
    @FXML
    private Button BtnFornecedorExecutar;
    @FXML
    private Button BtnFornecedorExcluir;
    @FXML
    private Button BtnFornecedorCancelar;

    //Cliente
    @FXML
    private RadioButton Radio_Cliente;
    @FXML
    private VBox Cad_Cliente;
    @FXML
    private TextField TxtClienteId;
    @FXML
    private TextField TxtClienteNome;
    @FXML
    private TextField TxtClienteCPF;

    @FXML
    private Button BtnClienteExecutar;
    @FXML
    private Button BtnClienteCancelar;
    @FXML
    private Button BtnClienteExcluir;


    //Produto
    @FXML
    private RadioButton Radio_Produto;
    @FXML
    private VBox Cad_Prod;
    @FXML
    private TextField TxtProdId;
    @FXML
    private TextField TxtProdNome;
    @FXML
    private TextField TxtProdPCompra;
    @FXML
    private TextField TxtProdPVenda;
    @FXML
    private TextField TxtProdTamanho;
    @FXML
    private TextField TxtProdQtd;
    @FXML
    private Button BtnProdExecutar;
    @FXML
    private Button BtnProdExcluir;
    @FXML
    private Button BtnProdCancelar;
    @FXML
    private ListView<Produto> LstProd;
    @FXML
    private HBox HBLstProd;



    

    //Tela Consulta/Cadastro

    @FXML
    private ComboBox CboTipoOperacao;



    @FXML
    private void Radio_Fornecedor_Selected(ActionEvent evento) {
        Radio_Fornecedor.setSelected(true);
        Radio_Cliente.setSelected(false);
        Radio_Produto.setSelected(false);
        Cad_Fornecedor.setVisible(true);
        Cad_Cliente.setVisible(false);
        Cad_Prod.setVisible(false);
        Limpar_CadCliente();
        Limpar_CadProduto();
        Consulta_Produto(true);
        TxtProdId.setDisable(true);
        Consulta_Cliente(true);
        TxtClienteId.setDisable(true);
        CboTipoOperacao.setValue("");
        LstUsuario.setVisible(true);
        LstProd.setVisible(false);
        atualizarLista();
        HBLstProd.setVisible(false);

    }

    @FXML
    private void Radio_Cliente_Selected(ActionEvent evento) {
        Radio_Fornecedor.setSelected(false);
        Radio_Cliente.setSelected(true);
        Radio_Produto.setSelected(false);
        Cad_Fornecedor.setVisible(false);
        Cad_Cliente.setVisible(true);
        Cad_Prod.setVisible(false);
        Limpar_CadFornecedor();
        Limpar_CadProduto();
        Consulta_Produto(true);
        TxtProdId.setDisable(true);
        Consulta_Fornecedor(true);
        TxtFornecedorId.setDisable(true);
        CboTipoOperacao.setValue("");
        LstUsuario.setVisible(true);
        LstProd.setVisible(false);
        atualizarLista();
        HBLstProd.setVisible(false);

    }

    @FXML
    private void Radio_Produto_Selected(ActionEvent evento) {
        Radio_Fornecedor.setSelected(false);
        Radio_Cliente.setSelected(false);
        Radio_Produto.setSelected(true);
        Cad_Fornecedor.setVisible(false);
        Cad_Cliente.setVisible(false);
        Cad_Prod.setVisible(true);
        Limpar_CadFornecedor();
        Limpar_CadCliente();
        Consulta_Cliente(true);
        TxtClienteId.setDisable(true);
        Consulta_Fornecedor(true);
        TxtFornecedorId.setDisable(true);
        CboTipoOperacao.setValue("");
        LstProd.setVisible(true);
        LstUsuario.setVisible(false);
        atualizarLista_Produto();
        HBLstProd.setVisible(true);

    }

    @FXML
    private void CboTipoOperacao_Action(ActionEvent evento){

        if(CboTipoOperacao.getValue().toString().equals("Consulta")){
            if (Radio_Produto.isSelected()) {
                Consulta_Produto(true);
            } else {
                if (Radio_Cliente.isSelected()) {
                    Consulta_Cliente(true);
                } else {
                    if (Radio_Fornecedor.isSelected()) {
                        Consulta_Fornecedor(true);
                    }
                }

            }

        }if(CboTipoOperacao.getValue().toString().equals("Cadastro")){

            if (Radio_Produto.isSelected()) {
                Consulta_Produto(false);
            } else {
                if (Radio_Cliente.isSelected()) {
                    Consulta_Cliente(false);
                } else {
                    if (Radio_Fornecedor.isSelected()) {
                        Consulta_Fornecedor(false);
                    }
                }
            }

        }
    }


    @FXML
    private void Limpar_CadFornecedor(){
        TxtFornecedorId.setText("");
        TxtFornecedorNome.setText("");
        TxtFornecedorCNPJ.setText("");
        TxtFornecedorTelefone.setText("");
        TxtFornecedorEndereco.setText("");
        TxtFornecedorNumero.setText("");
    }

    @FXML
    private void Limpar_CadCliente(){
        TxtClienteId.setText("");
        TxtClienteNome.setText("");
        TxtClienteCPF.setText("");
    }

    @FXML
    private void Limpar_CadProduto(){
        TxtProdId.setText("");
        TxtProdNome.setText("");
        TxtProdPCompra.setText("");
        TxtProdPVenda.setText("");
        TxtProdTamanho.setText("");
        TxtProdQtd.setText("");
    }

    @FXML
    private void Consulta_Fornecedor(boolean b){
        Limpar_CadFornecedor();

        TxtFornecedorNome.setDisable(b);
        TxtFornecedorCNPJ.setDisable(b);
        TxtFornecedorTelefone.setDisable(b);
        TxtFornecedorEndereco.setDisable(b);
        TxtFornecedorNumero.setDisable(b);
    }

    @FXML
    private void Consulta_Cliente(boolean b){
        Limpar_CadCliente();

        TxtClienteNome.setDisable(b);
        TxtClienteCPF.setDisable(b);
    }

    @FXML
    private void Consulta_Produto(boolean b){
        Limpar_CadProduto();

        TxtProdNome.setDisable(b);
        TxtProdPCompra.setDisable(b);
        TxtProdPVenda.setDisable(b);
        TxtProdTamanho.setDisable(b);
        TxtProdQtd.setDisable(b);
    }
    //########################################################################### Fornecedor
    @FXML
    private void LstUsuario_MouseClicked(MouseEvent evento) {
        exibirUsuario();
    }

    @FXML
    private void LstUsuario_KeyPressed(KeyEvent evento) {
        exibirUsuario();
    }


    private void exibirUsuario(){

        Usuario usuario = LstUsuario.getSelectionModel().getSelectedItem();
        if (usuario==null) return;


        if (usuario instanceof Fornecedor) {

            Fornecedor forn = (Fornecedor) usuario;
            TxtFornecedorId.setText(String.valueOf(usuario.getId()));
            TxtFornecedorNome.setText(usuario.getNome());
            TxtFornecedorCNPJ.setText(forn.getCnpj());
            TxtFornecedorTelefone.setText(forn.getTelefone());
            TxtFornecedorEndereco.setText(forn.getEndereco());
            TxtFornecedorNumero.setText(forn.getNumCasa());
            Cad_Cliente.setVisible(false);
            Cad_Prod.setVisible(false);
            Cad_Fornecedor.setVisible(true);


        } else {

            Cliente clie = (Cliente) usuario;
            TxtClienteId.setText(String.valueOf(usuario.getId()));
            TxtClienteNome.setText(usuario.getNome());
            TxtClienteCPF.setText(clie.getCpf());
            Cad_Cliente.setVisible(true);
            Cad_Prod.setVisible(false);
            Cad_Fornecedor.setVisible(false);
        }

    }

    private void atualizarLista() {
        List<Usuario> usuarios;

        try {
            usuarios = usuarioDao.listar();
        } catch (Exception e) {
            usuarios = new ArrayList<>();
        }
        ObservableList<Usuario> usuarioOb = FXCollections.observableArrayList(usuarios);
        LstUsuario.setItems(usuarioOb);
    }

    @FXML
    private void BtnFornecedorExecutar_Action(ActionEvent evento) {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome(TxtFornecedorNome.getText());
        fornecedor.setCnpj(TxtFornecedorCNPJ.getText());
        fornecedor.setTelefone(TxtFornecedorTelefone.getText());
        fornecedor.setEndereco(TxtFornecedorEndereco.getText());
        fornecedor.setNumCasa(TxtFornecedorNumero.getText());

        if (TxtFornecedorNome.getText().equals("") ||
            TxtFornecedorCNPJ.getText().equals("") ||
            TxtFornecedorTelefone.getText().equals("") ||
            TxtFornecedorEndereco.getText().equals("") ||
            TxtFornecedorNumero.getText().equals("")){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.show();
        }
        else {
            try {
                usuarioDao.gravar(fornecedor);
            } catch (Exception e) {
                e.printStackTrace();
            }
            atualizarLista();
            Limpar_CadFornecedor();
        }
    }


    @FXML
    private void BtnFornecedorExcluir_Action(ActionEvent evento) {
        Usuario usuario = LstUsuario.getSelectionModel().getSelectedItem();

        if (usuario==null) return;

        try {
            usuarioDao.excluir(usuario);
        } catch (Exception e) {
            e.printStackTrace();
        }

        atualizarLista();
    }

    @FXML
    private void BtnFornecedorCancelar_Action(ActionEvent evento) {
        Limpar_CadFornecedor();
    }

    //########################################################################### Cliente
    @FXML
    private void BtnClienteExecutar_Action(ActionEvent evento) {
        Cliente cliente = new Cliente();
        cliente.setNome(TxtClienteNome.getText());
        cliente.setCpf(TxtClienteCPF.getText());

        if (TxtClienteNome.getText().equals("") ||
                TxtClienteCPF.getText().equals("") ){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.show();
        }
        else {
            try {
                usuarioDao.gravar(cliente);
            } catch (Exception e) {
                e.printStackTrace();
            }
            atualizarLista();
            Limpar_CadCliente();
        }
    }


    @FXML
    private void BtnClienteExcluir_Action(ActionEvent evento) {
        Usuario usuario = LstUsuario.getSelectionModel().getSelectedItem();

        if (usuario==null) return;

        try {
            usuarioDao.excluir(usuario);
        } catch (Exception e) {
            e.printStackTrace();
        }

        atualizarLista();
    }

    @FXML
    private void BtnClienteCancelar_Action(ActionEvent evento) {
        Limpar_CadCliente();
    }
    //########################################################################### Produto
    @FXML
    private void LstProd_MouseClicked(MouseEvent evento) {
        exibirProduto();
    }

    @FXML
    private void LstProd_KeyPressed(KeyEvent evento) {
        exibirProduto();
    }

    private void exibirProduto(){
        Produto produto = LstProd.getSelectionModel().getSelectedItem();
        if(produto==null) return;
        TxtProdId.setText(Long.toString(produto.getId()));
        TxtProdNome.setText(produto.getNome());
        TxtProdPCompra.setText(Float.toString(produto.getPrecoComp()));
        TxtProdPVenda.setText(Float.toString(produto.getPrecoVend()));
        TxtProdTamanho.setText(Integer.toString(produto.getTamanho()));
        TxtProdQtd.setText(Integer.toString(produto.getQtdProduto()));
    }

    private void atualizarLista_Produto() {
        List<Produto> produtos;
        try {
            produtos = produtoDao.listar();
        } catch (Exception e) {
            produtos = new ArrayList<>();
        }
        ObservableList<Produto> produtoOb = FXCollections.observableArrayList(produtos);
        LstProd.setItems(produtoOb);
    }

    @FXML
    private void BtnProdExecutar_Action(ActionEvent evento) {
        Produto produto = new Produto();
        produto.setNome(TxtProdNome.getText());
        produto.setPrecoComp(Float.parseFloat(TxtProdPCompra.getText()));
        produto.setPrecoVend(Float.parseFloat(TxtProdPVenda.getText()));
        produto.setTamanho(Integer.parseInt(TxtProdTamanho.getText()));
        produto.setQtdProduto(Integer.parseInt(TxtProdQtd.getText()));

        if (TxtProdNome.getText().equals("") ||
                TxtProdPCompra.getText().equals("") ||
                TxtProdPVenda.getText().equals("") ||
                TxtProdTamanho.getText().equals("") ||
                TxtProdQtd.getText().equals("")){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.show();
        }
        else {
            try {
                produtoDao.gravar(produto);
            } catch (Exception e) {
                e.printStackTrace();
            }
            atualizarLista_Produto();
            Limpar_CadProduto();
        }
    }


    @FXML
    private void BtnProdExcluir_Action(ActionEvent evento) {
        Produto produto = LstProd.getSelectionModel().getSelectedItem();

        if (produto==null) return;

        try {
            produtoDao.excluir(produto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        atualizarLista_Produto();
    }

    @FXML
    private void BtnProdCancelar_Action(ActionEvent evento) {
        Limpar_CadProduto();
    }


}
