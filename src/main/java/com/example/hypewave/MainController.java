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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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

        initSQL();

    }

    public void initSQL(){
        String connectionUrl =
                "jdbc:sqlserver://Chrissi.database.windows.net:1433;"
                        + "database=AdventureWorks;"
                        + "user=Chrissi@Chrissi;"
                        + "password=Chrissi123;"
                        + "encrypt=true;"
                        + "trustServerCertificate=false;"
                        + "loginTimeout=30;";

        try (Connection connection = DriverManager.getConnection(connectionUrl);) {
            System.out.println("connected");
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
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

    public void getBillInfos(){

    }

    public void saveBillToSQL(){
        jdbc:mysql://127.0.0.1:3306/?user=Chrissi
    }


    public void loadCalculatePane(ActionEvent actionEvent) {
        activateMainPane(calculatePane);
    }

    public void loadBillPane(ActionEvent actionEvent) {
        activateMainPane(billPane);
    }

}