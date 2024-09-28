package it.polito.tdp.RacePlanner;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.controlsfx.control.CheckComboBox;

import it.polito.tdp.RacePlanner.model.Model;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnSave;

    @FXML
    private CheckComboBox<?> ckCmbContinents;

    @FXML
    private CheckComboBox<?> ckCmbMonths;

    @FXML
    private CheckComboBox<?> ckCmbNations;

    @FXML
    private ComboBox<?> cmbCatR1;

    @FXML
    private ComboBox<?> cmbCatR2;

    @FXML
    private ComboBox<?> cmbCatR3;

    @FXML
    private ComboBox<?> cmbCatR4;

    @FXML
    private ComboBox<?> cmbCatR5;

    @FXML
    private ComboBox<?> cmbCategory;

    @FXML
    private ComboBox<?> cmbFavRace;

    @FXML
    private ComboBox<?> cmbLevel;

    @FXML
    private ComboBox<?> cmbNameR1;

    @FXML
    private ComboBox<?> cmbNameR2;

    @FXML
    private ComboBox<?> cmbNameR3;

    @FXML
    private ComboBox<?> cmbNameR4;

    @FXML
    private ComboBox<?> cmbNameR5;

    @FXML
    private ComboBox<?> cmbNationR1;

    @FXML
    private ComboBox<?> cmbNationR2;

    @FXML
    private ComboBox<?> cmbNationR3;

    @FXML
    private ComboBox<?> cmbNationR4;

    @FXML
    private ComboBox<?> cmbNationR5;

    @FXML
    private ComboBox<?> cmbYear;

    @FXML
    private ComboBox<?> cmbYearR1;

    @FXML
    private ComboBox<?> cmbYearR2;

    @FXML
    private ComboBox<?> cmbYearR3;

    @FXML
    private ComboBox<?> cmbYearR4;

    @FXML
    private ComboBox<?> cmbYearR5;

    @FXML
    private TableColumn<?, ?> colCategory;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colElevGain;

    @FXML
    private TableColumn<?, ?> colKm;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPlace;

    @FXML
    private Label lblInstructions;

    @FXML
    private Label lblLevel;

    @FXML
    private Label lblWarnings;

    @FXML
    private Tab tabLevel;

    @FXML
    private Tab tabMain;

    @FXML
    private TableView<?> tblRaces;

    @FXML
    private TextField txtKmMaxRace;

    @FXML
    private TextField txtNMaxRaces;

    @FXML
    private TextArea txtResult;

    @FXML
    private TextField txtTimeR1;

    @FXML
    private TextField txtTimeR2;

    @FXML
    private TextField txtTimeR3;

    @FXML
    private TextField txtTimeR4;

    @FXML
    private TextField txtTimeR5;

    @FXML
    void doFindLevel(ActionEvent event) {

    }

    @FXML
    void doMaximizeKm(ActionEvent event) {

    }

    @FXML
    void doMaximizeNations(ActionEvent event) {

    }

    @FXML
    void doMaximizeRaces(ActionEvent event) {

    }

    @FXML
    void doOpenGuide(ActionEvent event) {

    }

    @FXML
    void doResetLevel(ActionEvent event) {

    }

    @FXML
    void doResetMain(ActionEvent event) {

    }

    @FXML
    void doSave(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert btnSave != null : "fx:id=\"btnSave\" was not injected: check your FXML file 'Scene.fxml'.";
        assert ckCmbContinents != null : "fx:id=\"ckCmbContinents\" was not injected: check your FXML file 'Scene.fxml'.";
        assert ckCmbMonths != null : "fx:id=\"ckCmbMonths\" was not injected: check your FXML file 'Scene.fxml'.";
        assert ckCmbNations != null : "fx:id=\"ckCmbNations\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbCatR1 != null : "fx:id=\"cmbCatR1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbCatR2 != null : "fx:id=\"cmbCatR2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbCatR3 != null : "fx:id=\"cmbCatR3\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbCatR4 != null : "fx:id=\"cmbCatR4\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbCatR5 != null : "fx:id=\"cmbCatR5\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbCategory != null : "fx:id=\"cmbCategory\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbFavRace != null : "fx:id=\"cmbFavRace\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbLevel != null : "fx:id=\"cmbLevel\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbNameR1 != null : "fx:id=\"cmbNameR1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbNameR2 != null : "fx:id=\"cmbNameR2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbNameR3 != null : "fx:id=\"cmbNameR3\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbNameR4 != null : "fx:id=\"cmbNameR4\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbNameR5 != null : "fx:id=\"cmbNameR5\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbNationR1 != null : "fx:id=\"cmbNationR1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbNationR2 != null : "fx:id=\"cmbNationR2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbNationR3 != null : "fx:id=\"cmbNationR3\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbNationR4 != null : "fx:id=\"cmbNationR4\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbNationR5 != null : "fx:id=\"cmbNationR5\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbYear != null : "fx:id=\"cmbYear\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbYearR1 != null : "fx:id=\"cmbYearR1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbYearR2 != null : "fx:id=\"cmbYearR2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbYearR3 != null : "fx:id=\"cmbYearR3\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbYearR4 != null : "fx:id=\"cmbYearR4\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbYearR5 != null : "fx:id=\"cmbYearR5\" was not injected: check your FXML file 'Scene.fxml'.";
        assert colCategory != null : "fx:id=\"colCategory\" was not injected: check your FXML file 'Scene.fxml'.";
        assert colDate != null : "fx:id=\"colDate\" was not injected: check your FXML file 'Scene.fxml'.";
        assert colElevGain != null : "fx:id=\"colElevGain\" was not injected: check your FXML file 'Scene.fxml'.";
        assert colKm != null : "fx:id=\"colKm\" was not injected: check your FXML file 'Scene.fxml'.";
        assert colName != null : "fx:id=\"colName\" was not injected: check your FXML file 'Scene.fxml'.";
        assert colPlace != null : "fx:id=\"colPlace\" was not injected: check your FXML file 'Scene.fxml'.";
        assert lblInstructions != null : "fx:id=\"lblInstructions\" was not injected: check your FXML file 'Scene.fxml'.";
        assert lblLevel != null : "fx:id=\"lblLevel\" was not injected: check your FXML file 'Scene.fxml'.";
        assert lblWarnings != null : "fx:id=\"lblWarnings\" was not injected: check your FXML file 'Scene.fxml'.";
        assert tabLevel != null : "fx:id=\"tabLevel\" was not injected: check your FXML file 'Scene.fxml'.";
        assert tabMain != null : "fx:id=\"tabMain\" was not injected: check your FXML file 'Scene.fxml'.";
        assert tblRaces != null : "fx:id=\"tblRaces\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtKmMaxRace != null : "fx:id=\"txtKmMaxRace\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNMaxRaces != null : "fx:id=\"txtNMaxRaces\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtTimeR1 != null : "fx:id=\"txtTimeR1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtTimeR2 != null : "fx:id=\"txtTimeR2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtTimeR3 != null : "fx:id=\"txtTimeR3\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtTimeR4 != null : "fx:id=\"txtTimeR4\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtTimeR5 != null : "fx:id=\"txtTimeR5\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }

}
