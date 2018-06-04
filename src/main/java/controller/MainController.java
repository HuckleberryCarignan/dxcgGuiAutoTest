package controller;

import com.pixelduke.javafx.ribbon.Ribbon;
import com.pixelduke.javafx.ribbon.RibbonTab;
import com.verscend.dxcg.api.DxCGAPI;
import com.verscend.dxcg.api.ProcessingStatus;
import com.verscend.dxcg.domain.config.Config;
import com.verscend.dxcg.domain.config.ConfigParser;
import com.verscend.dxcg.domain.config.IConfig;
import com.verscend.dxcg.exception.DxCGProcessorException;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.controlsfx.control.HiddenSidesPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.plugin2.message.MouseEventMessage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static javafx.scene.input.MouseEvent.*;

public class MainController implements Initializable{
    private static final Logger LOGGER = LoggerFactory
            .getLogger(MainController.class);
    @FXML private Button buttonConfigUtility;
    @FXML private BorderPane boarderPane;
    @FXML private Ribbon ribbon;
    @FXML private RibbonTab tabHome;
    @FXML private Button buttonRunConfig;
    @FXML private Label pinLabelProgress;
    @FXML private Button buttonProgress;
    @FXML private VBox vboxProgress;
    @FXML private VBox vboxTree;
    //@FXML private  Insets tabPaneMargin;
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

    //private String configPath;
    long startMs;
    long endMs;
    private IConfig config = new Config();
    String configPath;
    private Map<String, Tab> openTabs = new HashMap<>();
    Tab configTab = new Tab("Configuration File Utility");
    MenuItem menuItemOpenUtility ;
    //int returnValue;

//    public void openConfig() throws IOException {
//        //Tab configTab = new ;
//        if (!tabPane.getTabs().contains(configTab)){
//            configTab = new Tab("Config");
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/configTabPage.fxml"));
//            configTab.setContent(loader.load());
//            tabPane.getTabs().add(configTab);
//            tabPane.getSelectionModel().select(configTab);
//
//            ConfigTabPage configTabPage = loader.getController();
//            List<String> input;
//            try (Stream<String> stream = Files.lines(Paths.get(textfield_file.getText()))) {
//                input=stream.limit(100).collect(toList());
//            }
//            configTabPage.setTxtconfig(input);
//
//        }
//    }

