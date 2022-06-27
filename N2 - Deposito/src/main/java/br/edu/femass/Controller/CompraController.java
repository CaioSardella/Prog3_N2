package br.edu.femass.Controller;

import br.edu.femass.Dao.ItemDao;
import br.edu.femass.Dao.PedidoDao;
import br.edu.femass.Dao.ProdutoDao;
import br.edu.femass.Dao.UsuarioDao;
import br.edu.femass.Model.Cadastro.Fornecedor;
import br.edu.femass.Model.Cadastro.Produto;
import br.edu.femass.Model.Cadastro.Usuario;
import br.edu.femass.Model.Compra.Item;
import br.edu.femass.Model.Compra.Pedido;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

public class CompraController extends MainController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        atualizarListaPedido();
        atualizarListaCaixa();
        Integer id = 0;
        atualizarListaItem(id);
    }

    private final ProdutoDao produtoDao = new ProdutoDao();
    private final PedidoDao pedidoDao = new PedidoDao();
    private final UsuarioDao usuarioDao = new UsuarioDao();
    private final ItemDao itemDao = new ItemDao();

    private Pedido pedido = new Pedido();

    //==================== Compra
    @FXML
    private HBox HBItem;
    @FXML
    private VBox Cad_Compra;
    @FXML
    private RadioButton Radio_Compra;
    @FXML
    private TextField TxtIdCompra;
    @FXML
    private TextField TxtIdFornecedorCompra;
    @FXML
    private TextField TxtNomeFornecedorCompra;
    @FXML
    private TextField TxtTotalPedidoCompra;
    @FXML
    private Button BtnIniciarCompra;
    @FXML
    private Button BtnExcluirCompra;
    @FXML
    private Button BtnCancelarCompra;

    @FXML
    private HBox HBLstPedido;
    @FXML
    private ListView<Pedido> LstPedido;


    //==================== Venda
    @FXML
    private VBox Cad_Venda;
    @FXML
    private RadioButton Radio_Venda;
    @FXML
    private TextField TxtIdVenda;
    @FXML
    private TextField TxtIdClienteVenda;
    @FXML
    private TextField TxtNomeClienteVenda;
    @FXML
    private TextField TxtTotalPedidoVenda;
    @FXML
    private Button BtnIniciarVenda;
    @FXML
    private Button BtnExcluirVenda;
    @FXML
    private Button BtnCancelarVenda;

    //==================== Caixa
    @FXML
    private VBox Cad_Caixa;
    @FXML
    private ListView<Pedido> LstCaixa;
    @FXML
    private HBox HBLstCaixa;
    @FXML
    private Button BtnFecharCaixa;
    @FXML
    private TextField TxtDataCaixa;
    @FXML
    private TextField TxtCaixaCompra;
    @FXML
    private TextField TxtCaixaVenda;
    @FXML
    private TextField TxtCaixaSoma;

    //==================== Item

    @FXML
    private TextField TxtItemId;
    @FXML
    private TextField TxtIdProdutoItem;
    @FXML
    private TextField TxtNomeProdutoItem;
    @FXML
    private TextField TxtPrecoProdutoItem;
    @FXML
    private TextField TxtQtdProdutoItem;
    @FXML
    private TextField TxtPrecoTotalItem;
    @FXML
    private Button BtnAdicionarItem;
    @FXML
    private Button BtnItemExcluir;
    @FXML
    private Button BtnItemCancelar;
    @FXML
    private Button BtnFinalizarPedido;
    @FXML
    private VBox Cad_item;
    @FXML
    private HBox HBLstItem;
    @FXML
    private ListView<Item> LstItem;


    //######################################################### Compra
    @FXML
    private void Radio_Compra_Selected (ActionEvent evento){

            Radio_Venda.setSelected(false);
            Radio_Compra.setSelected(true);
            Cad_Compra.setVisible(true);
            Cad_Venda.setVisible(false);
            Cad_Caixa.setVisible(false);
            Cad_item.setVisible(false);
            Consulta_Venda(true);
            TxtIdCompra.setDisable(true);
            LstCaixa.setVisible(false);
            HBLstCaixa.setVisible(false);
            atualizarListaPedido();
            LstItem.setVisible(false);
            HBLstItem.setVisible(false);

            LstPedido.setVisible(true);
            HBLstPedido.setVisible(true);


    }

    public void CompletaNomeCompra(ActionEvent evento){
        try {
            for (Usuario u : usuarioDao.listar()) {
                if (u.getId().equals(Long.parseLong(TxtIdFornecedorCompra.getText()))) {
                    TxtNomeFornecedorCompra.setText(u.getNome());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @FXML
    private void BtnIniciarCompra_Action(ActionEvent evento){

        Cad_Venda.setVisible(false);
        Cad_Compra.setVisible(false);
        Cad_Caixa.setVisible(false);
        Cad_item.setVisible(true);

        LstCaixa.setVisible(false);
        LstPedido.setVisible(false);
        LstItem.setVisible(true);

        HBLstItem.setVisible(true);
        HBLstCaixa.setVisible(false);
        HBLstPedido.setVisible(false);

        cadastrarCompra();
        Limpar_CadItem();


    }


    @FXML
    private void BtnExcluirCompra_Action(ActionEvent evento){
        Pedido pedido = LstPedido.getSelectionModel().getSelectedItem();

        if (pedido==null) return;

        try {

        for(Item i : itemDao.listar(Math.toIntExact(pedido.getId()))){
            if(i.getId_pedido() == pedido.getId()){
                itemDao.excluir(i);
            }
        }

            pedidoDao.excluir(pedido);
        } catch (Exception e) {
            e.printStackTrace();
        }

        atualizarListaPedido();
    }

    @FXML
    private void BtnCancelarCompra_Action(ActionEvent evento){
        Consulta_Compra(true);
    }


    private void atualizarListaPedido() {

        List<Pedido> pedidos;
        try {
            pedidos = pedidoDao.listar();
        } catch (Exception e) {
            pedidos = new ArrayList<>();
        }
        ObservableList<Pedido> usuariosOb = FXCollections.observableArrayList(pedidos);
        LstPedido.setItems(usuariosOb);
    }


    @FXML
    private void Limpar_Compra(){
        TxtIdCompra.setText("");
        TxtIdFornecedorCompra.setText("");
        TxtNomeFornecedorCompra.setText("");
        TxtTotalPedidoCompra.setText("");
    }

    @FXML
    private void Consulta_Compra(boolean b){
        Limpar_Compra();

        TxtIdCompra.setDisable(b);
        TxtNomeFornecedorCompra.setDisable(b);
        TxtIdFornecedorCompra.setDisable(!b);
        TxtTotalPedidoCompra.setDisable(b);
    }

    @FXML
    private void cadastrarCompra(){

        pedido.setId_usuario(Integer.parseInt(TxtIdFornecedorCompra.getText()));
        pedido.setNome_usuario(TxtNomeFornecedorCompra.getText());

        LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar date = Calendar.getInstance();
        pedido.setData(sdf.format(date.getTime()));

        float valor = 0;
        pedido.setSubtotal(valor);


        if (TxtIdFornecedorCompra.getText().equals("")){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.show();
        }else{
            try {
                pedidoDao.gravar(pedido);
            } catch (Exception e) {
                e.printStackTrace();
            }
            atualizarListaPedido();
            TxtIdCompra.setText(String.valueOf(pedido.getId()));
        }

    }

    @FXML
    private void LstPedido_KeyPressed(KeyEvent evento){

    }

    @FXML
    private void LstPedido_MouseClicked(MouseEvent evento){

    }


    //######################################################### Venda
    @FXML
    private void Radio_Venda_Selected (ActionEvent evento) {
        Radio_Compra.setSelected(false);
        Radio_Venda.setSelected(true);
        Cad_Venda.setVisible(true);
        Cad_Compra.setVisible(false);
        Cad_Caixa.setVisible(false);
        Cad_item.setVisible(false);
        Consulta_Compra(true);
        TxtIdVenda.setDisable(true);

        LstPedido.setVisible(true);
        HBLstPedido.setVisible(true);

        LstCaixa.setVisible(false);
        HBLstCaixa.setVisible(false);
        atualizarListaPedido();
        LstItem.setVisible(false);
        HBLstItem.setVisible(false);


    }

    @FXML
    private void CompletaNomeVenda(ActionEvent evento){
        try {
            for (Usuario u : usuarioDao.listar()) {
                if (TxtIdClienteVenda.getText().equals((u.getId().toString()))) {
                    TxtNomeClienteVenda.setText(u.getNome());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void BtnIniciarVenda_Action(ActionEvent evento){

        Cad_Venda.setVisible(false);
        Cad_Compra.setVisible(false);
        Cad_Caixa.setVisible(false);
        Cad_item.setVisible(true);

        LstCaixa.setVisible(false);
        LstPedido.setVisible(false);
        LstItem.setVisible(true);

        HBLstItem.setVisible(true);
        HBLstCaixa.setVisible(false);
        HBLstPedido.setVisible(false);

        cadastrarVenda();

    }

    @FXML
    private void BtnExcluirVenda_Action(ActionEvent evento){
        Pedido pedido = LstPedido.getSelectionModel().getSelectedItem();

        if (pedido==null) return;

        try {

            for(Item i : itemDao.listar(Math.toIntExact(pedido.getId()))){
                if(i.getId_pedido() == pedido.getId()){
                    itemDao.excluir(i);
                }
            }

            pedidoDao.excluir(pedido);
        } catch (Exception e) {
            e.printStackTrace();
        }

        atualizarListaPedido();
    }

    @FXML
    private void BtnCancelarVenda_Action(ActionEvent evento){
        Consulta_Venda(true);
    }


    @FXML
    private void Limpar_Venda(){
        TxtIdVenda.setText("");
        TxtIdClienteVenda.setText("");
        TxtNomeClienteVenda.setText("");
        TxtTotalPedidoVenda.setText("");
    }

    @FXML
    private void Consulta_Venda(boolean b){
        Limpar_Venda();

        TxtIdVenda.setDisable(b);
        TxtNomeClienteVenda.setDisable(b);
        TxtIdClienteVenda.setDisable(!b);
        TxtTotalPedidoVenda.setDisable(b);
    }

    @FXML
    private void cadastrarVenda(){

        pedido.setId_usuario(Integer.parseInt(TxtIdClienteVenda.getText()));
        pedido.setNome_usuario(TxtNomeClienteVenda.getText());

        LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar date = Calendar.getInstance();
        pedido.setData(sdf.format(date.getTime()));

        float valor = 0;

        pedido.setSubtotal(valor);

        TxtIdVenda.setText(String.valueOf(pedido.getId()));
        TxtItemId.setText(String.valueOf(pedido.getId()));

        if (TxtIdClienteVenda.getText().equals("")){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.show();
        }else{
            try {
                pedidoDao.gravar(pedido);
            } catch (Exception e) {
                e.printStackTrace();
            }
            atualizarListaPedido();
            TxtIdVenda.setText(String.valueOf(pedido.getId()));
        }

    }

//######################################################### Fechar Caixa

    private void atualizarListaCaixa() {
        data();
        List<Pedido> pedidos;
        try {
            pedidos = pedidoDao.filtrarData(TxtDataCaixa.getText());

        } catch (Exception e) {
            pedidos = new ArrayList<>();
        }
        ObservableList<Pedido> usuariosOb = FXCollections.observableArrayList(pedidos);
        LstCaixa.setItems(usuariosOb);

    }

    private float somarPedido(){

        float saldoFinal= 0;

        try {
            for(Pedido p : pedidoDao.filtrarData(TxtDataCaixa.getText())){
                saldoFinal = saldoFinal + p.getSubtotal();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return saldoFinal;
    }

    private void data (){

        TxtDataCaixa.setEditable(false);
        TxtDataCaixa.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar date = Calendar.getInstance();
        TxtDataCaixa.setText(sdf.format(date.getTime()));

    }

    @FXML
    private void BtnFecharCaixa_Action (ActionEvent evento){
        data();
        atualizarListaCaixa();
        somarPedido();
        TxtCaixaSoma.setText("R$"+String.valueOf(somarPedido()));
        Radio_Venda.setSelected(false);
        Radio_Compra.setSelected(false);
        Cad_Compra.setVisible(false);
        Cad_Venda.setVisible(false);
        Cad_Caixa.setVisible(true);
        Consulta_Venda(true);
        Consulta_Compra(true);
        LstCaixa.setVisible(true);
        HBLstCaixa.setVisible(true);

        float total_compra=0, total_venda=0;

        try {
            for(Pedido p: pedidoDao.listar()){
                if(p.getSubtotal() < 0){
                    total_compra=total_compra+p.getSubtotal();
                }

                if(p.getSubtotal() > 0){
                    total_venda=total_venda+p.getSubtotal();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        TxtCaixaCompra.setText("R$"+String.valueOf(total_compra));
        TxtCaixaVenda.setText("R$"+String.valueOf(total_venda));

    }

    //######################################################### Item
    @FXML
    private void TxtIdProdutoItem_Action(ActionEvent evento){

        try {
            for (Produto p : produtoDao.listar()) {
                if (TxtIdProdutoItem.getText().equals((p.getId().toString()))) {
                    TxtNomeProdutoItem.setText(p.getNome());
                        if(Radio_Compra.isSelected()){
                            TxtItemId.setText(String.valueOf(TxtIdCompra.getText()));
                            TxtPrecoProdutoItem.setText(String.valueOf(p.getPrecoComp()));
                        }
                         if(Radio_Venda.isSelected()){
                            TxtItemId.setText(String.valueOf(TxtIdVenda.getText()));
                            TxtPrecoProdutoItem.setText(String.valueOf(p.getPrecoVend()));
                        }
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML private void TxtQtdProdutoItem_Action(ActionEvent evento){
        float total = 0;

        total = Float.parseFloat(TxtQtdProdutoItem.getText())*Float.parseFloat(TxtPrecoProdutoItem.getText());
        TxtPrecoTotalItem.setText(String.valueOf(total));
    }

    @FXML
    private void BtnAdicionarItem_Action(ActionEvent evento){

        Item item = new Item();

        item.setId_produto(Integer.parseInt(TxtIdProdutoItem.getText()));
        item.setQtd_item(Integer.parseInt(TxtQtdProdutoItem.getText()));
        item.setNome_produto(TxtNomeProdutoItem.getText());

        try {
            for (Produto prod : produtoDao.listar()){
                if(prod.getId()==(item.getId_produto())){
                    item.setNome_produto(prod.getNome());
                }
            }

            if(Radio_Compra.isSelected()){
                item.setId_pedido(Integer.parseInt(TxtIdCompra.getText()));
                for (Produto p : produtoDao.listar()){
                    if(p.getId()==(item.getId_produto())){
                        item.setPrecototal((item.getQtd_item()*p.getPrecoComp()));
                    }
                }
            }

            if(Radio_Venda.isSelected()){
                item.setId_pedido(Integer.parseInt(TxtIdVenda.getText()));
                for (Produto p : produtoDao.listar()){
                    if(p.getId()==(item.getId_produto())){
                        item.setPrecototal(item.getQtd_item()*p.getPrecoVend());
                    }
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        item.setPrecototal(Float.parseFloat(TxtPrecoTotalItem.getText()));
        item.setQtd_item(Integer.parseInt(TxtQtdProdutoItem.getText()));


        if (TxtIdProdutoItem.getText().equals("") ||
                TxtNomeProdutoItem.getText().equals("") ||
                TxtQtdProdutoItem.getText().equals("")){
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.show();
        }else{

            try {
                for (Produto prod : produtoDao.listar()) {
                    if (Radio_Venda.isSelected()){
                        if(prod.getQtdProduto() < Integer.parseInt(TxtQtdProdutoItem.getText())){
                            JOptionPane.showMessageDialog(null, "Quantidade de Produtos Indisponível");
                            System.out.println("Quantidade de Produtos Indisponível");
                            return;
                        }else{
                            itemDao.gravar(item);
                        }
                    }

                }

            for (Produto prod : produtoDao.listar()) {
                if (prod.getId()==(item.getId_produto())) {
                    if(Radio_Venda.isSelected()){
                        prod.setQtdProduto(prod.getQtdProduto()-item.getQtd_item());
                        pedido.setSubtotal(pedido.getSubtotal() + item.getPrecototal());
                        }

                    if(Radio_Compra.isSelected()){
                        prod.setQtdProduto(prod.getQtdProduto()+item.getQtd_item());
                        pedido.setSubtotal(pedido.getSubtotal() - item.getPrecototal());
                        }

                        System.out.println(prod.getQtdProduto());
                        produtoDao.alterar(prod);
                        itemDao.gravar(item);
                    }

                }
        } catch (Exception e) {
                     e.printStackTrace();
            }

        }

        atualizarListaItem(item.getId_pedido());
        Limpar_CadItem();

    }

    @FXML
    private void BtnItemExcluir_Action(ActionEvent evento){
        Item item = LstItem.getSelectionModel().getSelectedItem();

        if (item==null) return;

        try {
            itemDao.excluir(item);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(Radio_Compra.isSelected()){
            atualizarListaItem(Integer.valueOf(TxtItemId.getText()));

        }

        if(Radio_Venda.isSelected()){
            atualizarListaItem(Integer.valueOf(TxtItemId.getText()));
        }

        pedido.setSubtotal(pedido.getSubtotal() - item.getPrecototal());
    }

    @FXML
    private void BtnItemCancelar_Action(ActionEvent evento){

        Cad_Venda.setVisible(false);
        Cad_Compra.setVisible(false);
        Cad_Caixa.setVisible(false);
        Cad_item.setVisible(false);

        LstCaixa.setVisible(false);
        LstPedido.setVisible(true);
        LstItem.setVisible(false);

        HBLstItem.setVisible(false);
        HBLstCaixa.setVisible(false);
        HBLstPedido.setVisible(true);
    }

    @FXML
    private void BtnFinalizarPedido_Action(ActionEvent evento){

        Cad_Venda.setVisible(false);
        Cad_Compra.setVisible(false);
        Cad_Caixa.setVisible(false);
        Cad_item.setVisible(false);

        LstCaixa.setVisible(false);
        LstPedido.setVisible(true);
        LstItem.setVisible(false);

        HBLstItem.setVisible(false);
        HBLstCaixa.setVisible(false);
        HBLstPedido.setVisible(true);

        try {
            pedidoDao.atualizartotal(pedido);
        } catch (Exception e) {
            e.printStackTrace();
        }
        TxtItemId.setText("");
        atualizarListaPedido();


    }

    public void LstItem_KeyPressed(KeyEvent evento) {
        exibirItem();
    }

    public void LstItem_MouseClicked(MouseEvent evento) {
        exibirItem();
    }

    private void exibirItem(){

        Item item = LstItem.getSelectionModel().getSelectedItem();
        if(item==null) return;
        TxtIdProdutoItem.setText(Long.toString(item.getId()));
        TxtIdProdutoItem.setText(Long.toString(item.getId_produto()));
        TxtNomeProdutoItem.setText(item.getNome_produto());
        TxtQtdProdutoItem.setText(Integer.toString(item.getQtd_item()));
        TxtPrecoTotalItem.setText(Float.toString(item.getPrecototal()));

    }

    private void Limpar_CadItem(){
        TxtNomeProdutoItem.setText("");
        TxtIdProdutoItem.setText("");
        TxtQtdProdutoItem.setText("");
        TxtPrecoProdutoItem.setText("");
        TxtPrecoTotalItem.setText("");
    }

    private void atualizarListaItem(Integer id){

        List<Item> itens;
        try {
              itens = itemDao.listar(id);
        } catch (Exception e) {
            itens = new ArrayList<>();
        }
        ObservableList<Item> itensOb = FXCollections.observableArrayList(itens);
        LstItem.setItems(itensOb);
    }


}
