package com.example.hypewave;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class BillLabel {
    HBox hBox = new HBox();
    Button deleteButton = new Button();
    Label influencerLabel = new Label();
    Label betragLabel = new Label();
    Label typLabel = new Label();
    Pane hBoxParent;
    Bill bill;
    public BillLabel(Bill bill, Pane parent) {
        this.bill = bill;
        this.hBoxParent = parent;
        parent.getChildren().add(hBox);
        addComponents();
        editComponents();
    }

    public void addComponents(){
        hBox.getChildren().addAll(influencerLabel, betragLabel, typLabel, deleteButton);
    }

    public void editComponents(){
        influencerLabel.setText(bill.Influencer);
        betragLabel.setText(String.valueOf(bill.Betrag));
        typLabel.setText(bill.Typ);
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                VBox box = (VBox) deleteButton.getParent().getParent();
                box.getChildren().remove(deleteButton.getParent());
            }
        });
    }

}
