package com.example.hypewave;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.sql.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    public AnchorPane calculatePane;

    public AnchorPane billPane;
    public TextField billInputInfluencer;
    public TextField billInputBetrag;
    public ComboBox billInputTyp;
    public Button billSubmit;

    private List<Pane> mainPanes = new ArrayList<>();

    private Connection con;

    private List<Bill> bills = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainPanes.add(billPane);
        mainPanes.add(calculatePane);
        billInputTyp.getItems().addAll("Instagram Story", "TikTok Beitrag", "Instagram Reel");
        try {
            initSQL();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void initSQL() throws SQLException {
        String connectionUrl = "jdbc:mysql://217.228.198.39:3306/Hypewave";
        String user = "root";
        String password = "Chrissi123";
        con = DriverManager.getConnection(connectionUrl, user, password);


    }


    public void activateMainPane(Pane mainPane){
        for(Pane pane : mainPanes){
            if(pane != mainPane){
                pane.setVisible(false);
            }
            else {
                pane.setVisible(true);
            }
        }
    }

    public void getBillInfos() throws SQLException {
        String query  ="SELECT * FROM Bills";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        bills = new ArrayList<>();
        while (rs.next()){
            bills.add(new Bill(rs.getString(1), Integer.parseInt(rs.getString(2)), rs.getString(3)));
        }

    }

    public void saveBillToSQL() throws SQLException {
        String influencer = billInputInfluencer.getText();
        int betrag = Integer.parseInt(billInputBetrag.getText());
        String typ = billInputTyp.getItems().get(0).toString();
        String query = "INSERT INTO Bills (Influencer, Betrag, Typ) VALUES ('" + influencer + "', " + betrag + ", ' " + typ + "');";
        Statement stmt = con.createStatement();
        stmt.execute(query);
        stmt.close();
    }


    public void loadCalculatePane(ActionEvent actionEvent) {
        activateMainPane(calculatePane);
    }

    public void loadBillPane(ActionEvent actionEvent) {
        activateMainPane(billPane);
    }

}