    public void getFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Config File");
        File file = fileChooser.showOpenDialog(gridpane.getScene().getWindow());
        textfield_file.setText(file.getPath());
        configPath=textfield_file.getText();
        //configPath = "E:\\Mocha\\dxcg\\Provider_Demo\\provider_demo_mocha_true.cfg";
        //IConfig config = new Config();
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
      //  File f = new File(configPath);
        TreeItem<String> root = new TreeItem<String>(new File(configPath).getName());
        TreeItem<String> input = new TreeItem<String>("Input Files");
        TreeItem<String> invalid = new TreeItem<String>("Invalid Data Files");
        TreeItem<String> output = new TreeItem<String>("Output Files");
        root.getChildren().add(input);
        root.getChildren().add(invalid);
        root.getChildren().add(output);
        if (new File(config.getMemberFile()).exists()) {
            input.getChildren().add(new TreeItem<String>("Member File"));

        }
        if (new File(config.getDiagFile()).exists())
            input.getChildren().add(new TreeItem<String>("Diag File"));
        if (new File(config.getRxFile()).exists())
            input.getChildren().add(new TreeItem<String>("Rx File"));
//        if (new File(config.getHistoricalEligFile()).exists())
//            input.getChildren().add(new TreeItem<String>( "Historical File"));
//        if (new File(config.getRiskDriverFilePath()).exists())
//            input.getChildren().add(new TreeItem<String>("Risk Driver File"));
        if ( new File(config.getInvalidMemberFile()).exists())
            invalid.getChildren().add(new TreeItem<String>("Invalid Member File"));
        if (new File(config.getInvalidDiagFile()).exists())
            invalid.getChildren().add(new TreeItem<String>("Invalid Diag File"));
        if (new File(config.getInvalidRxFile()).exists())
            invalid.getChildren().add(new TreeItem<String>("Invalid Rx File"));
//        if(new File(config.getHistoricalInvalidEligFile()).exists())
//            invalid.getChildren().add(new TreeItem<String>("Invalid Historical FIle"));
        if(new File(config.getOutputFile()).exists())
            output.getChildren().add(new TreeItem<String>("Output File"));
        treeView.setRoot(root);
        root.setExpanded(true);
        input.setExpanded(true);
        invalid.setExpanded(true);
        output.setExpanded(true);
    }


    public void runConfig(ActionEvent actionEvent) {
        buttonRunConfig.setDisable(true);
        if(Objects.equals(pinLabelProgress.getText(), "Unpinned")){
            vboxProgress.toFront();
            handlePinProgressClicked(new MouseEvent(MOUSE_CLICKED, 0, 0, 0, 0, MouseButton.PRIMARY, 1, true, true, true,
                    false, true, false, false, false, false, true, null));
        }
        //String configFilePath = "E:\\Mocha\\dxcg\\Provider_Demo\\provider_demo_mocha.cfg";

        Task<Void> executeAppTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                String configPath=textfield_file.getText();
                ProcessingStatus processingStatus = new ProcessingStatus();
                processingStatus.addObserver(new Progress());
                DxCGAPI dxcgApi = new DxCGAPI();

                startMs = System.currentTimeMillis();
                LOGGER.debug("Initializing configuration file: " + configPath);

                int initReturnValue = dxcgApi.initialize(configPath,
                        processingStatus);
                //System.out.println("initReturnValue= "+initReturnValue);
               //returnValue = initReturnValue;
                if (initReturnValue == 0) {
                   dxcgApi.processMembers(configPath);
                }
                return null;
            }
        };


        Thread thread = new Thread(executeAppTask);
        thread.setDaemon(true);
        thread.start();

        executeAppTask.setOnSucceeded(e -> {
            // code to execute when task completes normally
            //returnValue = executeAppTask.getValue();
            endMs = System.currentTimeMillis();
            System.out.println("Execution time: "+ prettyPrintElapsedTime(endMs - startMs));
           // System.exit(returnValue[0]);
            LOGGER.warn("Execution time: "+ prettyPrintElapsedTime(endMs - startMs));
            buttonRunConfig.setDisable(false);
           // input.getChildren().add(new TreeItem<String>("Member File"));
            TreeItem<String> output= (TreeItem<String>) treeView.getRoot().getChildren().get(2);
            output.getChildren().add(new TreeItem<String>("Output File"));
            //logTextArea.appendText(String.valueOf( treeView.get(new TreeItem<String>("Output Files"))));
        });

        executeAppTask.setOnFailed(e -> {
            //Throwable problem = executeAppTask.getException();
            // code to execute if task throws exception
            buttonRunConfig.setDisable(false);

        });

        executeAppTask.setOnCancelled(e -> {
            //task was cancelled
            buttonRunConfig.setDisable(false);

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
        if (Objects.equals(pinLabel.getText(), "Pinned")) {
          //  pinLabel.setText("(Unpinned)");
           // hiddenSidesPane.setPinnedSide(null);
           // hiddenSidesPane.setPrefWidth(50);
            pinLabel.setText("Unpinned");
            vboxTree.setVisible(false);
            VBox.setMargin(treeView, new Insets(0,0,0,10));

            if (Objects.equals(pinLabelProgress.getText(), "Pinned")){
                Insets insets = new Insets(0,0,190,50);
                StackPane.setMargin(tabPane,insets);
                StackPane.setMargin(vboxProgress, new Insets(0,0,0,34));
                StackPane.setMargin(vboxTree, new Insets(0,0,0,30));


            }
            else {
                Insets insets = new Insets(0, 0, 60, 50);
                StackPane.setMargin(tabPane, insets);
                StackPane.setMargin(vboxProgress, new Insets(0,0,30,34));
                StackPane.setMargin(vboxTree, new Insets(0,0,34,30));

                //StackPane.setMargin(vboxTree, new Insets(0,0,34,30));

            }
          //  hiddenSidesPane.setMinWidth(50);
        } else {
          //  pinLabel.setText("(pinned)");
            pinLabel.setText("Pinned");
            vboxTree.setVisible(true);
            VBox.setMargin(treeView, new Insets(0,0,0,0));



            if (Objects.equals(pinLabelProgress.getText(), "Pinned")){
                Insets insets = new Insets(0,0,190,270);
                StackPane.setMargin(tabPane,insets);
                StackPane.setMargin(vboxTree, new Insets(0,0,154,0));
                StackPane.setMargin(vboxProgress, new Insets(0,0,0,0));

            }
            else {
                Insets insets = new Insets(0, 0, 60, 270);
                StackPane.setMargin(tabPane, insets);
                StackPane.setMargin(vboxTree, new Insets(0,0,34,0));
                StackPane.setMargin(vboxProgress, new Insets(0,0,30,0));

            }

            /// / tabPaneMargin.equals(insets);
           // hiddenSidesPane.setMinWidth(400);
            //button.toFront();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        treeView.setCellFactory(p -> {
            TreeCellFactory treeCellFactory = new TreeCellFactory();
            treeCellFactory.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if(event.getClickCount() == 2) {
                           // logTextArea.appendText(treeCellFactory.getTreeItem().getValue().toString());
                            findNode(treeCellFactory.getTreeItem().getValue().toString());
                    }
                    if (openTabs.containsKey(new File(configPath).getName()) || tabPane.getTabs().contains(configTab)){
                        treeCellFactory.getMenuItemOpenUtility().setDisable(true);
                    }
                    else {
                        treeCellFactory.getMenuItemOpenUtility().setDisable(false);
                    }
                    // logTextArea.appendText(treeCellFactory.getTreeItem().getValue().toString());
                }
            });

            return treeCellFactory ;
        });

