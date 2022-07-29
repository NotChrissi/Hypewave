package com.example.hypewave;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    public AnchorPane calculatePane;

    public AnchorPane billPane;

    private List<Pane> mainPanes = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainPanes.add(billPane);
        mainPanes.add(calculatePane);

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


    public void loadCalculatePane(ActionEvent actionEvent) {
        activateMainPane(calculatePane);
        System.out.println("calc");
    }

    public void loadBillPane(ActionEvent actionEvent) {
        activateMainPane(billPane);
        System.out.println("bill");
    }

}