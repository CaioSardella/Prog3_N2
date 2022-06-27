package br.edu.femass.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    @FXML
    private  Button BtnCadastroConsulta;
    @FXML
    private Button BtnCompraVenda;

    @FXML
    private void BtnCompraVenda_Action(ActionEvent evento){
        abrirTela("Compra&Venda","Compras & Vendas");
    }

    @FXML
    private void BtnCadastroConsulta_Action(ActionEvent evento){
        abrirTela("Cadastro&Consulta","Cadastros & Consultas");
    }



    public void abrirTela(String nome, String titulo){
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/" +nome+ ".fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        scene.getRoot().setStyle("-fx-font-family: 'serif'");

        Stage stage = new Stage();
        stage.setTitle(titulo);
        stage.setScene(scene);
        stage.show();
    }

}