//        button.setOnMouseEntered(event -> {
//            logTextArea.setText("Mouse entered button");
//            treeView.setVisible(true);
//            vboxTree.toFront();
//        });
            //logTextArea.setText(String.valueOf((int) 100L*100/100));
                abc.addEventFilter(MOUSE_MOVED, event -> {
                    abc.requestLayout();
                    if (!vboxTree.localToScene(vboxTree.getBoundsInLocal()).contains(event.getSceneX(), event.getSceneY())
                            && !Objects.equals(pinLabel.getText(), "Pinned")) {
                        //logTextArea.setText(String.valueOf(event.getSceneX()) + ", " + String.valueOf(event.getSceneY()));
                        vboxTree.setVisible(false);
                        vboxTree.toBack();

                    }
                    if (button.localToScene(button.getBoundsInLocal()).contains(event.getSceneX(), event.getSceneY())) {
                        // if (button.getBoundsInParent().contains(event.getX(), event.getY())) {
                        // pinLabel.setText("Pinned");
                        //logTextArea.setText(String.valueOf(event.getSceneX()) + ", " + String.valueOf(event.getSceneY()));
                        vboxTree.setVisible(true);
                        vboxTree.toFront();
                    }
                    if (!vboxProgress.localToScene(vboxProgress.getBoundsInLocal()).contains(event.getSceneX(), event.getSceneY())
                            && !Objects.equals(pinLabelProgress.getText(), "Pinned")) {
                       // logTextArea.setText(String.valueOf(event.getSceneX()) + ", " + String.valueOf(event.getSceneY()));
                        vboxProgress.setVisible(false);
                        vboxProgress.toBack();

                    }
                    if (buttonProgress.localToScene(buttonProgress.getBoundsInLocal()).contains(event.getSceneX(), event.getSceneY())) {
                        // if (button.getBoundsInParent().contains(event.getX(), event.getY())) {
                        // pinLabel.setText("Pinned");
                        //logTextArea.setText(String.valueOf(event.getSceneX()) + ", " + String.valueOf(event.getSceneY()));
                        vboxProgress.setVisible(true);
                        vboxProgress.toFront();
                    }


                });

                ribbon.addEventFilter(MOUSE_MOVED, event -> {
                    if(ribbon.localToScene(ribbon.getBoundsInLocal()).contains(event.getSceneX(),event.getSceneY())
                            && !Objects.equals(pinLabel.getText(), "Pinned")){
                        vboxTree.setVisible(false);
                        vboxTree.toBack();
                    }

                    //Node homeTab= ribbon.lookup("#Home");

                });

                ribbon.addEventFilter(MOUSE_CLICKED, event -> {
                    if(ribbon.lookup("#tabHome").getBoundsInLocal().contains(event.getSceneX(),event.getSceneY())
                            && event.getClickCount()==2 && ribbon.getPrefHeight()!=28 ){
                        //ribbon.setVisible(false);
                        ribbon.setPrefSize(30,28);
                    }
                    else if(ribbon.lookup("#tabHome").getBoundsInLocal().contains(event.getSceneX(),event.getSceneY())
                            && event.getClickCount()==2 && ribbon.getPrefHeight()==28 ){
                        //ribbon.setVisible(false);
                        ribbon.setPrefSize(1360,118);
                    }
//                    if(event.getPickResult().getIntersectedNode().toString().contains(tabHome.getText())){
//                        ribbon.setVisible(false);
//                    }

                   // Node abc = event.getPickResult().getIntersectedNode();

                   // else setLogTextArea("not selected tabHome");
                });




       //loadTreeItems(,);
       // String configPath=textfield_file.getText();
        //button.toFront();
