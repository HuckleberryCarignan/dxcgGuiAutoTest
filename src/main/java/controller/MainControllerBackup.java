package controller;

import com.verscend.dxcg.api.DxCGAPI;
import com.verscend.dxcg.api.ProcessingStatus;
import com.verscend.dxcg.domain.config.Config;
import com.verscend.dxcg.domain.config.ConfigParser;
import com.verscend.dxcg.domain.config.IConfig;
import com.verscend.dxcg.exception.DxCGProcessorException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.controlsfx.control.HiddenSidesPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class MainControllerBackup implements Initializable{
    private static final Logger LOGGER = LoggerFactory
            .getLogger(MainControllerBackup.class);
    @FXML private Button button;
    @FXML private TreeView treeView;
    @FXML private StackPane abc;
    @FXML private Label pinLabel;
    @FXML private HiddenSidesPane hiddenSidesPane;
    @FXML private TextArea logTextArea;
   // @FXML private Label logLabel;

    @FXML private ProgressBar progressbar;
    @FXML private ProgressIndicator progressindicator;
    @FXML private TextField textfield_file;
    @FXML private GridPane gridpane;
    @FXML private TabPane tabPane;
    private Tab configTab ;
    //private String configPath;


    public void openConfig() throws IOException {

        if (!tabPane.getTabs().contains(configTab)){
            configTab = new Tab("Config");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/configTabPage.fxml"));
            configTab.setContent(loader.load());
            tabPane.getTabs().add(configTab);
            tabPane.getSelectionModel().select(configTab);

            ConfigTabPage configTabPage = loader.getController();
            List<String> input;
            try (Stream<String> stream = Files.lines(Paths.get(textfield_file.getText()))) {
                input=stream.limit(100).collect(toList());
            }
            configTabPage.setTxtconfig(input);

        }
    }

    public void getFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Config File");
        File file = fileChooser.showOpenDialog(gridpane.getScene().getWindow());
        textfield_file.setText(file.getPath());

        String configPath=textfield_file.getText();
        IConfig config = new Config();
        ConfigParser configParser = new ConfigParser();
        Properties configProperties = null;
        try {
            configProperties = configParser.parseProperties(configPath);

        } catch (DxCGProcessorException e) {
            e.printStackTrace();
        }
        configParser.populateConfigFromPropertiesFile(configProperties,
                config);
        configParser.validateConfig(configProperties, config);
        File f = new File(configPath);
        TreeItem<String> root = new TreeItem<String>(f.getName());
        TreeItem<String> input = new TreeItem<String>("Input Files");
        TreeItem<String> invalid = new TreeItem<String>("Invalid Data Files");
        TreeItem<String> output = new TreeItem<String>("Output Files");
        root.getChildren().add(input);
        root.getChildren().add(invalid);
        root.getChildren().add(output);
        input.getChildren().add(new TreeItem<String>(config.getMemberFile()));
        input.getChildren().add(new TreeItem<String>(config.getDiagFile()));
        input.getChildren().add(new TreeItem<String>(config.getRxFile()));
        invalid.getChildren().add(new TreeItem<String>(config.getInvalidMemberFile()));
        invalid.getChildren().add(new TreeItem<String>(config.getInvalidDiagFile()));
        invalid.getChildren().add(new TreeItem<String>(config.getInvalidRxFile()));
        output.getChildren().add(new TreeItem<String>(config.getOutputFile()));
        treeView.setRoot(root);
        root.setExpanded(true);
        input.setExpanded(true);
        invalid.setExpanded(true);
        output.setExpanded(true);
    }


    public void runConfig(ActionEvent actionEvent) {

        //String configFilePath = "E:\\Mocha\\dxcg\\Provider_Demo\\provider_demo_mocha.cfg";
        String configPath=textfield_file.getText();
        ProcessingStatus processingStatus = new ProcessingStatus();
        processingStatus.addObserver(new Progress());
        DxCGAPI dxcgApi = new DxCGAPI();
        long startMs;
        startMs = System.currentTimeMillis();
        LOGGER.debug("Initializing configuration file: " + configPath);

        int initReturnValue = dxcgApi.initialize(configPath,
                processingStatus);
        //System.out.println("initReturnValue= "+initReturnValue);
        final long[] endMs = new long[1];
        final int[] returnValue = {initReturnValue};
        Task<Void> executeAppTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                returnValue[0] = dxcgApi.processMembers(configPath);
                return null;
            }
        };
        if (initReturnValue == 0) {
            Thread thread = new Thread(executeAppTask);
            thread.setDaemon(true);
            thread.start();
        }
        executeAppTask.setOnSucceeded(e -> {
            // code to execute when task completes normally
            //returnValue = executeAppTask.getValue();
            endMs[0] = System.currentTimeMillis();
            System.out.println("Execution time: "+ prettyPrintElapsedTime(endMs[0] - startMs));
           // System.exit(returnValue[0]);
            LOGGER.warn("Execution time: "+ prettyPrintElapsedTime(endMs[0] - startMs));
        });

        executeAppTask.setOnFailed(e -> {
            //Throwable problem = executeAppTask.getException();
            // code to execute if task throws exception
        });

        executeAppTask.setOnCancelled(e -> {
            //task was cancelled
        });

    }
    private static String prettyPrintElapsedTime(long ms) {
        return DurationFormatUtils.formatDurationHMS(ms);
    }

    public void setProgress(int value){
        // SimpleIntegerProperty progress = new SimpleIntegerProperty(0);
        // progress.setValue(value/100);
        // progressbar.progressProperty().bind(progress);

        progressbar.setProgress(value/100F);
        progressindicator.setProgress(value/100F);
        // progressindicator.progressProperty().bind(progress);
    }

    //public void setLogLabel(String log) {
      //  logLabel.setText(log);
   // }
   // public Label getLogLabel(){
    //    return logLabel;
   // }
    public void setLogTextArea(String log){
        logTextArea.appendText(log);
        logTextArea.appendText("\n");
    }

    public void handleMouseClicked(MouseEvent mouseEvent) {
        if (hiddenSidesPane.getPinnedSide() != null) {
            pinLabel.setText("(unpinned)");
            hiddenSidesPane.setPinnedSide(null);
            hiddenSidesPane.setPrefWidth(50);
          //  hiddenSidesPane.setMinWidth(50);
        } else {
            pinLabel.setText("(pinned)");
            hiddenSidesPane.setPinnedSide(Side.LEFT);
            hiddenSidesPane.contentProperty().get().toFront();
            hiddenSidesPane.setPrefWidth(250);
           // hiddenSidesPane.setMinWidth(400);
            //button.toFront();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       //loadTreeItems(,);
       // String configPath=textfield_file.getText();
        //button.toFront();
        hiddenSidesPane.setOnMouseEntered(event ->
        {
            hiddenSidesPane.getContent().toFront();
            //tabPane.toBack();
            //hiddenSidesPane.leftProperty().get().toFront();
             hiddenSidesPane.toFront();
          //  MAKE CONTENT MA TABPANE;
            //logTextArea.setText("ASDASDASD");
           // tabPane.toBack();
            //hiddenSidesPane.toFront();
        });
      //  hiddenSidesPane.setOnMouseExited(event -> tabPane.toFront());
        hiddenSidesPane.leftProperty().addListener((observable, oldValue, newValue) -> {
            if (hiddenSidesPane.getLeft() == null){
                tabPane.toFront();
            }
        });

     //   tabPane.setOnMouseEntered(event -> tabPane.toFront());

        treeView.getSelectionModel().selectedItemProperty().addListener( new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue,
                                Object newValue) {

                TreeItem<String> selectedItem = (TreeItem<String>) newValue;
                //selectedItem.getValue();
                try {
                    openFile(selectedItem.getValue());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });

    }
    public void loadTreeItems(String... rootItems) {
        TreeItem<String> root = new TreeItem<String>("Root Node");
        root.setExpanded(true);
        for (String itemString: rootItems) {
            root.getChildren().add(new TreeItem<String>(itemString));
        }

        treeView.setRoot(root);
    }
    public void openFile(String filename) throws IOException {
            configTab = new Tab(filename);
        if (!tabPane.getTabs().contains(configTab)){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/configTabPage.fxml"));
            configTab.setContent(loader.load());
            tabPane.getTabs().add(configTab);
            tabPane.getSelectionModel().select(configTab);

            ConfigTabPage configTabPage = loader.getController();
            List<String> input;
            try (Stream<String> stream = Files.lines(Paths.get(filename))) {
                input=stream.limit(100).collect(toList());
            }
            configTabPage.setTxtconfig(input);

        }
    }
}
