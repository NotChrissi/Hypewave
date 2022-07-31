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
    public Label billInfoLabel;
    public VBox billInfoVBox;

    private List<Pane> mainPanes = new ArrayList<>();

    private Connection con;

    private List<Bill> bills = new ArrayList<>();
    private List<BillLabel> billLabels = new ArrayList<>();

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
        try {
            getBillInfos("ORDER BY Bill_ID DESC");
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




    public void getBillInfos(String order) throws SQLException {
        String query  ="SELECT * FROM Bills " + order;
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        for(BillLabel billLabel : billLabels){
            if(!billLabel.deleted){
                billLabel.delete();

            }
        }

        bills = new ArrayList<>();
        billLabels = new ArrayList<>();
        while (rs.next()){
            Bill newBill = new Bill(Integer.parseInt(rs.getString(1)), rs.getString(2), Integer.parseInt(rs.getString(3)), rs.getString(4));
            bills.add(newBill);
            billLabels.add(new BillLabel(newBill, billInfoVBox, con));
        }

    }

    public void saveBillToSQL() throws SQLException {
        String influencer = billInputInfluencer.getText();
        int betrag = Integer.parseInt(billInputBetrag.getText());
        String typ = (String) billInputTyp.getValue();
        String query = "INSERT INTO Bills (Influencer, Betrag, Typ) VALUES ('" + influencer + "', " + betrag + ", ' " + typ + "');";
        Statement stmt = con.createStatement();
        stmt.execute(query);
        stmt.close();
        getBillInfos("ORDER BY Bill_ID DESC");


    }


    public void refreshBills(ActionEvent actionEvent) throws SQLException {
        getBillInfos("ORDER BY Bill_ID DESC");
    }

    public void loadCalculatePane(ActionEvent actionEvent) {
        activateMainPane(calculatePane);
    }

    public void loadBillPane(ActionEvent actionEvent) {
        activateMainPane(billPane);
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
}