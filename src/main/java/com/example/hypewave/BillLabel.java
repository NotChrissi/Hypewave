package com.example.hypewave;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class BillLabel {
    int spacing = 10;
    HBox hBox = new HBox();
    Button deleteButton = new Button();
    Label idLabel = new Label();
    Label influencerLabel = new Label();
    Label betragLabel = new Label();
    Label typLabel = new Label();
    Pane hBoxParent;
    Bill bill;
    Connection con;

    boolean deleted = false;
    public BillLabel(Bill bill, Pane parent, Connection con) {
        this.bill = bill;
        this.hBoxParent = parent;
        parent.getChildren().add(hBox);
        addComponents();
        editComponents();
        this.con = con;
        hBox.setSpacing(spacing);
    }

    public void addComponents(){
        hBox.getChildren().addAll(idLabel, influencerLabel, betragLabel, typLabel, deleteButton);
    }

    public void editComponents(){
        influencerLabel.setText(bill.Influencer);
        betragLabel.setText(String.valueOf(bill.Betrag));
        typLabel.setText(bill.Typ);
        idLabel.setText(String.valueOf(bill.id));
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                delete();
                try {
                    deleteFromSQL();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    public void delete(){
        VBox box = (VBox) deleteButton.getParent().getParent();
        box.getChildren().remove(deleteButton.getParent());
        deleted = true;
    }

    public void deleteFromSQL() throws SQLException {
        Statement stmt = con.createStatement();
        String query = "DELETE FROM Bills where Bill_ID = " + bill.id;

        stmt.execute(query);
        stmt.close();
    }

}
