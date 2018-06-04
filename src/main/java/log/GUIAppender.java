package log;


import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.encoder.EchoEncoder;
import ch.qos.logback.core.encoder.Encoder;
import controller.Main;
import controller.MainController;
import javafx.application.Platform;
import javafx.scene.control.Label;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class GUIAppender extends AppenderBase<ILoggingEvent> {

    @Override
    public void append(ILoggingEvent event) {
        MainController mainController = Main.getController();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mainController.setLogTextArea( event.getMessage());
            }
        });
    }
}