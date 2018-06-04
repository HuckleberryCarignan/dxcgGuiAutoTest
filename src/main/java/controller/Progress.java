package controller;

import com.verscend.dxcg.api.IProcessingObserver;
import com.verscend.dxcg.api.IProcessingStatus;
import javafx.application.Platform;

public class Progress implements IProcessingObserver {
    //private MainController mainController;
    @Override
    public void updateProgressChange(IProcessingStatus processingStatus) {
        try {
            MainController mainController = Main.getController();
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    mainController.setProgress(processingStatus.getProgress());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateStatusChange(IProcessingStatus processingStatus) {
        //System.out.print(String.format("%s", processingStatus.getMessage()));
       // mainController.setStatus(processingStatus.getMessage())
    }


}
