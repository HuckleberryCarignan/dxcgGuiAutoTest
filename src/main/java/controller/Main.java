package controller;

import com.verscend.dxcg.domain.grouper.XmlObjectFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import log.GUIAppender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;

public class Main extends Application {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(Main.class);
    private static MainController controller;
    @Override
    public void start(Stage primaryStage) throws Exception {
        XmlObjectFactory.getInstance();
        if (!XmlObjectFactory.getExceptions().isEmpty()) {
            for (Exception e : XmlObjectFactory.getExceptions()) {
                LOGGER.error(e.getMessage());
            }
            System.exit(-1);
        }
       // TextArea textArea = new TextArea();

        primaryStage.setTitle("DxCG Application");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/main.fxml"));
        Pane pane = loader.load();
        controller = loader.getController();
//        Label loglabel=controller.getLogLabel();
//        OutputStream os = new LabelOutputStream(loglabel);
//
//        MyStaticOutputStreamAppender.setStaticOutputStream(os);
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setMaximized(true);
    }

//    private static class LabelOutputStream extends OutputStream {
//
//        private Label loglabel;
//
//        public LabelOutputStream(Label loglabel) {
//            this.loglabel = loglabel;
//        }
//
//        @Override
//        public void write(int b) throws IOException {
//            loglabel.setText(String.valueOf((char) b));
//        }
//    }

    public static void main(String[] args) {
        launch(args);
    }

    public static MainController getController() {
        return controller;
    }
}



