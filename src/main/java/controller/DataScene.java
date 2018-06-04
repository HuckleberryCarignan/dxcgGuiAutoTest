package controller;

import com.verscend.dxcg.domain.config.Range;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import javax.xml.soap.Text;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class DataScene  implements Initializable{
    @FXML private TabPane tabPaneConfig;
    @FXML private Tab memberTabPage;
    @FXML private TextArea txtMember;
    @FXML private RadioButton radioID;
    @FXML private RadioButton radioDOB;
    @FXML private RadioButton radioAge;
    @FXML private RadioButton radioGender;
    @FXML private RadioButton radioMonthsElig;
    @FXML private RadioButton radioMedExp;
    @FXML private TextField textFieldID;
    @FXML private TextField textFieldDOB;
    @FXML private TextField textFieldAge;
    @FXML private TextField textFieldGender;
    @FXML private TextField textFieldMonthsElig;
    @FXML private TextField textFieldMedExp;
    @FXML private RadioButton radioEligCat;
    @FXML private TextField textFieldMedEnr;
    @FXML private RadioButton radioMedEnr;
    @FXML private RadioButton radioORETM;
    @FXML private TextField textFieldEligCat;
    @FXML private TextField textFieldORETM;
    @FXML private TextField textFieldGrpVar;
    @FXML private TextField textFieldPassThrVar;
    @FXML private Tab diagnosisTabPage;
    @FXML private TextArea txtDiagnosis;
    @FXML private Tab pharmacyTabPage;
    @FXML private TextArea txtPharmacy;
    @FXML private ToggleGroup toggleGroup;
    private TextField textFieldToSet;
    static String memberFile;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        final int[] noOfLines = {0};
        // "D:/Mocha/Data/Provider demo/Input Files/provider_demo_members.txt"
        Platform.runLater(() -> {
            // txtconfig.requestFocus();
            List<String> input;
            try (Stream<String> stream = Files.lines(Paths.get(memberFile))) {
                input=stream.limit(100).collect(toList());
                noOfLines[0] = input.size() < 100 ? input.size() : 100;
                for(String line: input){
                    txtMember.appendText(line);
                    txtMember.appendText(System.lineSeparator());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            // TextArea textArea= configTabPage.geTxtconfig();
            txtMember.setScrollTop(0);
            radioID.setSelected(true);
        });

        txtMember.selectionProperty().addListener((observable, oldValue, newValue) -> {
            if (txtMember.isFocused()) {
                int start;
                int end;
                // max number of columns in member file or length of row.
                // noOfLines gives the minimum of 100 and no of lines in member file.
                int maxColumn= txtMember.getLength()/noOfLines[0];

                // Text selection start and end point line number.
                int startLineNo = newValue.getStart()/maxColumn;
                int endLineNo = newValue.getEnd()/maxColumn;

                // if the text selected is from different line, deselect the text.
                if (startLineNo!=endLineNo){
                    txtMember.deselect();
                }
                else{
                    // newValue.getStart() gives the start position of selection.
                    // But this value is the offset from start of textArea.
                    // So, subtracting the total offset by the line number position * length of that line.
                    // This mimicks as if the text area has only "One" line.
                    start= newValue.getStart()-startLineNo*maxColumn +1;
                    end= newValue.getEnd()-endLineNo*maxColumn;
                    //txtMember.selectRange(start,end);
                    if(start<= end) textFieldToSet.setText(String.valueOf(start)+"-"+String .valueOf(end));
                }
            }
        });
        textFieldAge.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                radioAge.setSelected(true);
            }
        });
        textFieldDOB.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                radioDOB.setSelected(true);
            }
        });
        textFieldEligCat.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                radioEligCat.setSelected(true);
            }
        });
        textFieldGender.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                radioGender.setSelected(true);
            }
        });

        textFieldID.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                radioID.setSelected(true);
            }
        });
        textFieldMedEnr.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                radioMedEnr.setSelected(true);
            }
        });
        textFieldMedExp.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                radioMedExp.setSelected(true);
            }
        });
        textFieldMonthsElig.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                radioMonthsElig.setSelected(true);
            }
        });
        textFieldORETM.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                radioORETM.setSelected(true);
            }
        });

        toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                RadioButton chk = (RadioButton) newValue.getToggleGroup().getSelectedToggle(); // Cast object to radio button
                //System.out.println("Selected Radio Button - "+chk.getText());
                if(chk.equals(radioAge)) {
                    textFieldAge.requestFocus();
                    textFieldToSet=textFieldAge;
                }
                else if(chk.equals(radioDOB)) {
                    textFieldDOB.requestFocus();
                    textFieldToSet=textFieldDOB;
                }
                else if(chk.equals(radioEligCat)) {
                    textFieldEligCat.requestFocus();
                    textFieldToSet=textFieldEligCat;
                }
                else if(chk.equals(radioGender)){
                    textFieldGender.requestFocus();
                    textFieldToSet=textFieldGender;
                }
                else if(chk.equals(radioID)){
                    textFieldID.requestFocus();
                    textFieldToSet=textFieldID;
                }
                else if(chk.equals(radioMedEnr)){
                    textFieldMedEnr.requestFocus();
                    textFieldToSet=textFieldMedEnr;
                }
                else if(chk.equals(radioMedExp)){
                    textFieldMedExp.requestFocus();
                    textFieldToSet=textFieldMedExp;
                }
                else if(chk.equals(radioMonthsElig)){
                    textFieldMonthsElig.requestFocus();
                    textFieldToSet=textFieldMonthsElig;
                }
                else if(chk.equals(radioORETM)){
                    textFieldORETM.requestFocus();
                    textFieldToSet=textFieldORETM;
                }

            }
        });
    }
    public void setTxtMember(List<String> config){
        for(String line:config){
            txtMember.appendText(line);
            txtMember.appendText("\n");
        }
    }
    public void setTextFieldID(Range id){
        textFieldID.setText(id.getBegin()+"-"+id.getEnd());
    }

}
