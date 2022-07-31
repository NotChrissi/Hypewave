package com.example.hypewave;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.sql.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    //h VARIABLES

    //MAIN_OPERATIONS

    public AnchorPane calculatePane;
    public AnchorPane billPane;

    private List<Pane> mainPanes = new ArrayList<>();


    //BILL_PANE

    private Connection billConnection;
    private List<Bill> bills = new ArrayList<>();
    private List<BillLabel> billLabels = new ArrayList<>();
    public ComboBox orderBillsByBox;
    public TextField billInputInfluencer;
    public TextField billInputBetrag;
    public ComboBox billInputTyp;
    public Button billSubmit;

    public Label billInfoLabel;
    public VBox billInfoVBox;

    private enum billOrderPossibilitys{
        DATUM_AUFSTEIGEND,
        DATUM_ABSTEIGEND,
        BETRAG_AUFSTEIGEND,
        BETRAG_ABSTEIGEND
    }

    private enum billTypPossibilitys{
        INSTAGRAM_STORY,
        TIKTOK_BEITRAG,
        INSTAGRAM_REEL
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //h INITIALIZE

        //MAIN_OPERATIONS

        mainPanes.add(billPane);
        mainPanes.add(calculatePane);



        //BILL_PANE

        for(billTypPossibilitys billTyp : billTypPossibilitys.values()){
            billInputTyp.getItems().add(billTyp.toString());
        }

        for(billOrderPossibilitys billOrder : billOrderPossibilitys.values()){
            orderBillsByBox.getItems().add(billOrder.toString());
        }

        try {
            initBillSQL();
            getBillInfos("ORDER BY Bill_ID DESC");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    //h METHODS

    //MAIN_OPERATIONS

    public void activatePane(Pane mainPane){
        for(Pane pane : mainPanes){
            if(pane != mainPane){
                pane.setVisible(false);
            }
            else {
                pane.setVisible(true);
            }
        }
    }
    public void loadCalculatePane(ActionEvent actionEvent) {
        activatePane(calculatePane);
    }

    public void loadBillPane(ActionEvent actionEvent) {
        activatePane(billPane);
    }


    //BILL_PANE

    public void initBillSQL() throws SQLException {
        String connectionUrl = "jdbc:mysql://217.228.198.39:3306/Hypewave";
        String user = "root";
        String password = "Chrissi123";
        billConnection = DriverManager.getConnection(connectionUrl, user, password);
    }

    public void getBillInfos(String order){
        try {
            String query  ="SELECT * FROM Bills " + order;
            Statement stmt = billConnection.createStatement();
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
                billLabels.add(new BillLabel(newBill, billInfoVBox, billConnection));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void saveBillToSQL() throws SQLException {
        String influencer = billInputInfluencer.getText();
        int betrag = Integer.parseInt(billInputBetrag.getText());
        String typ = (String) billInputTyp.getValue();
        String query = "INSERT INTO Bills (Influencer, Betrag, Typ) VALUES ('" + influencer + "', " + betrag + ", ' " + typ + "');";
        Statement stmt = billConnection.createStatement();
        stmt.execute(query);
        stmt.close();
        getBillInfos("ORDER BY Bill_ID DESC");


    }


    public void refreshBills(ActionEvent actionEvent) throws SQLException {
        getBillInfos("ORDER BY Bill_ID DESC");
    }


    public void changeBillOrder(ActionEvent actionEvent) {
        String selection = orderBillsByBox.getValue().toString();
        String order = "";
        if(Objects.equals(selection, billOrderPossibilitys.DATUM_AUFSTEIGEND.toString())){
            order = "ORDER BY Bill_ID ASC";
        }
        else if(Objects.equals(selection, billOrderPossibilitys.DATUM_ABSTEIGEND.toString())){
            order = "ORDER BY Bill_ID DESC";
        }
        else if(Objects.equals(selection, billOrderPossibilitys.BETRAG_AUFSTEIGEND.toString())){
            order = "ORDER BY Betrag ASC";
        }
        else if(Objects.equals(selection, billOrderPossibilitys.BETRAG_ABSTEIGEND.toString())){
            order = "ORDER BY Betrag DESC";
        }

        getBillInfos(order);

    }
}