//        hiddenSidesPane.setOnMouseEntered(event ->
//        {
//            hiddenSidesPane.getContent().toFront();
//            //tabPane.toBack();
//            //hiddenSidesPane.leftProperty().get().toFront();
//             hiddenSidesPane.toFront();
//          //  MAKE CONTENT MA TABPANE;
//            //logTextArea.setText("ASDASDASD");
//           // tabPane.toBack();
//            //hiddenSidesPane.toFront();
//        });
//      //  hiddenSidesPane.setOnMouseExited(event -> tabPane.toFront());
//        hiddenSidesPane.leftProperty().addListener((observable, oldValue, newValue) -> {
//            if (hiddenSidesPane.getLeft() == null){
//                tabPane.toFront();
//            }
//        });
     //   tabPane.setOnMouseEntered(event -> tabPane.toFront());
//        treeView.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                if(event.getClickCount() == 2 && event.isPrimaryButtonDown())
//                {
//                    TreeItem<String> item = (TreeItem<String>) treeView.getSelectionModel().getSelectedItem();
//                    String itemValue = item.getValue();
//                    findNode(itemValue);
//
//
//                }
////                else if(mouseEvent.isSecondaryButtonDown()) {
////                    TreeItem<String> item = (TreeItem<String>) treeView.getSelectionModel().getSelectedItem();
////                    String check = item.getValue();
////                    item.
////                }
//            }
//        });
//        treeView.setOnMouseClicked(new EventHandler<MouseEvent>()
//        {
//            @Override
//            public void handle(MouseEvent mouseEvent)
//            {
//                if(mouseEvent.getClickCount() == 2 && mouseEvent.isPrimaryButtonDown())
//                {
//                    TreeItem<String> item = (TreeItem<String>) treeView.getSelectionModel().getSelectedItem();
//                    String itemValue = item.getValue();
//                    findNode(itemValue);
//
//
//                }
////                else if(mouseEvent.isSecondaryButtonDown()) {
////                    TreeItem<String> item = (TreeItem<String>) treeView.getSelectionModel().getSelectedItem();
////                    String check = item.getValue();
////                    item.
////                }
//            }
//        });

