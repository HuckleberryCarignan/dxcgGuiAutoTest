package controller;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Logger;

public class ConfigTabPage implements Initializable{


    @FXML private TextArea txtconfig;

  public void setTxtconfig(List<String> config){
      for(String line:config){
          txtconfig.appendText(line);
          txtconfig.appendText("\n");

      }
  }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
               // txtconfig.requestFocus();
                txtconfig.setScrollTop(0);
            }
        });

    }


}
