package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ConfigUtility implements Initializable{
    @FXML private BorderPane boarderPaneConfig;
    @FXML private Button buttonRun;
    @FXML private Button buttonModels;
    @FXML private Button buttonInput;
    @FXML private Button buttonData;
    @FXML private Button buttonOutput;
    @FXML private Button buttonReport;
    private DataScene dataSceneController;
    FXMLLoader dataSceneLoader = new FXMLLoader(getClass().getResource("/view/dataScene.fxml"));
    Node dataSceneNode;

    public DataScene getDataSceneController() {
        return dataSceneController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            dataSceneNode =dataSceneLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.dataSceneController = dataSceneLoader.getController();

    }

    public void dataClicked(ActionEvent actionEvent) throws IOException {
       // FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/dataScene.fxml"));
       // boarderPaneConfig.setCenter(d);
        boarderPaneConfig.setCenter(dataSceneNode);
       // configTab.setContent(loader.load());
       // tabPane.getTabs().add(configTab);

    }
    public void runClicked(ActionEvent actionEvent) {
    }

    public void modelsClicked(ActionEvent actionEvent) {
    }

    public void inputClicked(ActionEvent actionEvent) {
    }
    public void outputClicked(ActionEvent actionEvent) {
    }

    public void reportClicked(ActionEvent actionEvent) {
    }



}