//        treeView.getSelectionModel().selectedItemProperty().addListener( new ChangeListener() {
//            @Override
//            public void changed(ObservableValue observable, Object oldValue,
//                                Object newValue) {
//
//                TreeItem<String> selectedItem = (TreeItem<String>) newValue;
//                String check = selectedItem.getValue();
//
//                try {
//                    switch (check){
//                        case "Member File":
//                            openFile(config.getMemberFile());
//                            break;
//                        case "Diag File":
//                            openFile(config.getDiagFile());
//                            break;
//                        case "Rx File":
//                            openFile(config.getRxFile());
//                            break;
////                        case "Historical File":
////                            openFile(config.getHistoricalEligFile());
////                            break;
////                        case "Risk Driver File":
////                            openFile(config.getRiskDriverFilePath());
////                            break;
//                        case "Invalid Member File":
//                            openFile(config.getInvalidMemberFile());
//                            break;
//                        case "Invalid Diag File":
//                            openFile(config.getInvalidDiagFile());
//                            break;
//                        case "Invalid Rx File":
//                            openFile(config.getInvalidRxFile());
//                            break;
////                        case "Invalid Historical File":
////                            openFile(config.getHistoricalInvalidEligFile());
////                            break;
//                        case "Output File":
//                            openFile(config.getOutputFile());
//                            break;
//                    }
//                    if (Objects.equals(check, new File(configPath).getName())){
//                        openFile(configPath);
//                    }
//                   // openFile(selectedItem.getValue());
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        });


    }

    private void findNode(String itemValue) {
        try {
            switch (itemValue){
                case "Member File":
                    openFile("Member File",config.getMemberFile());
                    break;
                case "Diag File":
                    openFile("Diag File",config.getDiagFile());
                    break;
                case "Rx File":
                    openFile("Rx File",config.getRxFile());
                    break;
//                        case "Historical File":
//                            openFile(config.getHistoricalEligFile());
//                            break;
//                        case "Risk Driver File":
//                            openFile(config.getRiskDriverFilePath());
//                            break;
                case "Invalid Member File":
                    openFile("Invalid Member File",config.getInvalidMemberFile());
                    break;
                case "Invalid Diag File":
                    openFile("Invalid Diag File",config.getInvalidDiagFile());
                    break;
                case "Invalid Rx File":
                    openFile("Invalid Rx File",config.getInvalidRxFile());
                    break;
//                        case "Invalid Historical File":
//                            openFile(config.getHistoricalInvalidEligFile());
//                            break;
                case "Output File":
                    openFile("Output File",config.getOutputFile());
                    break;
            }
            if (Objects.equals(itemValue, new File(configPath).getName())){
                openFile(new File(configPath).getName(),configPath);
            }
            // openFile(selectedItem.getValue());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadTreeItems(String... rootItems) {
        TreeItem<String> root = new TreeItem<String>("Root Node");
        root.setExpanded(true);
        for (String itemString: rootItems) {
            root.getChildren().add(new TreeItem<String>(itemString));
        }

        treeView.setRoot(root);
    }
    public void openFile(String name, String path) throws IOException {
        if (openTabs.containsKey(name)){
            tabPane.getSelectionModel().select(openTabs.get(name));
        }
        else{
            Tab tab = new Tab(name);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/configTabPage.fxml"));
            tab.setContent(loader.load());
            tabPane.getTabs().add(tab);
            openTabs.put(name, tab);
            tabPane.getSelectionModel().select(tab);
            tab.setOnClosed(e -> openTabs.remove(name));

            ConfigTabPage configTabPage = loader.getController();
            List<String> input;
            try (Stream<String> stream = Files.lines(Paths.get(path))) {
                input=stream.limit(100).collect(toList());
            }
            configTabPage.setTxtconfig(input);
        }
    }


    public void handlePinProgressClicked(MouseEvent mouseEvent) {
        if (Objects.equals(pinLabelProgress.getText(), "Pinned")) {
            //  pinLabel.setText("(Unpinned)");
            // hiddenSidesPane.setPinnedSide(null);
            // hiddenSidesPane.setPrefWidth(50);
            pinLabelProgress.setText("Unpinned");
            vboxProgress.setVisible(false);
            VBox.setMargin(logTextArea, new Insets(0,0,10,0));


            if (Objects.equals(pinLabel.getText(), "Pinned")){
                Insets insets = new Insets(0,0,60,270);
                StackPane.setMargin(tabPane,insets);
                StackPane.setMargin(vboxProgress, new Insets(0,0,30,0));
                StackPane.setMargin(vboxTree, new Insets(0,0,34,0));



            }
            else {
                Insets insets = new Insets(0,0,60,50);
                StackPane.setMargin(tabPane,insets);
                StackPane.setMargin(vboxProgress, new Insets(0,0,30,34));
                StackPane.setMargin(vboxTree, new Insets(0,0,34,30));



            }


            //  hiddenSidesPane.setMinWidth(50);
        } else {
            //  pinLabel.setText("(pinned)");
            pinLabelProgress.setText("Pinned");
            vboxProgress.setVisible(true);
            //StackPane.setMargin(vboxProgress, new Insets(0,0,0,34));
            VBox.setMargin(logTextArea, new Insets(0,0,0,0));


            if (Objects.equals(pinLabel.getText(), "Pinned")){
                Insets insets = new Insets(0,0,160,270);
                StackPane.setMargin(tabPane,insets);
                StackPane.setMargin(vboxTree, new Insets(0,0,154,0));
                StackPane.setMargin(vboxProgress, new Insets(0,0,0,0));
            }
            else {
                Insets insets = new Insets(0, 0, 160, 50);
                StackPane.setMargin(tabPane, insets);
                StackPane.setMargin(vboxTree, new Insets(0,0,0,0));
                StackPane.setMargin(vboxProgress, new Insets(0,0,0,34));
            }
            /// / tabPaneMargin.equals(insets);
            // hiddenSidesPane.setMinWidth(400);
            //button.toFront();
        }
    }

    public void configUtility(ActionEvent actionEvent) throws IOException {
        if (!tabPane.getTabs().contains(configTab)){
           // configTab = new Tab("Member data fields");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/configUtility.fxml"));
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Member File");
            File file = fileChooser.showOpenDialog(gridpane.getScene().getWindow());
            DataScene.memberFile = file.getAbsolutePath();
            configTab.setContent(loader.load());
            tabPane.getTabs().add(configTab);
            tabPane.getSelectionModel().select(configTab);

            // configTabPage.setLblposition(textArea.getCaretPosition());
        }
    }
    private final class TreeCellFactory extends TreeCell<Object> {
        ContextMenu configContextMenu = new ContextMenu();
        ContextMenu fileContextMenu = new ContextMenu();
        MenuItem menuItemRunConfig = new MenuItem("Run Config");
        MenuItem menuItemOpen = new MenuItem("Open");
        MenuItem menuItemOpenUtility = new MenuItem("Open with Config Utility");
        MenuItem menuItemRename = new MenuItem("Rename");
        MenuItem menuItemSave = new MenuItem("Save As");
        MenuItem menuItemDelete = new MenuItem("Delete");
        MenuItem menuItemProperties = new MenuItem("Properties");

        MenuItem menuItemOpenFile = new MenuItem("Open");
        MenuItem menuItemPropertiesFile = new MenuItem("Properties");

        public MenuItem getMenuItemOpenUtility() {
            return menuItemOpenUtility;
        }

        public TreeCellFactory() {

            configContextMenu.getItems().addAll(menuItemRunConfig, menuItemOpen, menuItemOpenUtility, menuItemRename, menuItemSave, menuItemDelete, menuItemProperties);
            fileContextMenu.getItems().addAll(menuItemOpenFile,menuItemPropertiesFile);
            menuItemRunConfig.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    runConfig(event);
                }
            });
            menuItemOpen.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    TreeItem<String> item = (TreeItem<String>) treeView.getSelectionModel().getSelectedItem();
                    String itemValue = item.getValue();
                    findNode(itemValue);
                }
            });
            menuItemOpenFile.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    TreeItem<String> item = (TreeItem<String>) treeView.getSelectionModel().getSelectedItem();
                    String itemValue = item.getValue();
                    findNode(itemValue);
                }
            });
            menuItemOpenUtility.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/configUtility.fxml"));
                    try {
                        configTab.setContent(loader.load());
                        ConfigUtility configUtilityController = loader.getController();
                        tabPane.getTabs().add(configTab);
                        tabPane.getSelectionModel().select(configTab);
                        DataScene dataSceneController= configUtilityController.getDataSceneController();
                        //logTextArea.setText(dataSceneController.toString());
                        dataSceneController.setTextFieldID(config.getMemberIdRange());
                        DataScene.memberFile = config.getMemberFile();
                        //config.getMemberIdRange().toString();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        @Override
        public void updateItem(Object item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setText(null);
            } else {
                setText(item.toString());
                if(item.toString().contains(".cfg")) setContextMenu(configContextMenu);
                else if(!item.toString().contains(".cfg") && !item.toString().contains("Files")) setContextMenu(fileContextMenu);
            }

        }
    }
}
